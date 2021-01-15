package fonte.sucury;

// Classe da variável String

// Alisson Peloso     <alisson.luan2000@gmail.com>
// Eduardo Lazaretti  <eduardolazaretti3@gmail.com>
// Guilherme Graeff   <guilherme.rafael.graeff@gmail.com>
// Stefani Meneghetti <meneghettistefani@gmail.com> 

public class VarString extends Variable{
    public String value;

    VarString(String name, String value){
        this.type = "string";
        this.name = name;
        this.value = value;
    }
    VarString(String name){
        this.type = "string";
        this.name = name;
        this.value = null;
    }

    @Override
    public String getValue(){
        return this.value;
    }

    @Override
    public void setValue(Object value) {
        this.value = (String) value;
    }
}
