package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import object.ObjectKey;

public class GUI {

	GamePanel gp;
	Font iconFont;
	BufferedImage keyIcon;
	
	public boolean messageOn = false;
	public String message = "";
	int messageLifetime = 0;
	
	public GUI(GamePanel gp) {
		this.gp = gp;
		iconFont = new Font("Cambria", Font.PLAIN, 40);
		ObjectKey key = new ObjectKey();
		keyIcon = key.image;
		
	}
	
	public void showMessage(String text) {
		
		message = text;
		messageOn = true;
	}
	
	public void draw(Graphics2D g2) {
		
		g2.setFont(iconFont);
		g2.setColor(Color.white);
		g2.drawImage(keyIcon, gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize, null);
		g2.drawString(" = " + gp.player.hasKey, 72, 62);
		
		
		if(messageOn) {
			
			g2.setFont(g2.getFont().deriveFont(30F));
			g2.drawString(message, gp.tileSize/2, gp.tileSize*5);
			
			messageLifetime++;
			if(messageLifetime > 120) {
				messageLifetime = 0;
				messageOn = false;
			}
		}
	}
}
