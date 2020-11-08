import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting work.");
        byte[][] square = takeInput();

        Square latinSquare = new Square((byte) square.length, square);
        System.out.println((byte) square.length);
        latinSquare.print();
        latinSquare.backtrack(0);
        latinSquare.print();
    }

    public static byte[][] takeInput(){
        try {
            Scanner scanner = new Scanner(new File(Constants.INPUT_FILE_DIR));
            String line = scanner.nextLine();
            line = line.replace("N=", "");
            line = line.replace(";", "");

            int dimension = Integer.parseInt(line);

            line = "";
            while (scanner.hasNext())
                line += scanner.nextLine() + ",";

            line = line.replace("start=", "");
            line = line.replace(";", "");
            line = line.replace("[", "");
            line = line.replace("]", "");
            line = line.replace("|", " ");
            line = line.replace(",", " ");
            line = line.replace("  ", " ");
            line = line.replace("  ", " ");

            String[] tokens = line.split(" ");

            byte[][] square = new byte[dimension][dimension];

            for (int x = 0, y=0; x < tokens.length; x++) {
                if (tokens[x].length() == 0)
                    continue;
                int i = y/dimension;
                int j = y%dimension;

                square[i][j] = (byte) Integer.parseInt(tokens[x]);
                y++;
            }
            return square;
        } catch (Exception e) {
            System.out.println("Input file not found or format not matched.");
            System.exit(-1);
        }
        return null;
    }
}
