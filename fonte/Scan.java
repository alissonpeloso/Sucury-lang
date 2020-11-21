import java.io.File;
import java.util.Scanner;

public class Scan {
    String lines[];
    int numbLines;

    public void printFile(){
        for (int i = 0; i < this.lines.length ; i++) {
            System.out.println("Linha " + i + ": " + this.lines[i]);
            String[] a= this.lines[i].split(" ");//splita nos espacos
            
            //------Verifica se e int-----//
            SetVarInt[] inteiros= new SetVarInt[10000];
            if("int".equals(a[0])){
                inteiros[i]=new SetVarInt(); //manda pra funcao de pegar o int(SetVarInt)
                inteiros[i].setInt(a);
                System.out.println(inteiros[i].name+" = "+inteiros[i].value);
            } 
            //------Verifica se e float-----//
            SetVarFloat[] pflutuante= new SetVarFloat[10000];
            if("float".equals(a[0])){
                pflutuante[i]=new SetVarFloat(); //manda pra funcao de pegar o float(SetVarFloat)
                pflutuante[i].setFloat(a);
                System.out.println(pflutuante[i].name+" = "+pflutuante[i].value);
            } 
        }
    }

    public void readFile(String fileName){
        try {
            this.lines = new String[0];
            int amount = 0;
            File file = new File(fileName);
            Scanner input = new Scanner(file);
            while (input.hasNextLine()) {
                String line = input.nextLine();
                this.lines = Util.AppendArray(this.lines.length, this.lines, line);
                amount++;
            }
            input.close();
        } catch (Exception e) {
            System.out.println("Nao foi possivel abrir o arquivo "+fileName+".");
            e.printStackTrace();
        }
    }    

   
   
}
