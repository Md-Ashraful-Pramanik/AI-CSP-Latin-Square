import java.util.HashSet;
import java.util.Vector;

public class Square {
    public byte dimension;
    public byte[][] square;
    public boolean[][] rows;
    public boolean[][] columns;

    public Square(byte dimension) {
        this.dimension = dimension;

        rows = new boolean[dimension][dimension + 1];
        columns = new boolean[dimension][dimension + 1];
    }

    public Square(byte dimension, byte[][] square) {
        this(dimension);
        this.square = square;

        for (byte i = 0; i < dimension; i++) {
            for (byte j = 0; j < dimension; j++) {
                this.square[i][j] = square[i][j];
                if(square[i][j] != Constants.NOT_SET)
                {
                    rows[i][square[i][j]] = true;
                    columns[j][square[i][j]] = true;
                }
            }
        }
    }

    public boolean backtrack(int x){

        if(x == dimension*dimension)
            return true;

        int i = x/dimension;
        int j = x%dimension;

        if(square[i][j] != Constants.NOT_SET)
            return backtrack(x + 1);

        for (byte k = 1; k <= dimension; k++) {
            square[i][j] = k;
            if(!rows[i][k]){
                rows[i][k] = true;
                if(!columns[j][k]){
                    columns[j][k] = true;
                    if(backtrack(x + 1)){
                        return true;
                    }
                    columns[j][k] = false;
                }
                rows[i][k] = false;
            }
            square[i][j] = Constants.NOT_SET;
        }
        return false;
    }

    public boolean isLatinSquare(){
        for (byte i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if(square[i][j] == Constants.NOT_SET)
                    return false;
            }
        }

        return true;
    }

    public void print(){
        System.out.println("Latin Square: " + isLatinSquare());

        for (byte i = 0; i < dimension; i++) {
            for (byte j = 0; j < dimension; j++) {
                System.out.print(square[i][j] + "\t");
            }
            System.out.println();
        }
    }
}
