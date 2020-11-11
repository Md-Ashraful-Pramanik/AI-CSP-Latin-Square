import java.util.Vector;

public class SquareMAC extends Square {

    public SquareMAC(byte[][] square, Configuration configuration) {
        super(square, configuration);
    }

    @Override
    public boolean backtrack() {
        if (priorityQueue.size() == 0)
            return true;
        if (priorityQueue.peek().domain.size() <= 0)
            return false;

        Variable temp = priorityQueue.poll();

        for (Value v:temp.domain) {
            noOfConsistencyChecking++;
            Vector<Change> changes = updateHueristics(temp.x, temp.y, v.value);
            updateUsingLastUpdates(changes);
            if (backtrack()) return true;
            noOfFailure++;
            undoChanges(changes);
        }

        temp.value = null;
        priorityQueue.add(temp);
        return false;
    }

    public void updateUsingLastUpdates(Vector<Change> changes){
        Change c;
        for (int i=0;;i++) {
            if(changes.size() == i) return;
            c = changes.get(i);
            if(square[c.x][c.y].domain.size() == 1)
            {
                for (Value v : square[c.x][c.y].domain) {
                    changes.addAll(updateHueristics(c.x, c.y, v.value));
                    break;
                }
            }
        }
    }
}
