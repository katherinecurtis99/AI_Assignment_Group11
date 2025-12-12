/*package agent;

import java.util.LinkedList;
import java.util.Queue;
import main.GamePanel;
*/


package agent;

import java.awt.Point;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Queue;

import main.GamePanel;
import main.DirectionHandler;

/**
 * Simple BFS-based Decision that finds nearest long grass (mapTileNum == 0)
 * and sets DirectionHandler booleans so the mower moves using its own update().
 */
public class Decision {

    private final GamePanel gp;
    private final lawnMower mower;
    private final DirectionHandler dh;

    // neighbor offsets: up, down, left, right (tile deltas)
    private final int[] dx = {0, 0, -1, 1};
    private final int[] dy = {-1, 1, 0, 0};

    public Decision(GamePanel gp, lawnMower mower) {
        this.gp = gp;
        this.mower = mower;
        this.dh = gp.directionHandler;
    }

    public void update() {
        // clear AI "key presses" first (AI controls movement each tick)
        dh.upPressed = dh.downPressed = dh.leftPressed = dh.rightPressed = false;

        // compute mower center tile
        int startCol = (mower.x + gp.tileSize / 2) / gp.tileSize;
        int startRow = (mower.y + gp.tileSize / 2) / gp.tileSize;

        // find nearest target tile using BFS (target = long grass -> 0)
        Point target = findNearestTarget(startCol, startRow);

        if (target == null) {
            // nothing to do (all grass cut), remain idle
            return;
        }

        // decide a single-direction step that moves toward target tile.
        // We use tile coordinates so movement is coarse / grid-aligned.
        int curCol = startCol;
        int curRow = startRow;
        int dxTile = target.x - curCol;
        int dyTile = target.y - curRow;

        // Prioritise vertical movement first to avoid constant diagonal speed
        if (dyTile < 0) dh.upPressed = true;
        else if (dyTile > 0) dh.downPressed = true;
        else if (dxTile < 0) dh.leftPressed = true;
        else if (dxTile > 0) dh.rightPressed = true;

        // mower.update() (called from GamePanel.update()) will apply movement per-frame
    }

    private Point findNearestTarget(int startCol, int startRow) {
        int cols = gp.maxScreenCol;
        int rows = gp.maxScreenRow;

        boolean[][] visited = new boolean[cols][rows];
        Queue<Point> q = new ArrayDeque<>();
        q.add(new Point(startCol, startRow));
        visited[startCol][startRow] = true;

        while (!q.isEmpty()) {
            Point p = q.poll();
            int c = p.x;
            int r = p.y;

            // target condition: long grass tile (index 0)
            int tileVal = gp.getTileManager().mapTileNum[c][r];
            if (tileVal == 0) return p;

            for (int i = 0; i < 4; i++) {
                int nc = c + dx[i];
                int nr = r + dy[i];

                if (nc < 0 || nr < 0 || nc >= cols || nr >= rows) continue;
                if (visited[nc][nr]) continue;

                // passability: treat fences/walls as impassable (change index as needed)
                int val = gp.getTileManager().mapTileNum[nc][nr];
                boolean passable = (val != /*wall*/ 9); // replace 9 with your wall index
                if (!passable) continue;

                visited[nc][nr] = true;
                q.add(new Point(nc, nr));
            }
        }

        return null; // no long grass found
    }
}

/*


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
*/