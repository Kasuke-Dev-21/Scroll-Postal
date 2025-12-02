package main;
import java.util.Random;

import entity.NPC;
import entity.StaticNPC;
import object.MailAltar;

public class AssetSetup {
	
	GamePanel gp;
	Random random = new Random();
	
	public AssetSetup(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setObject() {
		gp.obj[0] = new MailAltar(gp);
		gp.obj[0].worldX = gp.tileSize * 23; // Set its location
		gp.obj[0].worldY = gp.tileSize * 38;
	}

	public void setNPC(){

		gp.npc[0] = new StaticNPC(gp, "fisher"); 
		gp.npc[0].worldX = gp.tileSize * 24; // Set its location
		gp.npc[0].worldY = gp.tileSize * 7;

		for (int i = 0; i < 10; i++) {
            // Find a valid spawn location for each Roaming NPC
			int ndx = 1 + i;
            int[] spawnCoords = getValidSpawnLocation();
            
            gp.npc[ndx] = new NPC(gp, 1); 
            gp.npc[ndx].worldX = spawnCoords[0]; 
            gp.npc[ndx].worldY = spawnCoords[1];
        }
	}

	public int[] getValidSpawnLocation() {
        
        int maxWorldCol = gp.maxWorldCol;
        int maxWorldRow = gp.maxWorldRow;
        int tileSize = gp.tileSize;
        
        final int GRASS_TILE_INDEX = 0; 
        
        // Attempt to find a valid location
        int attempts = 0;
        while (attempts < 1000) { // Safety loop to prevent infinite loop
            
            int randomCol = random.nextInt(maxWorldCol);
            int randomRow = random.nextInt(maxWorldRow);
            
            if (gp.TM.tileNo[randomCol][randomRow] == GRASS_TILE_INDEX) {
                
                int worldX = randomCol * tileSize;
                int worldY = randomRow * tileSize;
                
                // Return the World Coordinates in pixels
                return new int[]{worldX, worldY};
            }
            attempts++;
        }
        
        // Fallback: If after 1000 attempts, no spot is found (e.g., small map)
        // This places the NPC at a default safe location (e.g., 20, 20)
        return new int[]{gp.tileSize * 20, gp.tileSize * 20};
    }
}
