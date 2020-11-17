import java.util.Vector;

public class SquareMAC extends Square {

    public SquareMAC(byte[][] square, Configuration configuration) {
        super(square, configuration);
    }

    @Override
    public boolean backtrack() {
        if (priorityQueue.size() == 0)
            return true;
        if (priorityQueue.peek().domain.size() <= 0) {
            System.out.println("Not worked");
            return false;
        }

        Variable temp = priorityQueue.poll();

        for (Value v : temp.domain) {
            noOfNodes++;
            Vector<Change> changes = updateHueristics(temp.x, temp.y, v.value);
            if (isBackTrack)
                return revert(changes, temp);

            if (updateUsingLastUpdates(changes))
                return revert(changes, temp);

            if (backtrack())
                return true;
            undoChanges(changes);
        }

        return revert(null, temp);
    }


    @Override
    protected boolean checkConsistency(int x, int y) {
        if (square[x][y].domain.size() == 0)
            return false;

        int i, j;

        for (Value v : square[x][y].domain) {
            boolean consistent = true;
            for (int k = 0; k < dimension * 2; k++) {
                if (k < dimension) {
                    i = x;
                    j = k;
                } else {
                    i = k % dimension;
                    j = y;
                }

                if (i == x && j == y)
                    continue;

                if (square[i][j].isNotAssigned() &&
                        square[i][j].domain.size() == 1 &&
                        square[i][j].domain.contains(v)) {
                    consistent = false;
                    break;
                }
            }

            if (consistent) return true;
        }

        return false;
    }

    public boolean updateUsingLastUpdates(Vector<Change> changes) {
        Change c;
        for (int i = 0; ; i++) {
            if (changes.size() == i) return false;
            c = changes.get(i);
            if (square[c.x][c.y].domain.size() == 1) {
                for (Value v : square[c.x][c.y].domain) {
                    changes.addAll(updateHueristics(c.x, c.y, v.value));
                    if (isBackTrack) return true;
                }
            }
        }
    }
}
