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
	
	public int checkObject(Entity e, boolean player) {
		
		int ndx = -1;
		
		for(int ctr = 0; ctr < gp.obj.length; ctr++) {
			if (gp.obj[ctr] != null){
				
				//get hitbox positions
				e.hitbox.x += e.worldX;
				e.hitbox.y += e.worldY;
				
				gp.obj[ctr].hitbox.x += gp.obj[ctr].worldX;
				gp.obj[ctr].hitbox.y += gp.obj[ctr].worldY;
				
				switch(e.direction) {
				case("up"):
					e.hitbox.y -= e.speed;
					if(e.hitbox.intersects(gp.obj[ctr].hitbox)) {
						if(gp.obj[ctr].collision) {
							e.collisionOn = true;
						}
						if(player) {
							ndx = ctr;
						}
					}
					break;
					
				case("down"):
					e.hitbox.y += e.speed;
					if(e.hitbox.intersects(gp.obj[ctr].hitbox)) {
						if(gp.obj[ctr].collision) {
							e.collisionOn = true;
						}
						if(player) {
							ndx = ctr;
						}
					}
					break;
					
				case("left"):
					e.hitbox.x -= e.speed;
					if(e.hitbox.intersects(gp.obj[ctr].hitbox)) {
						if(gp.obj[ctr].collision) {
							e.collisionOn = true;
						}
						if(player) {
							ndx = ctr;
						}
					}
					break;
					
				case("right"):
					e.hitbox.x += e.speed;
					if(e.hitbox.intersects(gp.obj[ctr].hitbox)) {
						if(gp.obj[ctr].collision) {
							e.collisionOn = true;
						}
						if(player) {
							ndx = ctr;
						}
					}
					break;
				
				}
				e.hitbox.x = e.hitboxDefaultX;
				e.hitbox.y = e.hitboxDefaultY;
				
				gp.obj[ctr].hitbox.x = gp.obj[ctr].hitboxDefaultX;
				gp.obj[ctr].hitbox.y = gp.obj[ctr].hitboxDefaultY;
			}
		}
		
		return ndx;
	}

	//ENTITY COLLISION
	public int checkEntity(Entity e, Entity[] target){
		int ndx = -1;
		
		for(int ctr = 0; ctr < target.length; ctr++) {
			if (target[ctr] != null){
				
				//get hitbox positions
				e.hitbox.x += e.worldX;
				e.hitbox.y += e.worldY;
				
				target[ctr].hitbox.x += target[ctr].worldX;
				target[ctr].hitbox.y += target[ctr].worldY;
				
				switch(e.direction) {
				case("up"):
					e.hitbox.y -= e.speed;
					if(e.hitbox.intersects(target[ctr].hitbox)) {
						e.collisionOn = true;
						ndx = ctr;
					}
					break;
					
				case("down"):
					e.hitbox.y += e.speed;
					if(e.hitbox.intersects(target[ctr].hitbox)) {
						e.collisionOn = true;
						ndx = ctr;
					}
					break;
					
				case("left"):
					e.hitbox.x -= e.speed;
					if(e.hitbox.intersects(target[ctr].hitbox)) {
						e.collisionOn = true;
						ndx = ctr;
					}
					break;
					
				case("right"):
					e.hitbox.x += e.speed;
					if(e.hitbox.intersects(target[ctr].hitbox)) {
						e.collisionOn = true;
						ndx = ctr;
					}
					break;
				
				}
				e.hitbox.x = e.hitboxDefaultX;
				e.hitbox.y = e.hitboxDefaultY;
				
				target[ctr].hitbox.x = target[ctr].hitboxDefaultX;
				target[ctr].hitbox.y = target[ctr].hitboxDefaultY;
			}
		}
		
		return ndx;
	}

	public void checkPlayer(Entity e){
		//get hitbox positions
		e.hitbox.x += e.worldX;
		e.hitbox.y += e.worldY;
		
		gp.player.hitbox.x += gp.player.worldX;
		gp.player.hitbox.y += gp.player.worldY;
		
		switch(e.direction) {
		case("up"):
			e.hitbox.y -= e.speed;
			if(e.hitbox.intersects(gp.player.hitbox)) {
				e.collisionOn = true;
			}
			break;
			
		case("down"):
			e.hitbox.y += e.speed;
			if(e.hitbox.intersects(gp.player.hitbox)) {
				e.collisionOn = true;
			}
			break;
			
		case("left"):
			e.hitbox.x -= e.speed;
			if(e.hitbox.intersects(gp.player.hitbox)) {
				e.collisionOn = true;
			}
			break;
			
		case("right"):
			e.hitbox.x += e.speed;
			if(e.hitbox.intersects(gp.player.hitbox)) {
				e.collisionOn = true;
			}
			break;
		
		}
		e.hitbox.x = e.hitboxDefaultX;
		e.hitbox.y = e.hitboxDefaultY;
		
		gp.player.hitbox.x = gp.player.hitboxDefaultX;
		gp.player.hitbox.y = gp.player.hitboxDefaultY;
	}
}
