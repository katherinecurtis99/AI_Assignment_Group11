package agent;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class QLearner {
    private final double alpha;   // learning rate
    private final double gamma;   // discount factor
    private double eps;           // exploration probability (will decay)
    private final Random rnd = new Random();
    private final Map<String, Double> q = new HashMap<>();

    public static final int ACTION_UP = 0;
    public static final int ACTION_DOWN = 1;
    public static final int ACTION_LEFT = 2;
    public static final int ACTION_RIGHT = 3;
    public static final int[] ALL_ACTIONS = {ACTION_UP,ACTION_DOWN,ACTION_LEFT,ACTION_RIGHT};

    public QLearner(double alpha, double gamma, double eps) {
        this.alpha = alpha;
        this.gamma = gamma;
        this.eps = eps;
    }

    private String key(int col, int row, int action) {
        return col + "," + row + "," + action;
    }

    public double getQ(int col, int row, int action) {
        return q.getOrDefault(key(col,row,action), 0.0);
    }

    public int chooseAction(int col, int row) {
        if (rnd.nextDouble() < eps) {
            return ALL_ACTIONS[rnd.nextInt(ALL_ACTIONS.length)];
        }
        // greedy
        int best = ALL_ACTIONS[0];
        double bestVal = getQ(col,row,best);
        for (int a : ALL_ACTIONS) {
            double v = getQ(col,row,a);
            if (v > bestVal) { best = a; bestVal = v; }
        }
        return best;
    }

    public void update(int col, int row, int action, int nextCol, int nextRow, double reward) {
        double qOld = getQ(col,row,action);
        double maxNext = Double.NEGATIVE_INFINITY;
        for (int a : ALL_ACTIONS) {
            maxNext = Math.max(maxNext, getQ(nextCol,nextRow,a));
        }
        if (maxNext == Double.NEGATIVE_INFINITY) maxNext = 0;
        double qNew = qOld + alpha * (reward + gamma * maxNext - qOld);
        q.put(key(col,row,action), qNew);
    }

    // optional: call to decay exploration
    public void decayEpsilon(double factor) { eps *= factor; }
    public double getEpsilon() { return eps; }
}
