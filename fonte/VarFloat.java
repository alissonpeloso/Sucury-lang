public class VarFloat extends Variable {
    public float valor;

    public VarFloat(String name, float valor){
        this.type = "float";
        this.name = name;
        this.valor = valor;
    }
    public VarFloat(String name){
        this.type = "float";
        this.name = name;
        this.valor = 0;
    }




}
