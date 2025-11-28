package main;

import java.awt.Rectangle;

import entity.Building;
import entity.NPC;
import entity.Player;

public class InteractManager {

    GamePanel gp;

    public InteractManager(GamePanel gp) {
        this.gp = gp;
    }

    public void handleInteractions() {
        Player player = gp.player;

        if (gp.key.interactTyped) {
            Rectangle playerHitbox = new Rectangle(
                    player.worldX + player.hitbox.x,
                    player.worldY + player.hitbox.y,
                    player.hitbox.width,
                    player.hitbox.height);

            // NPC interactions
            for (NPC npc : gp.npcs) {
                if (npc != null) {
                    Rectangle npcArea = new Rectangle(
                            npc.worldX + npc.hitbox.x,
                            npc.worldY + npc.hitbox.y,
                            npc.hitbox.width,
                            npc.hitbox.height);

                    if (playerHitbox.intersects(npcArea)) {
                        npc.interact(player, gp);
                    }
                }
            }

            // Building interactions
            for (Building b : gp.buildings) {
                if (b != null) {
                    Rectangle bArea = new Rectangle(
                            b.worldX + b.hitbox.x,
                            b.worldY + b.hitbox.y,
                            b.hitbox.width,
                            b.hitbox.height);

                    if (playerHitbox.intersects(bArea)) {
                        b.interact(player, gp);
                    }
                }
            }
        }
    }
}
