public class Float extends Variable {
    public float valor;

    public Float(String name, float valor){
        this.type = "float";
        this.name = name;
        this.valor = valor;
    }
}