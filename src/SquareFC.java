import java.util.Vector;

public class SquareFC extends Square {

    public SquareFC(byte[][] square, Configuration configuration) {
        super(square, configuration);
    }

    @Override
    public boolean backtrack() {
        if (priorityQueue.size() == 0)
            return true;
        if (priorityQueue.peek().domain.size() <= 0){
            System.out.println("Not worked");
            return false;
        }

        Variable temp = priorityQueue.poll();

        for (Value v:temp.domain) {
            noOfNodes++;
            Vector<Change> changes = updateHueristics(temp.x, temp.y, v.value);
            if(isBackTrack)
                return revert(changes, temp);
            if (backtrack())
                return true;
            undoChanges(changes);
        }

        return revert(null, temp);
    }
}
