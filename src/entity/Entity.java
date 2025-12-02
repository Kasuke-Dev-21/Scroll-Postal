package entity;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.Util;

public abstract class Entity {
	
	GamePanel gp;

	public int worldX, worldY;
	public int speed;
	
	public ArrayList<BufferedImage> up = new ArrayList<>();
    public ArrayList<BufferedImage> down = new ArrayList<>();
    public ArrayList<BufferedImage> left = new ArrayList<>();
    public ArrayList<BufferedImage> right = new ArrayList<>();
    public ArrayList<BufferedImage> idle = new ArrayList<>();
	public ArrayList<BufferedImage> currentSprites = down;
	public String direction;
	public String orientation = "right";
	
	public int spriteCounter = 0;
	// spriteIndex replaces spriteNum and will index the currentSprites list
	public int spriteIndex = 0;
	public int animationSpeed = 12;
	
	public Rectangle hitbox = new Rectangle(0, 0, 48, 48);
	public int hitboxDefaultX, hitboxDefaultY;
	public boolean collisionOn = false;
	public int actionLockCounter;

	public Entity(GamePanel gp){
		this.gp = gp;
	}

	public BufferedImage setup(String dir, String imagePath){
		
		Util tool = new Util();
		BufferedImage image = null;

		try{
			image = ImageIO.read(getClass().getResourceAsStream("/" + dir + "/" + imagePath));
			image = tool.scaleImage(image, gp.tileSize, gp.tileSize);
		} catch(IOException e){
			e.printStackTrace();
		}

		return image;
	}

	public BufferedImage setupSheet(String dir, String sheetPath, int col, int row){
		
		Util tool = new Util();
		BufferedImage sheet = null;
		BufferedImage image = null;

		try{
			sheet = ImageIO.read(getClass().getResourceAsStream("/" + dir + "/" + sheetPath));
			
			int originalTileSize = gp.originalTileSize;
			int x = col * originalTileSize;
			int y = row * originalTileSize;

			image = sheet.getSubimage(x, y, originalTileSize, originalTileSize);			
			image = tool.scaleImage(image, gp.tileSize, gp.tileSize);
			
		} catch(IOException e){
			e.printStackTrace();
		}

		return image;
	}


	public void setAction(){}

	public void update(){

		setAction();

		// Only run movement/collision logic if the entity is capable of moving
		if (speed > 0) { 
			switch(direction){
			case "left": orientation = "left"; break;
			case "right": orientation = "right"; break;
			}

			collisionOn = false;
			gp.cd.checkTile(this);
			gp.cd.checkObject(this, false);
			gp.cd.checkPlayer(this);

			//Collision detection
			if(collisionOn == false) {
				switch(direction) {
				case "up": worldY -= speed; break;
				case "down": worldY += speed; break;
				case "left": worldX -= speed; break;
				case "right": worldX += speed; break;
				}
			}
		}
			
		// Animation loop (This always runs)
		spriteCounter++;
		if(spriteCounter > animationSpeed) {
			spriteIndex = (spriteIndex + 1) % currentSprites.size();
			spriteCounter = 0;
		}
	}

	public void draw(Graphics2D g2){

		BufferedImage image = null;

		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		
		if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && 
				worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
				worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
				worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
			
			if(!currentSprites.isEmpty()){
				image = currentSprites.get(spriteIndex);
			} else {
				image = null;
			}
            
            // --- NEW: Image Flipping Logic ---
            if (image != null) {
                
                // Define the source coordinates (the whole image, not cropped)
                int sourceX1 = 0;
                int sourceY1 = 0;
                int sourceX2 = gp.tileSize;
                int sourceY2 = gp.tileSize;
                
                // Define the destination coordinates (screen location and size)
                int destX1 = screenX;
                int destY1 = screenY;
                int destX2 = screenX + gp.tileSize;
                int destY2 = screenY + gp.tileSize;

                // Flip the image by swapping destination X coordinates if facing left
                if (orientation.equals("left")) {
                    // This flips the image horizontally
                    destX1 = screenX + gp.tileSize; 
                    destX2 = screenX;
                }
                
                // Draw the image using the flipped coordinates if necessary
                g2.drawImage(image, destX1, destY1, destX2, destY2, 
                                     sourceX1, sourceY1, sourceX2, sourceY2, null);
            }
		}
	}
}