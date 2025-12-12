/*package agent;

import java.util.LinkedList;
import java.util.Queue;
import main.GamePanel;
*/


package agent;

import java.awt.Point;
import java.util.Random;

import main.GamePanel;

/**
 * Q-learning Decision with improved stuck handling:
 * - If mower stays on the same centre tile for STUCK_LIMIT frames,
 *   pick a random forced direction and hold it for HOLD_FRAMES frames
 *   OR until the mower's centre tile changes (whichever occurs first).
 */
public class Decision {
    private final GamePanel gp;
    private final lawnMower mower;
    private final QLearner q;

    // remember last state/action for learning
    private int lastCol = -1, lastRow = -1, lastAction = -1;

    // simple stuck detector
    private int sameTileSteps = 0;
    private final int STUCK_LIMIT = 6;   // tweak down to break faster
    private int prevLearnCol = -1;
    private int prevLearnRow = -1;
    private final Random _rnd = new Random();

    // forced-move fields (improved)
    private boolean forceActive = false;
    private int forcedDir = -1;      // 0=up,1=down,2=left,3=right
    private int forcedFrames = 0;
    private final int HOLD_FRAMES = 12; // hold forced action up to this many frames

    public Decision(GamePanel gp, lawnMower mower) {
        this.gp = gp;
        this.mower = mower;
        this.q = new QLearner(0.5, 0.95, 0.2);
    }

    // called before mower moves
    public void act() {
        // current centre tile (state)
        int col = (mower.x + gp.tileSize/2) / gp.tileSize;
        int row = (mower.y + gp.tileSize/2) / gp.tileSize;

        int action;
        if (forceActive) {
            // while forced, just use the forcedDir
            action = forcedDir;
        } else {
            // normal Q action
            action = q.chooseAction(col, row);
        }

        // set direction booleans for one frame
        gp.directionHandler.upPressed = gp.directionHandler.downPressed = false;
        gp.directionHandler.leftPressed = gp.directionHandler.rightPressed = false;

        switch (action) {
            case QLearner.ACTION_UP:    gp.directionHandler.upPressed = true; break;
            case QLearner.ACTION_DOWN:  gp.directionHandler.downPressed = true; break;
            case QLearner.ACTION_LEFT:  gp.directionHandler.leftPressed = true; break;
            case QLearner.ACTION_RIGHT: gp.directionHandler.rightPressed = true; break;
            default: break;
        }

        // store last for learning
        lastCol = col; lastRow = row; lastAction = action;
    }

    // called after mower moved and cutGrassAt ran
    public void learn() {
        if (lastCol == -1) return;

        // new centre tile
        int ncol = (mower.x + gp.tileSize/2) / gp.tileSize;
        int nrow = (mower.y + gp.tileSize/2) / gp.tileSize;

        // reward from tile manager
        int reward = gp.getTileManager().lastReward;
        q.update(lastCol, lastRow, lastAction, ncol, nrow, reward);
        gp.getTileManager().lastReward = 0;

        // --- stuck detection ---
        if (ncol == prevLearnCol && nrow == prevLearnRow) {
            sameTileSteps++;
        } else {
            sameTileSteps = 0;
        }
        prevLearnCol = ncol;
        prevLearnRow = nrow;

        // if stuck and not already forcing, start forcing
        if (!forceActive && sameTileSteps >= STUCK_LIMIT) {
            forceActive = true;
            forcedDir = _rnd.nextInt(4);
            forcedFrames = HOLD_FRAMES;
            sameTileSteps = 0;
            // debug
            System.out.println("[Decision] stuck at " + ncol + "," + nrow + " -> forcing dir " + forcedDir + " for up to " + HOLD_FRAMES + " frames");
        }

        // if forcing is active, decrement frames and check if we left the tile
        if (forceActive) {
            forcedFrames--;
            // if we've moved to a different centre tile, stop forcing immediately
            if (ncol != lastCol || nrow != lastRow) {
                forceActive = false;
                forcedFrames = 0;
                // debug
                System.out.println("[Decision] left stuck tile -> stop forcing");
            } else if (forcedFrames <= 0) {
                // ran out of hold frames — stop forcing (still better than infinite)
                forceActive = false;
                // debug
                System.out.println("[Decision] forced frames expired -> stop forcing");
            }
        }
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