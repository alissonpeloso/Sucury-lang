package fonte.sucury;

public class VarString extends Variable{
    public String value;

    VarString(String name, String value){
        this.type = "string";
        this.name = name;
        this.value = value;
    }
    VarString(String name){
        this.type = "string";
        this.name = name;
        this.value = null;
    }

    @Override
    public String getValue(){
        return this.value;
    }

    @Override
    public void setValue(Object value) {
        this.value = (String) value;
    }
}
