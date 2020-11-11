import java.util.*;

public abstract class Square {
    public byte dimension;
    public Variable[][] square;
    public Configuration configuration;
    public PriorityQueue<Variable> priorityQueue;

    public int noOfFailure;
    public int noOfConsistencyChecking;

    public Value[] values;

    public Square(byte[][] s, Configuration conf) {
        configuration = conf;
        dimension = (byte) s.length;

        noOfConsistencyChecking = 0;
        noOfFailure = 0;
        priorityQueue = new PriorityQueue<>(new VariableComparator(configuration));
        square = new Variable[dimension][dimension];
        values = new Value[dimension + 1];

        for (byte i = 0; i < dimension; i++) {
            values[i + 1] = new Value((byte) (i+1));
            for (byte j = 0; j < dimension; j++) {
                square[i][j] = new Variable(dimension, i, j);
            }
        }

        for (byte i = 0; i < dimension; i++) {
            for (byte j = 0; j < dimension; j++) {
                if(s[i][j] != Constants.NOT_SET)
                    updateHueristics(i, j, s[i][j]);
            }
        }

        for (byte i = 0; i < dimension; i++) {
            for (byte j = 0; j < dimension; j++) {
                if(s[i][j] == Constants.NOT_SET)
                    priorityQueue.add(square[i][j]);
            }
        }
    }

    public Vector<Change> updateHueristics(byte x, byte y, byte value){
        square[x][y].value = values[value];

        Vector<Change> changes = new Vector<>(dimension*2-2);
        Change change;

        int i,j;
        for (int k = 0; k < dimension*2; k++){
            if(k<dimension) {
                i = x;
                j = k;
            }
            else {
                i = k%dimension;
                j = y;
            }

            if(square[i][j].isNotAssigned()){
                change = new Change(square[i][j]);

                /// updating domain
                if(square[i][j].domain.remove(square[x][y].value))
                    change.value = square[x][y].value;

                /// Updating dynamic degree
                if(configuration.needToCalculateDynamicDegree())
                    square[i][j].dynamicDegree--;

                /// Updating IBS value
                else if(configuration.needToCalculateIBS()) { }

                /// update priority queue
                if(priorityQueue.remove(square[i][j]))
                    priorityQueue.add(square[i][j]);

                changes.add(change);
            }
        }

        return changes;
    }

    public void undoChanges(Vector<Change> changes){
        for (Change change:changes) {
            square[change.x][change.y].dynamicDegree = change.dynamicDegree;
            square[change.x][change.y].ibs = change.ibs;
            if (change.value != null)
                square[change.x][change.y].domain.add(change.value);

            /// update priority queue
            if(priorityQueue.remove(square[change.x][change.y]))
                priorityQueue.add(square[change.x][change.y]);
        }
    }

    public void printResult(){
        System.out.println("No of Consistency Checking: " + noOfConsistencyChecking);
        System.out.println("No of Failure: " + noOfFailure);
    }

    public boolean isVariableAssigned(int i, int j){
        return square[i][j].isAssigned();
    }

    public boolean isVariableNotAssigned(int i, int j){
        return square[i][j].isNotAssigned();
    }

    public abstract boolean backtrack();

    public boolean isLatinSquare(){
        if(priorityQueue.size()>0)
            return false;
        for (byte i = 0; i < dimension; i++) {
            for (byte j = 0; j < dimension; j++) {
                if(isVariableNotAssigned(i, j))
                    return false;
            }
        }

        return true;
    }

    public void print(){
        System.out.println("Latin Square: " + isLatinSquare());

        for (byte i = 0; i < dimension; i++) {
            for (byte j = 0; j < dimension; j++) {
                System.out.print(square[i][j].getByteValue() + "\t");
            }
            System.out.println();
        }
    }
}
