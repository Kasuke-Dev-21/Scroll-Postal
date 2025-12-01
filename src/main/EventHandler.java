package main;

public class EventHandler {
    
    GamePanel gp;

    int interactionDistance = 1; 

    public EventHandler(GamePanel gp) {
        this.gp = gp;
    }

    public void checkEvent(int worldX, int worldY, String eventName) {
        
        // This method will be triggered by the Player class when 'E' is pressed
        
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
}
