package fonte.sucury;

// Classe com tratamento de erros

// Alisson Peloso     <alisson.luan2000@gmail.com>
// Eduardo Lazaretti  <eduardolazaretti3@gmail.com>
// Guilherme Graeff   <guilherme.rafael.graeff@gmail.com>
// Stefani Meneghetti <meneghettistefani@gmail.com> 

public class SucuryException extends Exception {
    private String message;
    private String cause;
    private String description;

    SucuryException(String message,  String cause, String description){
        this.cause = cause;
        this.message = message;
        this.description = description;
    } 

    SucuryException(String message,  String cause){
        this.cause = cause;
        this.message = message;
    }   

    SucuryException(String message){
        this.message = message;
    } 

    public void printException(int line){
        System.out.println("\n--------------------------------------------------");
        System.out.println("Linha: "+line);
        System.out.print("Erro: "+this.message);
        if(this.cause != null)
            System.out.print(": "+this.cause);
        System.out.println();
        if(this.description != null)
            System.out.println("Descrição: "+this.description);
        System.out.println("--------------------------------------------------");
    }
}
