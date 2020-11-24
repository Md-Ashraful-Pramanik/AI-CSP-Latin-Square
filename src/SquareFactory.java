import java.io.File;
import java.util.Scanner;

public class SquareFactory {

    public static Square getSquare(AlgorithmTypes algorithmsTypes, Heuristics heuristics, String inputDir) {

        byte[][] square = takeInput(inputDir);
        Configuration configuration = new Configuration(heuristics);
        if (algorithmsTypes == null)
            return new SquareFC(square, configuration);
        switch (algorithmsTypes) {
            case BACKTRACKING:
                return new SquareBT(square, configuration);
            case FORWARD_CHECKING:
                return new SquareFC(square, configuration);
            case MAINTAINING_ARC_CONSISTENCY:
                return new SquareMAC(square, configuration);
        }

        return new SquareFC(square, configuration);
    }


    public static byte[][] takeInput(String inputDir) {
        try {
            Scanner scanner = new Scanner(new File(inputDir));
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

            for (int x = 0, y = 0; x < tokens.length; x++) {
                if (tokens[x].length() == 0)
                    continue;
                int i = y / dimension;
                int j = y % dimension;

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
