import java.util.Vector;

public class SquareBT extends Square {

    public SquareBT(byte[][] square, Configuration configuration) {
        super(square, configuration);
    }

    @Override
    public boolean backtrack() {
        if (priorityQueue.size() == 0)
            return true;
        if (priorityQueue.peek().domain.size() <= 0)
            return false;

        Variable temp = priorityQueue.poll();

        for (byte i = 1; i <= dimension; i++) {
            noOfConsistencyChecking++;
            if(!temp.domain.contains(values[i]))
                noOfFailure++;
            else {
                Vector<Change> changes = updateHueristics(temp.x, temp.y, i);
                if (backtrack()) return true;
                noOfFailure++;
                undoChanges(changes);
            }
        }

        temp.value = null;
        priorityQueue.add(temp);
        return false;
    }
//    @Override
//    public boolean backtrack(){
//        return true;
////        if(x == dimension*dimension)
////            return true;
////
////        int i = x/dimension;
////        int j = x%dimension;
////
////        if(square[i][j] != Constants.NOT_SET)
////            return backtrack(x + 1);
////
////        for (byte k = 1; k <= dimension; k++) {
////            square[i][j] = k;
////            if(!rows[i][k]){
////                rows[i][k] = true;
////                if(!columns[j][k]){
////                    columns[j][k] = true;
////                    if(backtrack(x + 1)){
////                        return true;
////                    }
////                    columns[j][k] = false;
////                }
////                rows[i][k] = false;
////            }
////            square[i][j] = Constants.NOT_SET;
////        }
////        return false;
//    }
}
