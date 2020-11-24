import java.util.*;

public abstract class Square {
    public byte dimension;
    public Variable[][] square;
    public Configuration configuration;
    public Vector<Variable> unassignedVariables;

    public int noOfFailure;
    public int noOfNodes;

    public boolean invalid;

    public Value[] values;

    public Square(byte[][] s, Configuration conf) {
        configuration = conf;
        dimension = (byte) s.length;

        noOfNodes = 0;
        noOfFailure = 0;

        unassignedVariables = new Vector<>(dimension * dimension);

        square = new Variable[dimension][dimension];
        values = new Value[dimension + 1];

        for (byte i = 0; i < dimension; i++) {
            values[i + 1] = new Value((byte) (i + 1));
            for (byte j = 0; j < dimension; j++) {
                square[i][j] = new Variable(dimension, i, j);
            }
        }

        for (byte i = 0; i < dimension; i++) {
            for (byte j = 0; j < dimension; j++) {
                if (s[i][j] != Constants.NOT_SET)
                    updateHeuristics(i, j, s[i][j]);
                else
                    unassignedVariables.add(square[i][j]);
            }
        }
    }

    public Vector<Change> updateHeuristics(byte x, byte y, byte value) {
        invalid = false;
        square[x][y].value = values[value];

        Vector<Change> changes = new Vector<>(dimension * 2 - 2);
        Change change;

        int i, j;
        for (int k = 0; k < dimension * 2; k++) {
            if (k < dimension) {
                i = x;
                j = k;
            } else {
                i = k % dimension;
                j = y;
            }

            if (square[i][j].isNotAssigned()) {

                change = new Change(square[i][j]);

                /// updating domain
                if (square[i][j].domain.remove(square[x][y].value))
                    change.value = square[x][y].value;

                /// Updating dynamic degree
                if (configuration.needToCalculateDynamicDegree())
                    square[i][j].dynamicDegree--;

                /// Updating IBS value
//                else if (configuration.needToCalculateIBS()) {
//                }

                changes.add(change);

                if (!checkConsistency(i, j)) {
                    invalid = true;
                    return changes;
                }
            }
        }

        return changes;
    }

    protected boolean checkConsistency(int x, int y) {
        return square[x][y].domain.size() != 0;
    }

    public void undoChanges(Vector<Change> changes) {
        if (changes == null)
            return;
        for (Change change : changes) {
            square[change.x][change.y].dynamicDegree = change.dynamicDegree;
            square[change.x][change.y].ibs = change.ibs;
            if (change.value != null)
                square[change.x][change.y].domain.add(change.value);
        }
    }

    public boolean revert(Vector<Change> changes, Variable temp) {
        undoChanges(changes);

        noOfFailure++;
        temp.value = null;
        unassignedVariables.add(temp);

        return false;
    }

    public Variable getNextVariable() {
        Variable v1 = unassignedVariables.get(0);
        int index = 0;
        Variable v2;

        for (int i = 1; i < unassignedVariables.size(); i++) {
            v2 = unassignedVariables.get(i);
            switch (configuration.heuristics) {
                case SDF:
                    if (v1.domain.size() > v2.domain.size()) {
                        v1 = v2;
                        index = i;
                    }
                    break;
                case MAX_DYNAMIC_DEGREE:
                    if (v1.dynamicDegree < v2.dynamicDegree) {
                        v1 = v2;
                        index = i;
                    }
                    break;
                case MIN_DYNAMIC_DEGREE:
                    if (v1.dynamicDegree > v2.dynamicDegree) {
                        v1 = v2;
                        index = i;
                    }
                    break;
                case BRELAZ:
//                    if (v1.dynamicDegree > v2.dynamicDegree) {
//                        v1 = v2;
//                        index = i;
//                    } else if (v1.dynamicDegree == v2.dynamicDegree) {
//                        if (v1.domain.size() > v2.domain.size()) {
//                            v1 = v2;
//                            index = i;
//                        }
//                    }
                    if (v1.domain.size() == v2.domain.size()) {
                        if (v1.dynamicDegree < v2.dynamicDegree) {
                            v1 = v2;
                            index = i;
                        }
                    } else if (v1.domain.size() > v2.domain.size()) {
                        v1 = v2;
                        index = i;
                    }
                    break;
                case BRELAZ_MIN:
//                    if (v1.dynamicDegree > v2.dynamicDegree) {
//                        v1 = v2;
//                        index = i;
//                    } else if (v1.dynamicDegree == v2.dynamicDegree) {
//                        if (v1.domain.size() > v2.domain.size()) {
//                            v1 = v2;
//                            index = i;
//                        }
//                    }
                    if (v1.domain.size() == v2.domain.size()) {
                        if (v1.dynamicDegree > v2.dynamicDegree) {
                            v1 = v2;
                            index = i;
                        }
                    } else if (v1.domain.size() > v2.domain.size()) {
                        v1 = v2;
                        index = i;
                    }
                    break;
                case DOM_D_DEG:
                    if (Math.ceil((((double) v1.domain.size()) / v1.dynamicDegree)) >
                            Math.ceil((((double) v2.domain.size()) / v2.dynamicDegree))) {
                        v1 = v2;
                        index = i;
                    }
                    break;
            }
        }

        unassignedVariables.remove(index);
        return v1;
    }

    public void printResult() {
        System.out.println("No of Nodes: " + noOfNodes);
        System.out.println("No of Failure: " + noOfFailure);
    }

    public boolean isVariableAssigned(int i, int j) {
        return square[i][j].isAssigned();
    }

    public boolean isVariableNotAssigned(int i, int j) {
        return square[i][j].isNotAssigned();
    }

    public abstract boolean backtrack();

    public boolean isLatinSquare() {
        if (unassignedVariables.size() > 0)
            return false;
        for (byte i = 0; i < dimension; i++) {
            for (byte j = 0; j < dimension; j++) {
                if (isVariableNotAssigned(i, j))
                    return false;
            }
        }

        return true;
    }

    public void print() {
        System.out.println("Latin Square: " + isLatinSquare());

        for (byte i = 0; i < dimension; i++) {
            for (byte j = 0; j < dimension; j++) {
                System.out.print(square[i][j].getByteValue() + "\t");
            }
            System.out.println();
        }
    }
}
