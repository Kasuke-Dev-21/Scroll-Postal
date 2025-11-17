package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class ObjectBoots extends Interactable{

	
	public ObjectBoots() {
		name = "boot";
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/boots.png"));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
