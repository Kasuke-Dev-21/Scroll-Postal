package entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import main.GamePanel;

public class Building extends Entity {

    public String type;

    public Building(GamePanel gp, int worldX, int worldY, int width, int height, String type) {
        super();
        this.worldX = worldX;
        this.worldY = worldY;
        this.type = type;

        hitbox = new Rectangle(0, 0, width, height);
        collisionOn = true;
    }

    public void update() {
        // Static building, no update logic yet
    }

    public void draw(GamePanel gp) {
    	BufferedImage[] sprite = new BufferedImage[1];
        sprite[0] = new BufferedImage(gp.tileSize, gp.tileSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = sprite[0].createGraphics();
        g2.setColor(Color.RED);
        g2.fillRect(0, 0, gp.tileSize, gp.tileSize);
        g2.dispose();
    }

    public void interact(Player player, GamePanel gp) {
        if (type.equals("Post Office")) {
            if (!player.hasMail) {
                player.hasMail = true;
                gp.ui.showMessage("Picked up mail!");
            } else {
                gp.ui.showMessage("You already have mail!");
            }
        }
    }
}