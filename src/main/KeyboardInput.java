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

		//if(){}
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
