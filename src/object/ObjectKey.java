package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class ObjectKey extends Interactable{
	
	public ObjectKey() {
		name = "key";
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/key.png"));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
