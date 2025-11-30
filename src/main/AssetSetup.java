package main;

import entity.NPC;

public class AssetSetup {
	
	GamePanel gp;
	
	public AssetSetup(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setObject() {
	}

	public void setNPC(){

		gp.npc[0] = new NPC(gp, 1);
		gp.npc[0].worldX = gp.tileSize * 24;
		gp.npc[0].worldY = gp.tileSize * 7;
	}
}
