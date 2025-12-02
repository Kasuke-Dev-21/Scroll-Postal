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

        // Game States
        switch (gp.gameState) {
            
            case GamePanel.titleState:
                // TITLE SCREEN
                handleTitleState(code);
                break;
                
            case GamePanel.playState:
                // PLAY STATE
                handlePlayState(code);
                break;
                
            case GamePanel.pauseState:
                // PAUSE STATE
                handlePauseState(code);
                break;
                
            case GamePanel.readState:
                // READ STATE
                handleReadState(code);
                break;
                
            case GamePanel.endState:
                // END STATE
                handleEndState(code);
                break;
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

	private void handleTitleState(int code) {
        int maxOptions;
        
        // This is based on gp.ui.titleWindow
        switch(gp.ui.titleWindow){
        case 0: // Main Menu
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
                    gp.player.setDefault();
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
            
        case 1: // Character Select
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
                    gp.player.setHat(null);
                    break;
                case 1:
                    System.out.println("You selected Centurion!");
                    gp.player.setHat("centurion");
                    break;
                case 2:
                    System.out.println("You selected Scholar!");
                    gp.player.setHat("scholar");
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
    }

    private void handlePlayState(int code) {
        switch(code){
        case KeyEvent.VK_W: upPressed = true; break;
        case KeyEvent.VK_A: leftPressed = true; break;
        case KeyEvent.VK_S: downPressed = true; break;
        case KeyEvent.VK_D: rightPressed = true; break;
        case KeyEvent.VK_E: interactTyped = true; break;
        case KeyEvent.VK_T: checkRender = !checkRender; break;
        }
    }
    
    private void handlePauseState(int code) {
        int maxOptions = 2; // "Resume" and "Return to Title"
        switch(code){
        case KeyEvent.VK_UP:
        case KeyEvent.VK_W: gp.ui.commandNum--; break;
        case KeyEvent.VK_DOWN:
        case KeyEvent.VK_S: gp.ui.commandNum++; break;
        case KeyEvent.VK_ENTER: 
            switch(gp.ui.commandNum){
            case 0: // RESUME
                gp.gameState = GamePanel.playState;
                break;
            case 1: // RETURN TO TITLE
                gp.gameState = GamePanel.titleState;
                gp.ui.playTime = GUI.setPlayTime;
                gp.player.score = 0;
                gp.player.scrollCount = 0;
                gp.player.mailCount = 0;
                gp.player.scoreMult = 1.0;
                gp.ui.commandNum = 0; 
                gp.stopMusic();
                gp.playBGM(6); 
                break;
            }
            break;
        }
        gp.ui.commandNum = (gp.ui.commandNum < 0) ? gp.ui.commandNum + maxOptions : gp.ui.commandNum % maxOptions;
    }
    
    private void handleReadState(int code) {
    }

    private void handleEndState(int code) {
        int maxOptions = 2; // "Replay" and "Return"
        switch(code){
        case KeyEvent.VK_UP:
        case KeyEvent.VK_W: gp.ui.commandNum--; break;
        case KeyEvent.VK_DOWN:
        case KeyEvent.VK_S: gp.ui.commandNum++; break;
        case KeyEvent.VK_ENTER: 
            // Reset essential game state variables before transition
            gp.ui.playTime = 300;
            gp.player.score = 0;
            gp.player.scrollCount = 0;
            gp.player.mailCount = 0;
            gp.player.scoreMult = 1.0;
            
            switch(gp.ui.commandNum){
            case 0:
                gp.gameState = GamePanel.playState;
                gp.playBGM(0);
                break;
            case 1:
                gp.gameState = GamePanel.titleState;
                gp.ui.commandNum = 0;
                gp.playBGM(6);
                break;
            }
            break;
        }
        gp.ui.commandNum = (gp.ui.commandNum < 0) ? gp.ui.commandNum + maxOptions : gp.ui.commandNum % maxOptions;
    }

}
