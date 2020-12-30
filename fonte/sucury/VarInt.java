package fonte.sucury;

public class VarInt extends Variable {
    public int value;

    VarInt(String name, int value){
        this.type = "int";
        this.name = name;
        this.value = value;
    }
    VarInt(String name){
        this.type = "int";
        this.name = name;
        this.value = 0;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    void setValue(Object value) {
        this.value = (int) value;
    }
}
