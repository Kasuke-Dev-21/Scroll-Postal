package object;

import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;

public class ObjectBoots extends Interactable{

	GamePanel gp;

	public ObjectBoots(GamePanel gp) {
		name = "boot";
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/boots.png"));
			tool.scaleImage(image, gp.tileSize, gp.tileSize);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
