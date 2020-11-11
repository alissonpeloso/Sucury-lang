public class VarDouble extends Variable{
    public double value;

    public VarDouble(String name, double value){
        this.type = "double";
        this.name = name;
        this.value = value;
    }
    public VarDouble(String name){
        this.type= "double";
        this.name= name;
        this.value= 0;
    }

    public Double getValue(){
        return this.value;
    }

}
