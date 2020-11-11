import java.util.Vector;

public class SquareFC extends Square {

    public SquareFC(byte[][] square, Configuration configuration) {
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
            if (backtrack()) return true;
            noOfFailure++;
            undoChanges(changes);
        }

        temp.value = null;
        priorityQueue.add(temp);
        return false;
    }
}
