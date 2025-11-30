package main;
import entity.StaticNPC;

public class AssetSetup {
	
	GamePanel gp;
	
	public AssetSetup(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setObject() {
	}

	public void setNPC(){

		gp.npc[0] = new StaticNPC(gp); 
		gp.npc[0].worldX = gp.tileSize * 24; // Set its location
		gp.npc[0].worldY = gp.tileSize * 7;
	}
}
