package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyboardInput;
import main.Util;

public class Player extends Entity{

	GamePanel gp;
	KeyboardInput key;
	public final int screenX;
	public final int screenY;
	public int hasKey = 0;
	public boolean hasMail = false; // new field
	public int score = 0;           // track score
	
	public Player(GamePanel gp, KeyboardInput key) {
		
		this.gp = gp;
		this.key = key;
		
		screenX = gp.screenWidth/2 - gp.tileSize/2;
		screenY = gp.screenHeight/2 - gp.tileSize/2;
		
		hitbox = new Rectangle();
		hitbox.x = 8;
		hitbox.y = 14;
		hitbox.width = gp.tileSize - 18;
		hitbox.height = gp.tileSize - 16;
		
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		
		setDefault();
		getImage();
		
	}
	
	public void setDefault() {
		
		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 21;
		speed = 4;
		direction = "down";
		
	}
	
	public void getImage() {
		up1 = setup("boy_up_1.png");
		up2 = setup("boy_up_2.png");
		down1 = setup("boy_down_1.png");
		down2 = setup("boy_down_2.png");
		left1 = setup("boy_left_1.png");
		left2 = setup("boy_left_2.png");
		right1 = setup("boy_right_1.png");
		right2 = setup("boy_right_2.png");
	}

	public BufferedImage setup(String imagePath){
		
		Util tool = new Util();
		BufferedImage image = null;

		try{
			image = ImageIO.read(getClass().getResourceAsStream("/player/" + imagePath));
			image = tool.scaleImage(image, gp.tileSize, gp.tileSize);
		} catch(IOException e){
			e.printStackTrace();
		}

		return image;
	}
	
	public void update() {
		
		//Plays only if key is pressed
		if(key.upPressed == true || key.downPressed == true || 
				key.leftPressed == true || key.rightPressed == true) {
			
			//Player movement logic
			if(key.upPressed == true) {
				direction = "up";
			} else if(key.downPressed == true) {
				direction = "down";
			} else if(key.leftPressed == true) {
				direction  ="left";
			} else if(key.rightPressed == true) {
				direction = "right";
			}
			
			//Collision detection
			collisionOn = false;
			gp.cd.checkTile(this);
			int objNdx = gp.cd.checkObject(this, true);
			takeObject(objNdx);
			
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
		
	}
	
	public void takeObject(int ndx) {
		
		if(ndx != -1) {
			
			String objName = gp.obj[ndx].name;
			
			switch(objName) {
			case "key":
				gp.playSFX(2);
				hasKey++;
				gp.obj[ndx] = null;
				gp.ui.showMessage("Key obtained!");
				break;
			case "door":
				if(hasKey > 0) {
					gp.playSFX(4);
					gp.obj[ndx] = null;
					hasKey--;
					gp.ui.showMessage("Door opened!");
				} else {
					gp.ui.showMessage("Key needed!");
				}
				break;
			case "boot":
				gp.playSFX(3);
				speed += 3;
				gp.obj[ndx] = null;
				gp.ui.showMessage("Speed up!");
				break;
			case "mail":
			    if(!hasMail){
			        hasMail = true;
			        gp.obj[ndx] = null;
			        gp.ui.showMessage("Picked up mail!");
			    }
			    break;
			}
		}
	}
	
	
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
		
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
