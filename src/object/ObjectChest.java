package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class ObjectChest extends Interactable{
	
	public ObjectChest() {
		name = "chest_closed";
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/chest.png"));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}