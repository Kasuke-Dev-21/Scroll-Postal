package main;

import entity.Entity;

public class CollisionCheck {
	
	GamePanel gp;

	public CollisionCheck(GamePanel gp) {
		this.gp = gp;
		
	}
	
	public void checkTile(Entity e) {
		
		int hitLeftX = e.worldX + e.hitbox.x;
		int hitRightX = e.worldX + e.hitbox.x + e.hitbox.width;
		int hitTopY = e.worldY + e.hitbox.y;
		int hitBottomY = e.worldY + e.hitbox.y + e.hitbox.height;
		
		int hitLeftCol = hitLeftX/gp.tileSize;
		int hitRightCol = hitRightX/gp.tileSize;
		int hitTopRow = hitTopY/gp.tileSize;
		int hitBottomRow = hitBottomY/gp.tileSize;
		
		int tileNum1, tileNum2;
		
		switch(e.direction) {
		case("up"):
			hitTopRow = (hitTopY - e.speed)/gp.tileSize;
			tileNum1 = gp.TM.tileNo[hitLeftCol][hitTopRow];
			tileNum2 = gp.TM.tileNo[hitRightCol][hitTopRow];
			if(gp.TM.tile[tileNum1].collision == true || gp.TM.tile[tileNum2].collision == true) {
				e.collisionOn = true;
			}
			break;
			
		case("down"):
			hitBottomRow = (hitBottomY + e.speed)/gp.tileSize;
			tileNum1 = gp.TM.tileNo[hitLeftCol][hitBottomRow];
			tileNum2 = gp.TM.tileNo[hitRightCol][hitBottomRow];
			if(gp.TM.tile[tileNum1].collision == true || gp.TM.tile[tileNum2].collision == true) {
				e.collisionOn = true;
			}
			break;
			
		case("left"):
			hitLeftCol = (hitLeftX - e.speed)/gp.tileSize;
			tileNum1 = gp.TM.tileNo[hitLeftCol][hitTopRow];
			tileNum2 = gp.TM.tileNo[hitLeftCol][hitBottomRow];
			if(gp.TM.tile[tileNum1].collision == true || gp.TM.tile[tileNum2].collision == true) {
				e.collisionOn = true;
			}
			break;
			
		case("right"):
			hitRightCol = (hitRightX + e.speed)/gp.tileSize;
			tileNum1 = gp.TM.tileNo[hitRightCol][hitTopRow];
			tileNum2 = gp.TM.tileNo[hitRightCol][hitBottomRow];
			if(gp.TM.tile[tileNum1].collision == true || gp.TM.tile[tileNum2].collision == true) {
				e.collisionOn = true;
			}
			break;
		
		}
		
	}
}
