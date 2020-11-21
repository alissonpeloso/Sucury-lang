public class VarFloat extends Variable {
    public float value;

    public void VarFloat1(String name, float value){
        this.type = "float";
        this.name = name;
        this.value = value;
    }
    public void VarFloat1(String name){
        this.type = "float";
        this.name = name;
        this.value = 0;
    }

    public Float getValue(){
        return this.value;
    }

}
