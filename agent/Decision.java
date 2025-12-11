package agent;

import java.util.LinkedList;
import java.util.Queue;
import main.GamePanel;

public class Decision {

    GamePanel gp;
    lawnMower mower;

    // Movement directions
    int[] dx = {0, 0, -1, 1};
    int[] dy = {-1, 1, 0, 0};

    // track which tiles the mower already has cut
    boolean[][] visited;

    public Decision(GamePanel gp, lawnMower mower) {
        this.gp = gp;
        this.mower = mower;
        visited = new boolean[gp.maxScreenCol][gp.maxScreenRow];
    }

    public void update() {

        // convert world position tile position
        int startCol = (mower.x + gp.tileSize / 2) / gp.tileSize;
        int startRow = (mower.y + gp.tileSize / 2) / gp.tileSize;

        //mark the current tile as  cut  
        visited[startCol][startRow] = true;

        // bfs queue
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{startCol, startRow});

        // bfs visited array
        boolean[][] bfsVisited = new boolean[gp.maxScreenCol][gp.maxScreenRow];
        bfsVisited[startCol][startRow] = true;

        int targetCol = -1;
        int targetRow = -1;

        
        // search bfs
  
        while (!queue.isEmpty()) {
            int[] current = queue.remove();
            int col = current[0];
            int row = current[1];

            // if the grass is cut finds grass
            if (!visited[col][row]) {
                targetCol = col;
                targetRow = row;
                break;
            }

            // explore neighbors
            for (int i = 0; i < 4; i++) {
                int newCol = col + dx[i];
                int newRow = row + dy[i];

                // bounds
                if (newCol < 0 || newRow < 0 ||
                        newCol >= gp.maxScreenCol ||
                        newRow >= gp.maxScreenRow)
                    continue;

                // bfs visited check
                if (!bfsVisited[newCol][newRow]) {
                    bfsVisited[newCol][newRow] = true;
                    queue.add(new int[]{newCol, newRow});
                }
            }
        }

        
        // no target found
        if (targetCol == -1) {
            System.out.println("done");
            return;
        }

       
        // greedy movement
       
        if (targetCol > startCol) {
            mower.x += mower.speed; 
        } else if (targetCol < startCol) {
            mower.x -= mower.speed; 
        }

        if (targetRow > startRow) {
            mower.y += mower.speed; 
        } else if (targetRow < startRow) {
            mower.y -= mower.speed; 
        }
        
        // cut grass and point system
        int centerX = mower.x + gp.tileSize/2;
		int centerY = mower.y + gp.tileSize/2;
		int col     = centerX / gp.tileSize;
		int row     = centerY / gp.tileSize;
		
		if (col>=0 && row>=0 && col<gp.maxScreenCol && row<gp.maxScreenRow)
		{
			if (col != mower.prevCol || row != mower.prevRow) 	
			{
				gp.getTileManager().cutGrassAt(col, row);
				mower.prevCol = col;
				mower.prevRow = row;
			}
		}
    }
}
