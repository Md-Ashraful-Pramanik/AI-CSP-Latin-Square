import java.util.HashSet;

public class Variable {
    public Value value;
    public HashSet<Value> domain;
    public byte dynamicDegree;
    public double ibs;

    byte x;
    byte y;

    public Variable(byte domainSize, byte i, byte j){
        x = i;
        y = j;
        domain = new HashSet<>(domainSize);
        dynamicDegree = (byte) (domainSize * 2 - 2);
        ibs = 0;
        for (byte k = 1; k <= domainSize; k++)
            domain.add(new Value(k));
    }

    public byte getByteValue(){
        if(value == null)
            return Constants.NOT_SET;

        return value.value;
    }

    public boolean isAssigned(){
        return value != null;
    }

    public boolean isNotAssigned(){
        return value == null;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Variable))
            return false;
        Variable v = (Variable) obj;
//        if (getByteValue() != v.getByteValue())
//            return false;
//
//        if (domain == null && v.domain == null)
//            return true;
//        else if (domain == null)
//            return false;
//        else if(v.domain == null)
//            return false;
//
//        if(domain.size() != v.domain.size())
//            return false;
//
//        for (Value value:v.domain) {
//            if(!domain.contains(value))
//                return false;
//        }
        return x == v.x && y == v.y;
    }
}
