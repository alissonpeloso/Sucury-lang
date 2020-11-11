public class VarInt extends Variable {
    public int valor;

    public VarInt(String name, int valor){
        this.type = "int";
        this.name = name;
        this.valor = valor;
    }
    public VarInt(String name){
        this.type = "int";
        this.name = name;
        this.valor = 0;
    }





}
