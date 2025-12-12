package tiles;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import main.GamePanel;

public class TileManager {
	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][];

	public TileManager(GamePanel gp) {
		this.gp    = gp;
		tile       = new Tile[10];
		mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow];
		getTileImage();
	  	setGnomeAt(2,2);
		setGnomeAt(7,1);
		setGnomeAt(13,3);
	}
	
	private void setGnomeAt(int col, int row) 
	{
		if (col<0 || row<0 || col>=gp.maxScreenCol || row>=gp.maxScreenRow) return;
		mapTileNum[col][row] = 2;
	}

	public void getTileImage() {
		try {
			tile[0]       = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/res/1GardenBlock.png"));

			tile[1]       = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/res/cut_grass.png"));

			tile[2]       = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/res/gnome.png"));	
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void cutGrassAt(int col, int row) {
		if (col<0 || row<0 || col>=gp.maxScreenCol || row>=gp.maxScreenRow) {
			return;
		}
			
		if(mapTileNum[col][row] == 0) {
			mapTileNum[col][row] = 1;
			gp.lawnMower.addPoints();
			return;
		}

		if(mapTileNum[col][row] == 1) {
			gp.lawnMower.removePoints();
		}

		if(mapTileNum[col][row] == 2) {
			gp.lawnMower.gnomePoints();
		}
	}

	public void loadMap() {
		try {
			InputStream is = getClass().getResourceAsStream("/maps/gardengrid.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col =0;
			int row = 0;
			
			while(col < gp.maxScreenCol && row < gp.maxScreenRow) {
				String line = br.readLine();
				
				while(col < gp.maxScreenCol) {
					String numbers[] = line.split("");
					int num          = Integer.parseInt(numbers[col]);
					
					mapTileNum[col][row] = num;
					col ++;
				}
				
        		if(col == gp.maxScreenCol) {
					col = 0;
					row ++;
				}
			}
			br.close();
		}
		catch(Exception e) {
		}
	}
	
	public void draw(Graphics2D g2) {
		int col = 0;
		int row = 0;
		int x   = 0;
		int y   = 0;
		
		while(col < gp.maxScreenCol && row < gp.maxScreenRow) 
		{
			int tileNum = mapTileNum[col][row];
			if (tileNum >= 0 && tileNum < tile.length && tile[tileNum] != null && tile[tileNum].image != null) 
				{
            	g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
        		} 
		else 
			{

            	g2.setColor(java.awt.Color.BLACK);
            	g2.fillRect(x, y, gp.tileSize, gp.tileSize);
    	}

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
}
