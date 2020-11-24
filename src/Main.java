import java.io.File;
import java.io.FileWriter;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting work.");

        String[] fileNames = testCaseFileNames();

        try {
            FileWriter reportWriter = new FileWriter(new File("result.csv"));

            for (String fileName : fileNames) {
                for (Heuristics heuristics : Heuristics.values()) {
                    for (AlgorithmTypes types : AlgorithmTypes.values()) {
                        Square latinSquare = SquareFactory.getSquare(types, heuristics, fileName);

                        latinSquare.backtrack();
                        //latinSquare.print();

                        System.out.println("Algorithm: " + types +
                                ", Heuristics: " + heuristics + ", TestCase: " + fileName);
                        latinSquare.printResult();
                        System.out.println();
                        reportWriter.write(latinSquare.noOfNodes + ", " + latinSquare.noOfFailure + ", ");
                    }
                }
                reportWriter.write("\n");
                reportWriter.flush();
            }

            reportWriter.flush();
            reportWriter.close();
        } catch (Exception e) {
            System.out.println("Can not open report file.");
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
