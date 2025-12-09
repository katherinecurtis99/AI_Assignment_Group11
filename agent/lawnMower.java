package agent;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;


import main.GamePanel;
import main.DirectionHandler;

public class lawnMower extends entity
{
	GamePanel gp;
	DirectionHandler dh;
	private BufferedImage image;
	
  public lawnMower(GamePanel gp, DirectionHandler dh) 
	{
		this.gp = gp;
		this.dh = dh;
		loadImage("/res/lawn_mower.png");
		setDefaultValues();
	}
	

  private void loadImage(String resourcePath) {
	try (InputStream is = getClass().getResourceAsStream(resourcePath)) {
		if (is ==null) {
			System.err.println("Resource not found" + resourcePath);
			image = null;
			return;
		}
		image = ImageIO.read(is);
	}
	catch (IOException e) {
		e.printStackTrace();
		image = null;
	}

  }

  public void setDefaultValues()
	{
		x     = 100;
		y     = 100;
		speed = 4;
	}
	
	public void update()
	{
		//controls
		if(dh.upPressed == true)
		{
			y = y - speed;
		}
		else if(dh.downPressed == true) 
		{
			y = y + speed;
		}
		else if(dh.leftPressed == true)
		{
			x = x - speed;
		}
		else if(dh.rightPressed == true)
		{
			x = x + speed;
		}

		// Boundries
		if (x < 0) 
		{
			x = 0;
		}

		if (y < 0) 
		{
			y = 0;
		}

		int maxX = (gp.maxScreenCol * gp.tileSize) - gp.tileSize;

		int maxY = (gp.maxScreenRow * gp.tileSize) - gp.tileSize;

		if (x > maxX) 
		{
			x = maxX;
		}

		if (y > maxY) 
		{
			y = maxY;
		}

		// Cut grass
		int centerX = x + gp.tileSize/2;
		int centerY = y + gp.tileSize/2;
		int col     = centerX / gp.tileSize;
		int row     = centerY / gp.tileSize;

		if (col>=0 && row>=0 && col<gp.maxScreenCol && row<gp.maxScreenRow)
		{
			gp.getTileManager().cutGrassAt(col, row);
		}
	}
	
  public void draw(Graphics2D g2)
	{
		if (image != null) {
			g2.drawImage(image, x, y, gp.tileSize, gp.tileSize,null);
			return;
		}
		else{
		g2.setColor(Color.white);
		g2.fillRect(x, y, gp.tileSize, gp.tileSize);
		}
	}
}
