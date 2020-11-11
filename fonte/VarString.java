public class VarString extends Variable{
    public String valor;

    public VarString(String name, String valor){
        this.type = "string";
        this.name = name;
        this.valor = valor;
    }
}
