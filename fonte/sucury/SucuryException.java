package fonte.sucury;

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
        System.out.println("\nLinha: "+line);
        System.out.print("Erro: "+this.message);
        if(this.cause != null)
            System.out.print(": "+this.cause);
        System.out.println();
        if(this.description != null)
            System.out.println("Descrição: "+this.description+"");
    }
}
