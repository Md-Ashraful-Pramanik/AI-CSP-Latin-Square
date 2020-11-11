public class Change {
    public byte x;
    public byte y;
    public Value value;
    public byte dynamicDegree;
    public double ibs;

    public Change(byte x, byte y, byte dynamicDegree, double ibs) {
        this.x = x;
        this.y = y;
        this.dynamicDegree = dynamicDegree;
        this.ibs = ibs;
    }

    public Change(Variable v){
        this(v.x, v.y, v.dynamicDegree, v.ibs);
    }
}
