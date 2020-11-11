public class Main {
    public static void main(String[] args) {
        System.out.println("Starting work.");

        Square latinSquare = SquareFactory.getSquare(AlgorithmTypes.MAXIMAL_ARC_CONSISTENCY,
                Heuristics.BRELAZ, "input30.txt");
        //latinSquare.print();
        latinSquare.backtrack();
        latinSquare.print();
        latinSquare.printResult();
    }
}
