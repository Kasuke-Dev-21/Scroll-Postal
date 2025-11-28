package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class NPC extends Entity {

    public boolean hasRequest = true; // always wants mail for now

    public NPC(GamePanel gp, int worldX, int worldY) {
        super();
        String name;
        this.worldX = worldX;
        this.worldY = worldY;

        hitbox = new Rectangle(0, 0, gp.tileSize, gp.tileSize);
        collisionOn = true;
    }
    

    public void update() {
        
    }

    public void draw(Graphics2D g2, GamePanel gp) {
        g2.setColor(Color.BLUE);
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        if(screenX + gp.tileSize > 0 && screenX - gp.tileSize < gp.screenWidth &&
           screenY + gp.tileSize > 0 && screenY - gp.tileSize < gp.screenHeight) {
            g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);
        }
    }

    public void interact(Player player, GamePanel gp) {
        if (hasRequest && player.hasMail) {
            player.hasMail = false;
            hasRequest = false;
            player.score += 50; // reward points
            gp.ui.showMessage("Delivered mail! +50 points");
        } else if (hasRequest && !player.hasMail) {
            gp.ui.showMessage("This NPC wants mail!");
        }
    }
}
