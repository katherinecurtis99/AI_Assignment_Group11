package agent;

import java.awt.Color;
import java.awt.Graphics2D;

import main.GamePanel;
import main.DirectionHandler;

public class lawnMower extends entity
{
	GamePanel gp;
	DirectionHandler dh;
	
  public lawnMower(GamePanel gp, DirectionHandler dh) 
	{
		this.gp = gp;
		this.dh = dh;
		setDefaultValues();
	}
	
  public void setDefaultValues()
	{
		x     = 100;
		y     = 100;
		speed = 4;
	}
	
	public void update()
	{
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
	}
	
  public void draw(Graphics2D g2)
	{
		g2.setColor(Color.white);
		g2.fillRect(x, y, gp.tileSize, gp.tileSize);
	}
}
