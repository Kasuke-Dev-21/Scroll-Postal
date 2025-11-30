package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;


import entity.Player;
import entity.Entity;
import object.Interactable;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{

	/* SCREEN SETTINGS */
	
	//tile size
	public final int originalTileSize = 32; //default tile size
	public final int scale = 2;
	public final int tileSize = originalTileSize * scale; //scale with resolution
	
	//window size
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol;
	public final int screenHeight = tileSize * maxScreenRow;
	
	//FPS variable
	int fps = 60;
	
	//System Setup
	TileManager TM = new TileManager(this);
	KeyboardInput key = new KeyboardInput(this);
	public CollisionCheck cd = new CollisionCheck(this); 
	public AssetSetup AS = new AssetSetup(this);
	public GUI ui = new GUI(this);
	Sound bgm = new Sound();
	Sound sfx = new Sound();
	Thread gameThread; //game clock
	
	//Entity and object
	final int entityArrSize = 10;

	public Player player = new Player(this, key);
	public Interactable obj[] = new Interactable[entityArrSize];
	public Entity npc[] = new Entity[entityArrSize];

	/* GAME STATE SETTINGS */
	public int gameState;
	public static final int titleState = 0;
	public static final int playState = 1;
	public static final int pauseState = 2;
	public static final int readState = 3;
	
	/* WORLD SETTINGS */
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldRow;
	
	
	/* Game Panel Constructor */
	public GamePanel() {
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(key);
		this.setFocusable(true);
		
	}
	
	public void gameLoad() {
		
		AS.setObject();
		AS.setNPC();
		//playBGM(0);
		gameState = titleState;

	}
	
	public void startGameThread() {
		
		gameThread = new Thread(this);
		gameThread.start();
		
	}


	@Override
	public void run() {
		
		/* Sleep method = less accurate, not recommended
		*
		*	double drawInterval = 1000000000/fps;
		*	double nextDrawTime = System.nanoTime() + drawInterval;
		*	
		*	//Game Loop Logic
		*	while(gameThread != null) {
		*		
		*		// 1. UPDATE: update info such as character positions
		*		update();
		*		
		*		// 2. DRAW: draw the screen with the updated information
		*		repaint();
		*		
		*		try {
		*			double remainingTime = nextDrawTime - System.nanoTime();
		*			remainingTime /= 1000000;
		*			
		*			//check if no more allocated time
		*			if(remainingTime < 0) {
		*				remainingTime = 0;
		*			}
		*			
		*			Thread.sleep((long) remainingTime);
		*			
		*			nextDrawTime += drawInterval;
		*			
		*		} catch (InterruptedException e) {
		*			// TODO Auto-generated catch block
		*			e.printStackTrace();
		*		}
		*	}
		*
		*/
		
		/* Delta-Accumulator Method */
		double drawInterval = 1000000000/fps;
		double delta = 0;
		long lastTime =  System.nanoTime();
		long currentTime;
		
		long timer = 0;
		int drawCount = 0;
		
		//Game Loop Logic
		while(gameThread != null) {
			
			currentTime = System.nanoTime();
			
			timer += (currentTime - lastTime);
			delta += (currentTime - lastTime) / drawInterval;
			
			lastTime = currentTime;
			
			if(delta >= 1) {
				// 1. UPDATE: update info such as character positions
				update();
					
				// 2. DRAW: draw the screen with the updated information
				repaint();
				
				delta--;
				
				drawCount++;
			}
			
			if(timer >= 1000000000) {
				System.out.println("FPS: "+ drawCount);
				drawCount = 0;
				timer = 0;
			}
		
		}
	}
	
	public void update() {
		
		if(gameState == playState){
			//Player movement logic
			player.update();
			//NPC movement logic
			for(int ctr = 0; ctr < npc.length; ctr++){
				if(npc[ctr] != null){
					npc[ctr].update();
				}
			}
		} else if(gameState == pauseState){
			//pass;
		}

		// reset flag so it only triggers once per press
		key.interactTyped = false;
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		//2D Graphics
		Graphics2D g2 = (Graphics2D)g;
		
		//Debug
		long drawStart = 0;
		if(key.checkRender == true){
			drawStart = System.nanoTime();
		}

		//Title Screen logic
		if(gameState == titleState){
			ui.draw(g2);
		} else {
			//Tile
			TM.draw(g2);
			
			//Object
			for(int ctr = 0; ctr < obj.length; ctr++) {
				if (obj[ctr] != null) {
					obj[ctr].draw(g2, this);
				}
			}
			
			//NPC
			for(int ctr = 0; ctr < npc.length; ctr++){
				if(npc[ctr] != null){
					npc[ctr].draw(g2);
				}
			}

			//Player
			player.draw(g2);
			
			//GUI
			ui.draw(g2);
		}
		
		
		
		//Debug
		if(key.checkRender == true){
			long drawEnd = System.nanoTime();
			long passed = drawEnd - drawStart;
			g2.setColor(Color.white);
			g2.drawString("Draw Time: " + passed, 10, 400);
			System.out.println("Draw Time: " + passed);
		}
		g2.dispose();
		
	}
	
	public void playBGM(int x) {
		bgm.setFile(x);
		bgm.play();
		bgm.loop();
	}
	
	
	public void stopMusic() {
		bgm.stop();
	}
	
	public void playSFX(int x) {
		sfx.setFile(x);
		sfx.play();
	}
}
