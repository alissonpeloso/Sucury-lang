package fonte.sucury;

// Classe da variável Float

// Alisson Peloso     <alisson.luan2000@gmail.com>
// Eduardo Lazaretti  <eduardolazaretti3@gmail.com>
// Guilherme Graeff   <guilherme.rafael.graeff@gmail.com>
// Stefani Meneghetti <meneghettistefani@gmail.com> 

public class VarFloat extends Variable {
    public float value;

    VarFloat(String name, float value){
        this.type = "float";
        this.name = name;
        this.value = value;
    }
    VarFloat(String name){
        this.type = "float";
        this.name = name;
        this.value = 0;
    }

    @Override
    public Float getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) {
        this.value = (float) value;
    }
}
