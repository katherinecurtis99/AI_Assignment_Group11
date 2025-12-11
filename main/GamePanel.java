package main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

import agent.Decision;
import agent.lawnMower;
import tiles.TileManager;

public class GamePanel extends JPanel implements Runnable 
{
	// Screen Settings
	final int originalTileSize    = 16; //16x16 tile
	final int scale               = 3; //16x3=48
	public final int tileSize     = originalTileSize * scale; //48x48
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 4;
	final int screenWidth         = tileSize * maxScreenCol; //48x16=768pixles
	final int screenHeight        = tileSize * maxScreenRow; //48x4=192pixles
	
	//FPS
	int FPS = 60;
	
	TileManager tileM = new TileManager(this);
	DirectionHandler directionHandler = new DirectionHandler();
	Thread gameThread;
	public lawnMower lawnMower = new lawnMower(this, directionHandler);

	public GamePanel()
  {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true); //better rendering performance when true
		this.addKeyListener(directionHandler);
		this.setFocusable(true);
	}
	
	public void startGameThread()
	{
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run()
	{
		double drawInterval = 1000000000/FPS;
		double delta        = 0;
		long lastTime       = System.nanoTime();
		long currentTime;
		long timer          = 0;
		int drawCount       = 0;
		while(gameThread != null)
			{
				currentTime = System.nanoTime();
				delta       = ((currentTime - lastTime) / drawInterval) + delta;
				timer       = (currentTime - lastTime) + timer;
				lastTime    = currentTime;
				
				if(delta >= 1)
				{
				  update();
				  repaint();
				  delta--;
				  drawCount++;
				}
				
				if(timer >= 1000000000)
				{
					System.out.println("");
				}
			}
	}
	
	public TileManager getTileManager()
	{
		return tileM;
	}

  public void update()
	{
		decision.update();
	}
	
  public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		tileM.draw(g2);
		lawnMower.draw(g2);
		g2.setColor (Color.white);
		g2.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 24));
		g2.drawString("Points: " + lawnMower.points, 10, 20);
		g2.dispose();

	}
	public lawnMower lawnwMower = new lawnMower(this, directionHandler);
	public Decision decision = new Decision(this, lawnMower);
}
