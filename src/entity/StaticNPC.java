package entity;

import main.GamePanel;
import java.awt.image.BufferedImage;

public class StaticNPC extends Entity{

    public String name;
    
    // Define the file path for your single spritesheet
    private static final int FRAME_COUNT = 2;

    public StaticNPC(GamePanel gp, String name){
        super(gp);

        this.name = name;
        direction = "down"; 
        speed = 0;
        

        getImage(name + ".png");
        currentSprites = idle;
        animationSpeed = 30;
    }

    public void getImage(String path) {
        String dir = "npc";
        
        for (int i = 0; i < FRAME_COUNT; i++) {
            BufferedImage frame = setupSheet(dir, path, i, 0); 
            idle.add(frame);
        }
        
        down = idle;
        up = idle;
        left = idle;
        right = idle;
    }

    // setAction is left empty since the NPC is static.
    @Override
    public void setAction(){}
}