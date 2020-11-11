public class Configuration {
    Heuristics heuristics;

    public Configuration(Heuristics heuristics) {
        this.heuristics = heuristics;
    }

    public boolean needToCalculateDynamicDegree(){
        return heuristics == Heuristics.MAX_DYNAMIC_DEGREE ||
                heuristics == Heuristics.BRELAZ ||
                heuristics == Heuristics.DOM_D_DEG;
    }

    public boolean needToCalculateIBS(){
        return heuristics == Heuristics.IBS;
    }
}
