package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.KeyboardInput;

public class Player extends Entity{
	KeyboardInput key;
	private static final String PLAYER_WALK_SHEET_PATH = "player-walk.png"; 
    private static final String PLAYER_IDLE_SHEET_PATH = "player-idle.png";
	public final int screenX;
	public final int screenY;
	int idleCount = 0;
	public String orientation = "right";

	public int hasMail = 0;
	public int hasScroll = 0;
	public int hasBox = 0;
	public int hasBag = 0;
	public int score = 0;
	
	public Player(GamePanel gp, KeyboardInput key) {
		
		super(gp);
		this.key = key;
		
		screenX = gp.screenWidth/2 - gp.tileSize/2;
		screenY = gp.screenHeight/2 - gp.tileSize/2;
		
		hitbox = new Rectangle();
		hitbox.x = 20;
		hitbox.y = 16;
		hitbox.width = gp.tileSize / 2 - 8;
		hitbox.height = gp.tileSize - 24;
		
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
		String dir = "player";
		//Idle
		for (int i = 0; i < 8; i++) {
            // Note: We use PLAYER_IDLE_SHEET_PATH and row 0
            BufferedImage frame = setupSheet(dir, PLAYER_IDLE_SHEET_PATH, i, 0); 
            idle.add(frame);
        }
		//Walk
		for (int i = 0; i < 14; i++) {
            // Note: We use PLAYER_WALK_SHEET_PATH and row 0
            BufferedImage frame = setupSheet(dir, PLAYER_WALK_SHEET_PATH, i, 0); 
            down.add(frame);
        }
		
		up = down;
        left = down;
        right = down;
        
        // Set the initial current sprites to idle
        currentSprites = idle;
	}
	
	public void update() {
		
		boolean isMoving = key.upPressed || key.downPressed || key.leftPressed || key.rightPressed;

		//Plays only if key is pressed
		if(isMoving) {

			if (key.upPressed) {
                direction = "up";
            } else if (key.downPressed) {
                direction = "down";
            } else if (key.leftPressed) {
                direction = "left";
				orientation = "left";
            } else if (key.rightPressed) {
                direction = "right";
				orientation = "right";
            }
			
			//Player movement logic
			if (currentSprites != down) {
                currentSprites = down;
                animationSpeed = 2;
                spriteIndex = 0; // Reset animation index
            }
			
			
			//Collision detection
			collisionOn = false;
			gp.cd.checkTile(this);
			int objNdx = gp.cd.checkObject(this, true);
			takeObject(objNdx);

			int npcNdx = gp.cd.checkEntity(this, gp.npc);
			interact(npcNdx);
			
			if(collisionOn == false) {
				switch(direction) {
				case "up": worldY -= speed; break;
				case "down": worldY += speed; break;
				case "left": worldX -= speed; break;
				case "right": worldX += speed; break;
				}
			}
			
			super.update();
		
		} else {
			if (currentSprites != idle) {
                currentSprites = idle;
                animationSpeed = 5; // Slower animation speed for idle
                spriteIndex = 0; // Reset animation index
            }
			super.update();
		}
		
	}
	
	public void takeObject(int ndx) {
		
		if(ndx != -1) {
			
			String objName = gp.obj[ndx].name;
			
			switch(objName) {
			case "boot":
				gp.playSFX(3);
				speed += 3;
				gp.obj[ndx] = null;
				gp.ui.showMessage("Speed up!");
				break;
			case "mail":
				hasMail++;
				gp.obj[ndx] = null;
				gp.ui.showMessage("Picked up mail!");
			    break;
			}
		}
	}
	
	
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
        if (!currentSprites.isEmpty()) {
            image = currentSprites.get(spriteIndex); // Get the currently indexed frame
        }

		// Define the source coordinates (the whole image)
        int sourceX1 = 0;
        int sourceY1 = 0;
        int sourceX2 = gp.tileSize;
        int sourceY2 = gp.tileSize;
        
        // Define the destination coordinates (screen location and size)
        int destX1 = screenX;
        int destY1 = screenY;
        int destX2 = screenX + gp.tileSize;
        int destY2 = screenY + gp.tileSize;

		if (orientation.equals("left")) {
            destX1 = screenX + gp.tileSize;
            destX2 = screenX;
        }
		if (image != null) {
            g2.drawImage(image, destX1, destY1, destX2, destY2, sourceX1, sourceY1, sourceX2, sourceY2, null);
        }

		if(key.checkRender == true){
			g2.setColor(Color.red);
			g2.drawRect(screenX + hitbox.x, screenY + hitbox.y, hitbox.width, hitbox.height);
		}
	}



	public void interact(int ndx){
		if(ndx != -1) {
			
		}
	}
}
