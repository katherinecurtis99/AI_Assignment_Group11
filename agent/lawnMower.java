package agent;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

import main.GamePanel;
import main.DirectionHandler;

public class lawnMower extends entity {
	GamePanel gp;
	DirectionHandler dh;
	private BufferedImage image;
	public int points = 0;
	int prevCol = -1;
	int prevRow = -1;
	private java.util.Set<Integer> prevTouched = new java.util.HashSet<>();
	private int mowerWidth;
	private int mowerHeight;
	
  public lawnMower(GamePanel gp, DirectionHandler dh) {
		this.gp = gp;
		this.dh = dh;
		this.mowerWidth = gp.tileSize;
		this.mowerHeight = gp.tileSize;
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
	public void setDefaultValues() {
		x     = 100;
		y     = 100;
		speed = 4;
	}

	// Point System:
	/**Cell states    -  Penalties/Rewards
    LONG_GRASS    -  +2
    CUT_GRASS     -  -1 (small to imrove efficiency)
    GNOME         -  -20
    WALL/FENCE    -  impassable/ boundry
	*/
	public void addPoints() {
		points = points + 2;
	}
	public void removePoints() {
		points = points - 1;
	}
	public void gnomePoints() {
		points = points - 20;
	}
	public void update() {
		//controls
		if(dh.upPressed == true) {
			y = y - speed;
		}
		else if(dh.downPressed == true) {
			y = y + speed;
		}
		else if(dh.leftPressed == true) {
			x = x - speed;
		}
		else if(dh.rightPressed == true) {
			x = x + speed;
		}
		// Boundries
		if (x < 0) {
			x = 0;
		}

		if (y < 0) {
			y = 0;
		}

		int maxX = (gp.maxScreenCol * gp.tileSize) - gp.tileSize;
		int maxY = (gp.maxScreenRow * gp.tileSize) - gp.tileSize;

		if (x > maxX) {
			x = maxX;
		}

		if (y > maxY) {
			y = maxY;
		}

		// Mower overlap detection
		int left = x;
		int right= x + mowerWidth-1;
		int top  = y;
		int bottom = y + mowerHeight-1;

		int leftCol   = Math.max(0,left/gp.tileSize);
		int rightCol  = Math.min(gp.maxScreenCol -1, right/gp.tileSize);
		int topRow    = Math.max(0,top/gp.tileSize);
		int bottomRow = Math.min(gp.maxScreenRow -1, bottom/gp.tileSize);

		java.util.Set<Integer> currentTouched = new java.util.HashSet<>();
		for (int c = leftCol; c<= rightCol; c++)
		{
			for (int r=topRow; r<= bottomRow; r++)
			{
				int key = (c<<16) | r;
				currentTouched.add(key);
			}
		}

		if (this.prevTouched == null) this.prevTouched = new java.util.HashSet<>();

		for (Integer key : currentTouched) {
    	if (!this.prevTouched.contains(key)) {
        	int col = key >> 16;
        	int row = key & 0xFFFF;
        	int val = gp.getTileManager().mapTileNum[col][row]; 
        	if (val == 2) {
            	gp.getTileManager().cutGrassAt(col, row); 
        	}
    		}
		}	


		int centerX = x + gp.tileSize / 2;
		int centerY = y + gp.tileSize / 2;
		int centerCol = centerX / gp.tileSize;
		int centerRow = centerY / gp.tileSize;

		if (centerCol >= 0 && centerRow >= 0 && centerCol < gp.maxScreenCol && centerRow < gp.maxScreenRow) {
    		int centerVal = gp.getTileManager().mapTileNum[centerCol][centerRow];
    
    		if ( (centerCol != prevCol || centerRow != prevRow) && centerVal != 2 ) {
        		gp.getTileManager().cutGrassAt(centerCol, centerRow);
        		prevCol = centerCol;
        		prevRow = centerRow;
    		}
	}


	this.prevTouched.clear();
	this.prevTouched.addAll(currentTouched);

		// Cut grass
		//int centerX = x + gp.tileSize/2;
		//int centerY = y + gp.tileSize/2;
		//int col     = centerX / gp.tileSize;
	//	int row     = centerY / gp.tileSize;
		
	//	if (col>=0 && row>=0 && col<gp.maxScreenCol && row<gp.maxScreenRow)
	//	{
	//		if (col != prevCol || row != prevRow) 	
	//		{
	//			gp.getTileManager().cutGrassAt(col, row);
	//			prevCol = col;
	//			prevRow = row;
	//		}
	//	}
	}
	
  public void draw(Graphics2D g2) {
		if (image != null) {
			g2.drawImage(image, x, y, gp.tileSize, gp.tileSize,null);
			return;
		}
		else {
		g2.setColor(Color.white);
		g2.fillRect(x, y, gp.tileSize, gp.tileSize);
		}
	}
}
