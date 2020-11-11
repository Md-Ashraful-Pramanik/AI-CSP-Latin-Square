import java.util.Comparator;

public class VariableComparator implements Comparator<Variable> {

    public Configuration configuration;

    public VariableComparator(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public int compare(Variable o1, Variable o2) {
        switch(configuration.heuristics){
            case SDF:
                return o1.domain.size() - o2.domain.size();
            case MAX_DYNAMIC_DEGREE:
                return o1.dynamicDegree - o2.dynamicDegree;
            case BRELAZ:
                if (o1.domain.size() == o2.domain.size())
                    return o1.dynamicDegree - o2.dynamicDegree;
                return o1.domain.size() - o2.domain.size();
            case DOM_D_DEG: Math.ceil((((double) o1.domain.size())/o1.dynamicDegree) -
                    (((double) o2.domain.size())/o2.dynamicDegree));
            default: return 0;
        }
    }
}