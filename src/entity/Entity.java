package entity;

import java.awt.Rectangle;
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
	
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	public String direction;
	
	public int spriteCounter = 0;
	public int spriteNum = 1;
	
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

	public void setAction(){}

	public void update(){

		setAction();

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
			
			//Animation loop
			spriteCounter++;
			if(spriteCounter > 12) {
				switch(spriteNum) {
				case 1:
					spriteNum = 2; break;
				case 2:
					spriteNum = 1; break;
				}
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
			
			switch(direction) {
			case "up":
				switch(spriteNum) {
				case 1: image = up1; break;
				case 2: image = up2; break;
				}
				break;
			case "down":
				switch(spriteNum) {
				case 1: image = down1; break;
				case 2: image = down2; break;
				}
				break;
			case "left":
				switch(spriteNum) {
				case 1: image = left1; break;
				case 2: image = left2; break;
				}
				break;
			case "right":
				switch(spriteNum) {
				case 1: image = right1; break;
				case 2: image = right2; break;
				}
				break;
			}

			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
		}
		
	}

}
