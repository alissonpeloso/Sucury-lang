public class VarInt extends Variable {
    public int value;

    public void VarInt1(String name, int value){
        this.type = "int";
        this.name = name;
        this.value = value;
    }
    public void VarInt1(String name){
        this.type = "int";
        this.name = name;
        this.value = 0;
    }

    public Integer getValue(){
        return this.value;
    }



}
