package entity;

import java.util.Random;

import main.GamePanel;

public class NPC extends Entity{
    
    public NPC(GamePanel gp, int spd){
        super(gp);

        direction = "down";
        speed = spd;
        
        getImage();
    }

    public void getImage() {
		up1 = setup("npc","oldman_up_1.png");
		up2 = setup("npc","oldman_up_2.png");
		down1 = setup("npc","oldman_down_1.png");
		down2 = setup("npc","oldman_down_2.png");
		left1 = setup("npc","oldman_left_1.png");
		left2 = setup("npc","oldman_left_2.png");
		right1 = setup("npc","oldman_right_1.png");
		right2 = setup("npc","oldman_right_2.png");
	}

    public void setAction(){

        actionLockCounter++;

        if(actionLockCounter == 120) {
            Random random = new Random();
            int ctr = random.nextInt(100) + 1; //RNG 1 - 100

            if(ctr <= 25){
                direction = "up";
            }
            if(ctr > 25 && ctr <= 50){
                direction = "down";
            }
            if(ctr > 50 && ctr <= 75){
                direction = "left";
            }
            if(ctr > 75 && ctr <= 100){
                direction = "right";
            }

            actionLockCounter = 0;
        }
	}
    
}
