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
<<<<<<< HEAD
            int numeroLinhas = 0;
            while (input.hasNextLine()) {
                numeroLinhas++;
            }
            this.lines = new String[numeroLinhas];
            int numberOfLines = 0;
=======
            this.lines = new String[0];
            int amount = 0;
>>>>>>> db67a34dac698c7f10a7af9c478abfbd87b9d656
            File file = new File(fileName);
            Scanner input = new Scanner(file);
            while (input.hasNextLine()) {
                String line = input.nextLine();
<<<<<<< HEAD
                this.lines[numberOfLines++] = line;
            }
            input.close();
            for (int i = 0; i < numberOfLines; i++) {
                System.out.println("Linha " + i + ": " + this.lines[i]);
            }
=======
                this.lines = Util.AppendArray(this.lines.length, this.lines, line);
                amount++;
            }
            input.close();
>>>>>>> db67a34dac698c7f10a7af9c478abfbd87b9d656
        } catch (Exception e) {
            System.out.println("Nao foi possivel abrir o arquivo "+fileName+".");
            e.printStackTrace();
        }
    }    

   
   
}
