package fonte.sucury;

public class VarFloat extends Variable {
    public float value;

    VarFloat(String name, float value){
        this.type = "float";
        this.name = name;
        this.value = value;
    }
    VarFloat(String name){
        this.type = "float";
        this.name = name;
        this.value = 0;
    }

    @Override
    public Float getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) {
        this.value = (float) value;
    }
}
