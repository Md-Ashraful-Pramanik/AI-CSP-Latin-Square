import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting work.");

        String[] fileNames = testCaseFileNames();


        for (String fileName : fileNames) {
            for (AlgorithmTypes types : AlgorithmTypes.values()) {
                Square latinSquare = SquareFactory.getSquare(types,
                        Heuristics.BRELAZ, fileName);
                //latinSquare.print();
                latinSquare.backtrack();
                //latinSquare.print();

                System.out.println("Algorithm: " + types + ". TestCase: " + fileName);
                latinSquare.printResult();
                System.out.println();
            }
        }

    }

    public static String[] testCaseFileNames() {
        File folder = new File(Constants.INPUT_FILE_DIR);
        String[] fileNames = folder.list();

        for (int i = 0; i < fileNames.length; i++) {
            fileNames[i] = Constants.INPUT_FILE_DIR + "/" + fileNames[i];
        }
        return fileNames;
    }
}
