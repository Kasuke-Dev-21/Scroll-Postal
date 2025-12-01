package entity;

import java.util.Random;
import main.GamePanel;
import java.awt.image.BufferedImage;


public class NPC extends Entity{
    
    // Assuming a spritesheet specific to this NPC
    private static final String NPC_WALK_SHEET_PATH = "npc-walk.png"; 
    private static final String NPC_IDLE_SHEET_PATH = "npc-idle.png";

    public boolean isMoving = false; 

    public NPC(GamePanel gp, int spd){
        super(gp);

        direction = "down";
        speed = spd;
        
        getImage();
        currentSprites = idle;
        animationSpeed = 12;
    }

    public void getImage() {
        String dir = "npc";

        for (int i = 0; i < 8; i++) { // Assuming 8 frames for idle
            BufferedImage frame = setupSheet(dir, NPC_IDLE_SHEET_PATH, i, 0); 
            idle.add(frame);
        }
        
        for (int i = 0; i < 14; i++) { // Assuming 14 frames for walking
            BufferedImage frame = setupSheet(dir, NPC_WALK_SHEET_PATH, i, 0); 
            down.add(frame);
        }

        up = down;
        left = down;
        right = down;

    }

    @Override
    public void setAction(){
    actionLockCounter++;

        if(actionLockCounter == 120) {
            Random random = new Random();
            int ctr = random.nextInt(100) + 1;

            if (ctr <= 20) { // 20% chance to stand still
                isMoving = false;
                direction = "idle";
            } else { // 80% chance to move
                isMoving = true;
                
                if(ctr > 20 && ctr <= 40){
                    direction = "up";
                } else if(ctr > 40 && ctr <= 60){
                    direction = "down";
                } else if(ctr > 60 && ctr <= 80){
                    direction = "left";
                } else if(ctr > 80 && ctr <= 100){
                    direction = "right";
                }
            }
            
            if (isMoving) {
                // Moving State
                currentSprites = down;
                animationSpeed = 12; 
            } else {
                // Idle State
                currentSprites = idle;
                animationSpeed = 30; 
            }

            spriteIndex = 0; // Reset animation index
            actionLockCounter = 0;
        }
    }

    public void update() {
        
        setAction();
        String newDirection = direction;
        
        if (isMoving) {
            collisionOn = false;
            gp.cd.checkTile(this);
            gp.cd.checkObject(this, false); 
            gp.cd.checkEntity(this, gp.npc); 
            gp.cd.checkPlayer(this);
            
            // collision check
            if(collisionOn == false) {
                switch(direction) {
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }
        }
        
        super.update();
    }
}
