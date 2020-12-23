public class VarDouble extends Variable{
    public double value;

    VarDouble(String name, double value){
        this.type = "double";
        this.name = name;
        this.value = value;
    }
    VarDouble(String name){
        this.type= "double";
        this.name= name;
        this.value= 0;
    }
    public Double getValue(){
        return this.value;
    }
}
