package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class ObjectDoor extends Interactable{
	
	GamePanel gp;

	public ObjectDoor(GamePanel gp) {
		name = "door";
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/door.png"));
			tool.scaleImage(image, gp.tileSize, gp.tileSize);
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		collision = true;
	}
}
