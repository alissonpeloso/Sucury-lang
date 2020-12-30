package fonte.sucury;

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

    @Override
    public Double getValue() {
        return value;
    }

    @Override
    void setValue(Object value) {
        this.value = (double) value;
    }
}
