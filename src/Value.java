public class Value {
    public byte value;
    public double ibs;

    public Value(byte v) {
        value = v;
        ibs = 0;
    }

    @Override
    public int hashCode() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Byte)
            return value == (Byte) obj;
        if (!(obj instanceof Value))
            return false;
        return value == ((Value) obj).value;
    }
}
