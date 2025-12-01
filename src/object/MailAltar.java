package object;

import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;

public class MailAltar extends Interactable{
    GamePanel gp;

	public MailAltar(GamePanel gp) {
		name = "altar";
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/altar.png"));
			tool.scaleImage(image, gp.tileSize, gp.tileSize);
		} catch(IOException e) {
			e.printStackTrace();
		}

		collision = true;
	}
}
