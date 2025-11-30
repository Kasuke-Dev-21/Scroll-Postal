package entity;

import java.util.Random;
import main.GamePanel;

public class NPC extends Entity{
    
    // Assuming a spritesheet specific to this NPC
    private static final String NPC_SHEET_PATH = "npc_spritesheet.png"; 

    public NPC(GamePanel gp, int spd){
        super(gp);

        direction = "down";
        speed = spd;
        
        getImage();
        currentSprites = down; // Start with the down animation
        animationSpeed = 12; // Standard walking speed
    }

    public void getImage() {
        String dir = "npc";
        
        // --- ASSUMING NPC SPRITESHEET LAYOUT (2 frames per direction, 4 directions) ---
        // Row 0: Up (0,0 to 1,0)
        // Row 1: Down (0,1 to 1,1)
        // Row 2: Left (0,2 to 1,2)
        // Row 3: Right (0,3 to 1,3)

        // UP (Row 0)
        up.add(setupSheet(dir, NPC_SHEET_PATH, 0, 0));
        up.add(setupSheet(dir, NPC_SHEET_PATH, 1, 0));
        
        // DOWN (Row 1)
        down.add(setupSheet(dir, NPC_SHEET_PATH, 0, 1));
        down.add(setupSheet(dir, NPC_SHEET_PATH, 1, 1));
        
        // LEFT (Row 2)
        left.add(setupSheet(dir, NPC_SHEET_PATH, 0, 2));
        left.add(setupSheet(dir, NPC_SHEET_PATH, 1, 2));
        
        // RIGHT (Row 3)
        right.add(setupSheet(dir, NPC_SHEET_PATH, 0, 3));
        right.add(setupSheet(dir, NPC_SHEET_PATH, 1, 3));
    }

    @Override
    public void setAction(){

        actionLockCounter++;

        if(actionLockCounter == 120) {
            Random random = new Random();
            int ctr = random.nextInt(100) + 1;

            String oldDirection = direction;
            
            // Randomly set the direction
            if(ctr <= 25){
                direction = "up";
            } else if(ctr > 25 && ctr <= 50){
                direction = "down";
            } else if(ctr > 50 && ctr <= 75){
                direction = "left";
            } else if(ctr > 75 && ctr <= 100){
                direction = "right";
            }
            
            // Update currentSprites based on the new direction
            if (!direction.equals(oldDirection)) {
                switch (direction) {
                    case "up": currentSprites = up; break;
                    case "down": currentSprites = down; break;
                    case "left": currentSprites = left; break;
                    case "right": currentSprites = right; break;
                }
                spriteIndex = 0; // Reset animation when direction changes
            }

            actionLockCounter = 0;
        }
    }
}
