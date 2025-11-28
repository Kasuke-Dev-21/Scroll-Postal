package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class ObjectChest extends Interactable{
	
	GamePanel gp;

	public ObjectChest(GamePanel gp) {
		name = "chest_closed";
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/chest.png"));
			tool.scaleImage(image, gp.tileSize, gp.tileSize);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}