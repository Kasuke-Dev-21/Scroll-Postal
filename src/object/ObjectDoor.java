package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class ObjectDoor extends Interactable{
	
	public ObjectDoor() {
		name = "door";
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/door.png"));
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		collision = true;
	}
}
