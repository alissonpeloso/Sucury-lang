package sucury;

abstract class Variable {
    public String type;
    public String name;
    public Object value;

    abstract Object getValue();

    abstract void setValue(Object value);
}