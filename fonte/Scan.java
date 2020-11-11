import java.io.File;
import java.util.Scanner;

public class Scan {
    String lines[];

    public void printFile(){
        for (int i = 0; i < this.lines.length ; i++) {
            System.out.println("Linha " + i + ": " + this.lines[i]);
        }
    }
    public void readFile(String fileName){
        try {
            int numeroLinhas = 0;
            while (input.hasNextLine()) {
                numeroLinhas++;
            }
            this.lines = new String[numeroLinhas];
            int numberOfLines = 0;
            File file = new File(fileName);
            Scanner input = new Scanner(file);
            while (input.hasNextLine()) {
                String line = input.nextLine();
                this.lines[numberOfLines++] = line;
            }
            input.close();
            for (int i = 0; i < numberOfLines; i++) {
                System.out.println("Linha " + i + ": " + this.lines[i]);
            }
        } catch (Exception e) {
            System.out.println("Nao foi possivel abrir o arquivo "+fileName+".");
            e.printStackTrace();
        }
    }    

   
   
}
