package main;
import java.awt.Point;
import java.util.Random;

import entity.Entity;
import entity.NPC;

public class EventHandler {
    
    GamePanel gp;

    int interactionDistance = 1; 
    Random random = new Random();
    final int MIN_RELOCATION_DISTANCE_TILES = 15;

    public EventHandler(GamePanel gp) {
        this.gp = gp;
    }

    public void checkEvent(int worldX, int worldY, String eventName) {        
        if (eventName.equals("altar")) {
            mailAltarEvent(worldX, worldY);
        }
    }
    
    public void mailAltarEvent(int eventWorldX, int eventWorldY) {
        int playerTileX = gp.player.worldX / gp.tileSize;
        int playerTileY = gp.player.worldY / gp.tileSize;
        
        int eventTileX = eventWorldX / gp.tileSize;
        int eventTileY = eventWorldY / gp.tileSize;
        
        // Check for adjacency (within 1 tile)
        boolean isAdjacent = (Math.abs(playerTileX - eventTileX) <= interactionDistance) && 
                             (Math.abs(playerTileY - eventTileY) <= interactionDistance);

        if (isAdjacent) {
            
            // Interaction Logic: Convert Scroll to Mail
            if (gp.player.scrollCount > 0) {
                gp.player.scrollCount--;
                gp.player.mailCount++;
                gp.ui.showMessage("Scroll converted to Mail!");
                gp.playSFX(2);
            } else {
                gp.ui.showMessage("Not enough scrolls.");
            }
        }
    }

    public void checkNPCInteraction(int npcIndex) {
        
        Entity target = gp.npc[npcIndex];
        
        if (target instanceof NPC roamingNPC) {
            
            String message = "";
            int sfxIndex = -1;
            boolean interactionHappened = false;

            // get scrolls
            if (roamingNPC.canGiveScroll) {
                gp.player.scrollCount++;
                roamingNPC.canGiveScroll = false;
                sfxIndex = 2; 
                interactionHappened = true;
            } 
            // give mail
            else if (gp.player.mailCount > 0) {
                
                gp.player.mailCount--;
                double givePoint = 100 * gp.player.scoreMult;
                gp.player.score += (int)givePoint;
                
                relocateRoamingNPC(roamingNPC);
                roamingNPC.canGiveScroll = true; // Reset scroll giving status

                message = "Delivered mail! +" + (int)givePoint + "!";
                sfxIndex = 3; 
                interactionHappened = true;
            }
            // default response
            else {
                message = "Sorry, I don't have a scroll anymore.";
            }

            if (interactionHappened) {
                gp.playSFX(sfxIndex); 
            }
            gp.ui.showMessage(message);
        }
    }

    private void relocateRoamingNPC(NPC npc){
        // Use the same logic from AssetSetup to find a grass tile
        int maxWorldCol = gp.maxWorldCol;
        int maxWorldRow = gp.maxWorldRow;
        int tileSize = gp.tileSize;
        final int GRASS_TILE_INDEX = 0; 
        Point startTile = new Point(npc.worldX / tileSize, npc.worldY / tileSize);
        
        int attempts = 0;
        boolean valid = false;
        while (attempts < 1000 && !valid) { 
            
            int randomCol = random.nextInt(maxWorldCol);
            int randomRow = random.nextInt(maxWorldRow);
            
            if (gp.TM.tileNo[randomCol][randomRow] == GRASS_TILE_INDEX) {

                double distance = startTile.distance(randomCol, randomRow);
                
                if (distance >= MIN_RELOCATION_DISTANCE_TILES) {
                    
                    npc.worldX = randomCol * tileSize;
                    npc.worldY = randomRow * tileSize;
                    
                    // Reset NPC's state
                    npc.isMoving = false;
                    npc.direction = "idle";
                    npc.currentSprites = npc.idle;
                    npc.animationSpeed = 30;
                    
                    valid = true;
                }
            }
            attempts++;
        }
        
        // Fallback
        if (!valid) {
             // Just reset the NPC's state, keeping it in its current area
             npc.isMoving = false;
             npc.direction = "idle";
             npc.currentSprites = npc.idle;
             npc.animationSpeed = 30;
        }
    }
}
