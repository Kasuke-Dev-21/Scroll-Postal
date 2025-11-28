package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class ObjectMail extends Interactable{
	
	GamePanel gp;

	public ObjectMail(GamePanel gp) {
		name = "mail";
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/mail.png"));
			tool.scaleImage(image, gp.tileSize, gp.tileSize);
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		collision = true;
	}
}


