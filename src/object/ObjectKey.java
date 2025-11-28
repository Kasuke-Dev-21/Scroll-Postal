package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class ObjectKey extends Interactable{
	
	GamePanel gp;

	public ObjectKey(GamePanel gp) {
		name = "key";
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/key.png"));
			tool.scaleImage(image, gp.tileSize, gp.tileSize);
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		collision = true;
	}
}

