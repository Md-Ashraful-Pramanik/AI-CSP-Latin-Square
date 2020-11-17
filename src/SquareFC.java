import java.util.Vector;

public class SquareFC extends Square {

    public SquareFC(byte[][] square, Configuration configuration) {
        super(square, configuration);
    }

    @Override
    public boolean backtrack() {
        if(unassignedVariables.size() == 0)
            return true;
        Variable temp = getNextVariable();

        for (Value v:temp.domain) {
            noOfNodes++;
            Vector<Change> changes = updateHeuristics(temp.x, temp.y, v.value);
            if(invalid)
                return revert(changes, temp);
            if (backtrack())
                return true;
            undoChanges(changes);
        }

        return revert(null, temp);
    }
}
