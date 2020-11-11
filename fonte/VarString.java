public class VarString extends Variable{
    public String value;

    public VarString(String name, String value){
        this.type = "string";
        this.name = name;
        this.value = value;
    }
    public VarString(String name){
        this.type = "string";
        this.name = name;
        this.value = null;
    }

    public String getValue(){
        return this.value;
    }


}
