package main;

import entity.NPC;
import entity.Building;

public class EntityLoader {

    GamePanel gp;

    public EntityLoader(GamePanel gp) {
        this.gp = gp;
    }

    // Load NPCs and Buildings
    public void loadEntities() {
        loadNPCs();
        loadBuildings();
    }

    private void loadNPCs() {
        gp.npcList.clear();

        // Example NPCs (worldX, worldY in pixels)
        gp.npcList.add(new NPC(gp, gp.tileSize * 20, gp.tileSize * 20));
        gp.npcList.add(new NPC(gp, gp.tileSize * 23, gp.tileSize * 18));
        gp.npcList.add(new NPC(gp, gp.tileSize * 25, gp.tileSize * 22));
    }

    private void loadBuildings() {
        gp.buildingList.clear();

        // Example Buildings (worldX, worldY, width, height, type)
        gp.buildingList.add(new Building(gp, gp.tileSize * 10, gp.tileSize * 10, gp.tileSize * 3, gp.tileSize * 3, "House"));
        gp.buildingList.add(new Building(gp, gp.tileSize * 12, gp.tileSize * 15, gp.tileSize * 4, gp.tileSize * 2, "Shop"));
        gp.buildingList.add(new Building(gp, gp.tileSize * 18, gp.tileSize * 8, gp.tileSize * 5, gp.tileSize * 4, "Barn"));
    }
}
