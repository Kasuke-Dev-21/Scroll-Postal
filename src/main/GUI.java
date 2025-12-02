package main;

import java.awt.Color;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;

import java.awt.image.BufferedImage; 
import javax.imageio.ImageIO; 

import java.io.IOException;
import java.io.InputStream;
import java.awt.BasicStroke;
import java.text.DecimalFormat;


public class GUI {

	GamePanel gp;
	Graphics2D g2;

	//Fonts
	Font pixType, testType;
	Font contentFont; 

	//Text Content
	public String helpContent = "";
	public boolean messageOn = false;
	public String message = "";
	int messageLifetime = 0;

	//Menu Select
	public BufferedImage titleScreenImage;
	public BufferedImage endScreenImage;
	public int commandNum = 0;
	public int titleWindow = 0;
	
	//Icons
	BufferedImage scrollIcon, mailIcon;
	int iconSize = 40;
	int iconX;

	//Timer values
	public static final int setPlayTime = 600;
	double playTime = 600;
	DecimalFormat timeFormat = new DecimalFormat("#0");
	
	public GUI(GamePanel gp) {
		this.gp = gp;
		
		try {
			InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
			pixType = Font.createFont(Font.TRUETYPE_FONT, is);
			is = getClass().getResourceAsStream("/font/Purisa Bold.ttf");
			testType = Font.createFont(Font.TRUETYPE_FONT, is);

			titleScreenImage = ImageIO.read(getClass().getResourceAsStream("/screens/title-screen.png"));
			endScreenImage = ImageIO.read(getClass().getResourceAsStream("/screens/shift-over.png"));

			scrollIcon = ImageIO.read(getClass().getResourceAsStream("/objects/scroll.png"));
            mailIcon = ImageIO.read(getClass().getResourceAsStream("/objects/mail.png"));
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

		//Game State
		switch(gp.gameState){
			case GamePanel.titleState:
				drawTitle();
				break;
			case GamePanel.playState:
				//Timer
				drawPlayScreen(); // New method for cleaner code
                playTime -= (double)1/60;
                if (playTime <= 0) {
                    playTime = 0;
					gp.stopMusic();;
					gp.playSFX(5);
                    gp.gameState = GamePanel.endState;
                }
				break;
			case GamePanel.pauseState:
				drawPause();
				break;
			case GamePanel.readState:
				drawHelpScreen(); // Changed to a specific method
				break;
			case GamePanel.endState:
				drawEndShift();
				break;
		}
		// Always draw message on top
        if(messageOn == true) {
            g2.setFont(g2.getFont().deriveFont(30F));
            g2.drawString(message, gp.tileSize, gp.screenHeight - gp.tileSize);
            
            messageLifetime++;
            if(messageLifetime > 120) { // Display for 2 seconds (120 frames)
                messageLifetime = 0;
                messageOn = false;
            }
        }

	}

	public void drawTitle(){

		if (titleScreenImage != null) {
			g2.drawImage(titleScreenImage, 0, 0, gp.screenWidth, gp.screenHeight, null);
		} else {
			// Fallback: If image fails to load, use a solid color background
			g2.setColor(new Color(70, 120, 80));
			g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		}
		/* TITLE STATES */
		switch(titleWindow){
		case 0:
			g2.setColor(new Color(0, 0, 0, 50));
			g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
			/* MENU */
			//Start
			drawMenuOption("START", 9, 0);
			//Character Customization
			drawMenuOption("CHANGE CHARACTER", 10, 1);
			//Exit
			drawMenuOption("EXIT", 11, 2);
			break;
		case 1:
			g2.setColor(new Color(0, 0, 0, 90));
			g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
			// Character Select
			drawTitleText("CHARACTER SELECT", 3, 96);
			drawMenuOption("Jovi", 7, 0);
			drawMenuOption("Centurion", 8, 1);
			drawMenuOption("Scholar", 9, 2);
			drawMenuOption("Exit", 11, 3);

		}
		
	}

	public void drawPlayScreen() {
        int line1Y = 65;
        
        // Current Score (Left)
        g2.drawString("SCORE: " + String.valueOf(gp.player.score), gp.tileSize * 1, line1Y);
        
        // Time Remaining (Right)
        String timeText = "TIME: " + timeFormat.format(playTime);
        int timeX = rightAlignX(timeText, gp.screenWidth - gp.tileSize);
        g2.drawString(timeText, timeX, line1Y);
        
		//Inventory
		int line2Y = line1Y + 35; // Move down 30 pixels from line 1

        // Scroll Icon and Count
        iconX = gp.tileSize * 1;
        
        if (scrollIcon != null) {
            g2.drawImage(scrollIcon, iconX, line2Y - 30, iconSize, iconSize, null); // Draw image
            g2.drawString("x " + gp.player.scrollCount, iconX + 42, line2Y); // Draw count next to it
        } else {
            // Fallback if image fails
            g2.drawString("Scrolls: " + gp.player.scrollCount, iconX, line2Y);
        }

        // Mail Icon and Count
        iconX = iconX + 150;
        
        if (mailIcon != null) {
            g2.drawImage(mailIcon, iconX, line2Y - 30, iconSize, iconSize, null);
            g2.drawString("x " + gp.player.mailCount, iconX + 42, line2Y);
        } else {
            // Fallback if image fails
            g2.drawString("Mail: " + gp.player.mailCount, iconX, line2Y);
        }

        // Active Modifiers (Below Score)
        if (gp.player.scoreMult != 1.0) {
            String multText = "MULTIPLIER: x" + String.format("%.1f", gp.player.scoreMult);
            g2.setColor(Color.YELLOW);
            g2.drawString(multText, gp.tileSize * 1, 95);
            g2.setColor(Color.WHITE); // Reset color
        }
        if (gp.player.boostTimer > 0) {
            String speedText = "SPEED BOOST: " + timeFormat.format((double)gp.player.boostTimer/60);
            g2.setColor(Color.CYAN);
            g2.drawString(speedText, gp.tileSize * 1, 125);
            g2.setColor(Color.WHITE); // Reset color
        }
    }
    
    public int rightAlignX(String text, int rightX) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = rightX - length;
        return x;
    }

	public void drawPause(){

		g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

		g2.setFont(testType.deriveFont(Font.PLAIN, 80F));
		String text = "PAUSED";
		int x = centerTextX(text);
		int y = gp.tileSize * 3;

		g2.setColor(Color.WHITE);
		g2.drawString(text, x, y);

		g2.setFont(pixType);
		drawMenuOption("RESUME", 9, 0);
		drawMenuOption("RETURN TO TITLE", 10, 1);
	}

	public void drawHelpScreen(){
		int x = gp.tileSize * 2;
		int y = gp.tileSize / 2;
		int width = gp.screenWidth - (gp.tileSize * 4);
		int height = gp.tileSize * 10;
		
		drawDialogueWindow(x, y, width, height); 

		// Draw text content
		g2.setFont(contentFont);
		g2.setColor(Color.white);
		
		int textX = x + gp.tileSize;
		int textY = y + gp.tileSize;
		int lineHeight = (int)g2.getFontMetrics().getStringBounds("A", g2).getHeight();

		for(String line : helpContent.split("\n")) {
			g2.drawString(line, textX, textY);
			textY += lineHeight;
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

	public void drawTitleText(String text, int location, float size){
		//Title Name
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, size));
			int x = centerTextX(text);
			int y = gp.tileSize * location;

			//Shadow
			g2.setColor(Color.BLACK);
			g2.drawString(text, x + 3, y + 5);
			//Main Color
			g2.setColor(Color.WHITE);
			g2.drawString(text, x, y);
	}

	public void drawMenuOption(String text, int location, int menuNum){
		float size = 36F;
		
		//Start
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, size));
		int x = centerTextX(text);
		int y = gp.tileSize * location;
		//Shadow
		g2.setColor(Color.BLACK);
		g2.drawString(text, x + 3, y + 5);
		//Main Color
		g2.setColor(Color.WHITE);
		g2.drawString(text, x, y);
		if(commandNum == menuNum){
			//Shadow
			g2.setColor(Color.BLACK);
			g2.drawString("> ", x + 3 - gp.tileSize, y + 2);
			//Main Color
			g2.setColor(Color.WHITE);
			g2.drawString("> ", x - gp.tileSize, y);
		}
	}

	public void drawScore(){
		float size = 64F;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, size));
		g2.setColor(Color.WHITE);
		g2.drawString("SCORE: " + String.valueOf(gp.player.score), gp.tileSize * 1 - 24, gp.tileSize * 1);
	}

	public void drawEndShift(){
		if (endScreenImage != null) {
			g2.drawImage(endScreenImage, 0, 0, gp.screenWidth, gp.screenHeight, null);
		} else {
			// Fallback: If image fails to load, use a solid color background
			g2.setColor(new Color(70, 120, 80));
			g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		}

		g2.setColor(new Color(0, 0, 0, 50));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

		drawScore();
		/* MENU */
		//Start
		drawMenuOption("REPLAY", 9, 0);
		//Character Customization
		drawMenuOption("RETURN", 10, 1);
	}
}
