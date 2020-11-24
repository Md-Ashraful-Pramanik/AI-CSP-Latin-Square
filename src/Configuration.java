public class Configuration {
    Heuristics heuristics;

    public Configuration(Heuristics heuristics) {
        this.heuristics = heuristics;
    }

    public boolean needToCalculateDynamicDegree() {
        return heuristics != Heuristics.SDF;
    }
//
//    public boolean needToCalculateIBS(){
//        return heuristics == Heuristics.IBS;
//    }
}
