public class VarInt extends Variable {
    public int valor;

    public VarInt(String name, int valor){
        this.type = "int";
        this.name = name;
        this.valor = valor;
    }
}
