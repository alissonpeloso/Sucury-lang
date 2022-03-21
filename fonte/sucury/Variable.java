package fonte.sucury;

// Classe abstrata pai de todas as variáveis

// Alisson Peloso     <alisson.luan2000@gmail.com>
// Eduardo Lazaretti  <eduardolazaretti3@gmail.com>
// Guilherme Graeff   <guilherme.rafael.graeff@gmail.com>
// Stefani Meneghetti <meneghettistefani@gmail.com> 

abstract class Variable {
    public String type;
    public String name;
    public Object value;

    abstract Object getValue();

    abstract void setValue(Object value);
}