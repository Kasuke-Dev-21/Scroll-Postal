package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.Util;

public class TileManager {

	
	GamePanel gp;
	public Tile[] tile;
	String[] files = {"grass.png", "wall.png", "water.png", "earth.png", "tree.png", "sand.png"};
	public int tileNo[][];
	
	public TileManager(GamePanel gp) {
		
		this.gp = gp;
		
		tile = new Tile[10];
		
		tileNo = new int[gp.maxWorldCol][gp.maxWorldRow];
		
		getTileImage();
		
		loadMap("/maps/worldmap.txt");
		
	}
	
	public void getTileImage() {
		for(int x = 0; x < 6; x++) {
			boolean collision = false;

			switch(x) {
			case 1:
			case 2:
			case 4:
				tile[x].collision = true; break;
			}

			setup(x, files[x], collision);
		}		
		
	}
	
	public void setup(int index, String imagePath, boolean collision){
		Util tool = new Util();
		
		try{
			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imagePath));
			tile[index].image = tool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
			tile[index].collision = collision;
		} catch(IOException e){
			e.printStackTrace();
		}

	}

	public void loadMap(String map) {
		
		try {
			
			InputStream is = getClass().getResourceAsStream(map);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			
			for(int row = 0; row < gp.maxWorldRow; row++) {
				
				String line = br.readLine();
				for(int col = 0; col < gp.maxWorldCol; col++) {
					
					String numbers[] = line.split(" ");
					
					int no = Integer.parseInt(numbers[col]);
					
					tileNo[col][row] = no;
				}
				
			}
			br.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void draw(Graphics2D g2) {
		
		
		for(int worldRow = 0; worldRow < gp.maxWorldRow; worldRow++) {
			for(int worldCol = 0; worldCol < gp.maxWorldCol; worldCol++) {
				
				int tileNum = tileNo[worldCol][worldRow];
				
				int worldX = worldCol * gp.tileSize;
				int worldY = worldRow * gp.tileSize;
				
				int screenX = worldX - gp.player.worldX + gp.player.screenX;
				int screenY = worldY - gp.player.worldY + gp.player.screenY;
				
				if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && 
						worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
						worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
						worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
					
					g2.drawImage(tile[tileNum].image, screenX, screenY, null);
					
				}
				
			}
		}
		
	}
}
