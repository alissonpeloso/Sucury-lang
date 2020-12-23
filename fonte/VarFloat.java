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

    public Float getValue(){
        return this.value;
    }
}
