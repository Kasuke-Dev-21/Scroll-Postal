package main;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class Util {
    
    public BufferedImage scaleImage(BufferedImage og, int width, int height){
        BufferedImage scaled = new BufferedImage(width, height, og.getType());
		Graphics2D g2 = scaled.createGraphics();
		g2.drawImage(og, 0, 0, width, height, null);
        g2.dispose();

        return scaled;
    }
}
