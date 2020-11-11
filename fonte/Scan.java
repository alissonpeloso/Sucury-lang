import java.io.File;
import java.util.Scanner;

public class Scan {
    String lines[];
    int numbLines;

    public void printFile(){
        for (int i = 0; i < this.lines.length ; i++) {
            System.out.println("Linha " + i + ": " + this.lines[i]);
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
