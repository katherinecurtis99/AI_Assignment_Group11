package tiles;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import main.GamePanel;

public class TileManager 
{
	GamePanel gp;
	Tile[] tile;
	int mapTileNum[][];

	public TileManager(GamePanel gp) 
  {
		this.gp    = gp;
		tile       = new Tile[5];
		mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow];
		getTileImage();
		
	}
	
	public void getTileImage()
	{
		try 
		{
			tile[0]       = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/res/1GardenBlock.png"));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void loadMap()
	{
		try 
      {
			InputStream is = getClass().getResourceAsStream("/maps/gardengrid.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col =0;
			int row = 0;
			
			while(col < gp.maxScreenCol && row < gp.maxScreenRow)
			{
				String line = br.readLine();
				
				while(col < gp.maxScreenCol)
				{
					String numbers[] = line.split("");
					int num          = Integer.parseInt(numbers[col]);
					
					mapTileNum[col][row] = num;
					col ++;
				}
				
        if(col == gp.maxScreenCol)
				{
					col = 0;
					row ++;
				}
			}
			br.close();
		}
		catch(Exception e)
		{
		}
	}
	
	public void draw(Graphics2D g2)
	{
		int col = 0;
		int row = 0;
		int x   = 0;
		int y   = 0;
		
		while(col < gp.maxScreenCol && row < gp.maxScreenRow)
		{
			int tileNum = mapTileNum[col][row];
			g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
			col++;
			x = gp.tileSize + x;
			
			if(col == gp.maxScreenCol)
			{
				col = 0;
				x = 0;
				row++;
				y = gp.tileSize + y;
			}
		}
	}
	//public void draw(Graphics2D g2)
	//{
		//row 1
	//	g2.drawImage(tile[0].image,   0, 0, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image,  48, 0, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image,  96, 0, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image, 144, 0, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image, 192, 0, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image, 240, 0, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image, 288, 0, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image, 336, 0, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image, 384, 0, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image, 432, 0, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image, 480, 0, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image, 528, 0, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image, 576, 0, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image, 624, 0, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image, 672, 0, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image, 720, 0, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image, 768, 0, gp.tileSize, gp.tileSize, null);
	//	
		//row 2
	//	g2.drawImage(tile[0].image,   0, 48, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image,  48, 48, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image,  96, 48, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image, 144, 48, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image, 192, 48, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image, 240, 48, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image, 288, 48, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image, 336, 48, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image, 384, 48, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image, 432, 48, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image, 480, 48, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image, 528, 48, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image, 576, 48, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image, 624, 48, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image, 672, 48, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image, 720, 48, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image, 768, 48, gp.tileSize, gp.tileSize, null);
	//	
		//row 3
	//	g2.drawImage(tile[0].image,   0, 96, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image,  48, 96, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image,  96, 96, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image, 144, 96, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image, 192, 96, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image, 240, 96, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image, 288, 96, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image, 336, 96, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image, 384, 96, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image, 432, 96, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image, 480, 96, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image, 528, 96, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image, 576, 96, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image, 624, 96, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image, 672, 96, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image, 720, 96, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image, 768, 96, gp.tileSize, gp.tileSize, null);
	//	
		//row 4
	//	g2.drawImage(tile[0].image,   0, 144, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image,  48, 144, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image,  96, 144, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image, 144, 144, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image, 192, 144, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image, 240, 144, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image, 288, 144, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image, 336, 144, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image, 384, 144, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image, 432, 144, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image, 480, 144, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image, 528, 144, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image, 576, 144, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image, 624, 144, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image, 672, 144, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image, 720, 144, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[0].image, 768, 144, gp.tileSize, gp.tileSize, null);	
	//}
}
