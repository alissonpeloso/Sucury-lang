public class VarDouble extends Variable{
    public double valor;

    public VarDouble(String name, double valor){
        this.type = "double";
        this.name = name;
        this.valor = valor;
    }
    public VarDouble(String name){
        this.type= "double";
        this.name= name;
        this.valor= 0;
    }


}
