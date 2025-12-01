package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInput implements KeyListener{
	
	GamePanel gp;
	public boolean upPressed, downPressed, leftPressed, rightPressed;
	public boolean interactTyped;
	
	//Debug
	public boolean checkRender = false;
	

	public KeyboardInput(GamePanel gp){
		this.gp = gp;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		int code = e.getKeyCode();
		int maxOptions;

		if(gp.gameState == GamePanel.titleState){
			switch(gp.ui.titleWindow){
			case 0:
				maxOptions = 3;
				switch(code){
				case KeyEvent.VK_UP:
				case KeyEvent.VK_W: gp.ui.commandNum--; break;
				case KeyEvent.VK_DOWN:
				case KeyEvent.VK_S: gp.ui.commandNum++; break;
				case KeyEvent.VK_ENTER: 
					switch(gp.ui.commandNum){
					case 0:
						gp.stopMusic();
						gp.gameState = GamePanel.playState;
						gp.playBGM(0);
						break;
					case 1:
						gp.ui.titleWindow = 1;
						gp.ui.commandNum = 0;
						break;
					case 2:
						System.exit(0);
					}
					break;
				}
				gp.ui.commandNum = (gp.ui.commandNum < 0) ? gp.ui.commandNum + maxOptions : gp.ui.commandNum % maxOptions;
				break;
			case 1:
				maxOptions = 4;
				switch(code){
				case KeyEvent.VK_UP:
				case KeyEvent.VK_W: gp.ui.commandNum--; break;
				case KeyEvent.VK_DOWN:
				case KeyEvent.VK_S: gp.ui.commandNum++; break;
				case KeyEvent.VK_ENTER: 
					switch(gp.ui.commandNum){
					case 0:
						System.out.println("You selected Jovie!");
						break;
					case 1:
						System.out.println("You selected Centurion!");
						break;
					case 2:
						System.out.println("You selected Scholar!");
						break;
					case 3:
						gp.ui.titleWindow = 0;
						gp.ui.commandNum = 1;
						break;
					}
					break;
				}
				gp.ui.commandNum = (gp.ui.commandNum < 0) ? gp.ui.commandNum + maxOptions : gp.ui.commandNum % maxOptions;
				break;
			
			}
		} else if (gp.gameState == GamePanel.endState){
			maxOptions = 2;
			switch(code){
			case KeyEvent.VK_UP:
			case KeyEvent.VK_W: gp.ui.commandNum--; break;
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_S: gp.ui.commandNum++; break;
			case KeyEvent.VK_ENTER: 
				gp.ui.playTime = 300;
				gp.player.score = 0;
				switch(gp.ui.commandNum){
				case 0:
					gp.gameState = GamePanel.playState;
					gp.playBGM(0);
					break;
				case 1:
					gp.gameState = GamePanel.titleState;
					gp.ui.commandNum = 0;
					break;
				}
				break;
			}
			gp.ui.commandNum = (gp.ui.commandNum < 0) ? gp.ui.commandNum + maxOptions : gp.ui.commandNum % maxOptions;
		}
		
		if(code == KeyEvent.VK_SPACE){
			if(gp.gameState == GamePanel.playState){
				gp.gameState = GamePanel.pauseState;
			} else if(gp.gameState == GamePanel.pauseState){
				gp.gameState = GamePanel.playState;
			}
		}

		if(code == KeyEvent.VK_H){
			if(gp.gameState == GamePanel.playState){
				gp.gameState = GamePanel.readState;

			} else if(gp.gameState == GamePanel.readState){
				gp.gameState = GamePanel.playState;
			}
		}

		if(gp.gameState == GamePanel.playState){
			switch(code){
			case KeyEvent.VK_W: upPressed = true; break;
			case KeyEvent.VK_A: leftPressed = true;; break;
			case KeyEvent.VK_S: downPressed = true; break;
			case KeyEvent.VK_D: rightPressed = true; break;
			case KeyEvent.VK_E: interactTyped = true; break;
			case KeyEvent.VK_T: checkRender = !checkRender; break;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_W) {
			upPressed = false;
		}
		if(code == KeyEvent.VK_A) {
			leftPressed = false;
		}
		if(code == KeyEvent.VK_S) {
			downPressed = false;
		}
		if(code == KeyEvent.VK_D) {
			rightPressed = false;
		}
	}

}
