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

    public Integer getValue(){
        return this.value;
    }
}
