package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.IOException;
import java.io.InputStream;
import java.awt.BasicStroke;
import java.text.DecimalFormat;


public class GUI {

	GamePanel gp;
	Graphics2D g2;
	Font pixType, testType;
	
	public boolean messageOn = false;
	public String message = "";
	int messageLifetime = 0;

	double playTime = 600;
	DecimalFormat timeFormat = new DecimalFormat("#0");

	Font contentFont; 
	
	public String helpContent = "";
	
	public GUI(GamePanel gp) {
		this.gp = gp;
		
		try {
			InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
			pixType = Font.createFont(Font.TRUETYPE_FONT, is);
			is = getClass().getResourceAsStream("/font/Purisa Bold.ttf");
			testType = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch(FontFormatException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		pixType = pixType.deriveFont(Font.PLAIN, 40F);
		contentFont = pixType.deriveFont(Font.PLAIN, 24F);
		setHelpContent(); 
	}
	
	public void setHelpContent() {
		helpContent = 
			"=== HELP SCREEN ===\n" +
			"W, A, S, D : Movement\n" +
			"E : Give mail to NPCs\n" +
			"Space : Pause/Unpause Game\n" +
			"H : Open/Close Help Screen\n" +
			"T : Toggle Debug Mode (FPS/Draw Time)\n\n" +
			"Welcome to the game! Good luck exploring the world.";
	}
	
	public void showMessage(String text) {
		
		message = text;
		messageOn = true;
	}
	
	public void draw(Graphics2D g2) {
		
		this.g2 = g2;
		g2.setFont(pixType);
		g2.setColor(Color.white);

		//Timer
		g2.drawString(timeFormat.format(playTime), gp.tileSize*13, 65);

		//Game State
		switch(gp.gameState){
			case GamePanel.playState:
				playTime -= (double)1/60;
				break;
			case GamePanel.pauseState:
				drawPause();
				break;
			case GamePanel.readState:
				drawHelpScreen(); // Changed to a specific method
				break;
		}

	}
	public void drawPause(){

		g2.setFont(testType.deriveFont(Font.PLAIN, 80F));

		String text = "PAUSED";
		
		int x = centerTextX(text);
		int y = gp.screenHeight/2;

		g2.drawString(text, x, y);
	}

	public void drawHelpScreen(){
		// Coordinates for the dialogue box/help window
		int x = gp.tileSize * 2;
		int y = gp.tileSize / 2;
		int width = gp.screenWidth - (gp.tileSize * 4);
		int height = gp.tileSize * 10;
		
		drawDialogueWindow(x, y, width, height); // Draw the box

		// Draw text content
		g2.setFont(contentFont);
		g2.setColor(Color.white);
		
		int textX = x + gp.tileSize;
		int textY = y + gp.tileSize;
		int lineHeight = (int)g2.getFontMetrics().getStringBounds("A", g2).getHeight();

		// Iterate through lines of the help content and draw them
		for(String line : helpContent.split("\n")) {
			g2.drawString(line, textX, textY);
			textY += lineHeight; // Move down for the next line
		}
	}

	public void drawDialogueWindow(int x, int y, int width, int height){

		// Background (Black with transparency)
		Color hex = new Color(0,0,0, 220);
		g2.setColor(hex);
		int arc = 35;
		g2.fillRoundRect(x, y, width, height, arc, arc);

		// Border (White)
		hex = new Color(255,255,255);
		int weight = 5;
		int edge = 2 * weight;
		g2.setColor(hex);
		g2.setStroke(new BasicStroke(weight));
		g2.drawRoundRect(x + weight, y + weight, width - edge, height - edge, arc - edge, arc - edge);
	}

	public int centerTextX(String text){
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth/2 - length/2;

		return x;
	}
}
