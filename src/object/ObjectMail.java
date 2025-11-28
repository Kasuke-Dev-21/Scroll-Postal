package object;

import javax.imageio.ImageIO;

import main.GamePanel;

public class ObjectMail extends Interactable {

    public ObjectMail(GamePanel gp) {
        super();

        name = "mail";

        // Placeholder sprite
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/mail"));;
        } catch (Exception e) {
            e.printStackTrace();
        }

        collision = false;
        
    }
}

