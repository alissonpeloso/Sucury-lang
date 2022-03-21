package fonte.sucury;

// Realiza a leitura das linhas do arquivo .sy

// Alisson Peloso     <alisson.luan2000@gmail.com>
// Eduardo Lazaretti  <eduardolazaretti3@gmail.com>
// Guilherme Graeff   <guilherme.rafael.graeff@gmail.com>
// Stefani Meneghetti <meneghettistefani@gmail.com> 

import java.io.File;
import java.util.Scanner;

public class Scan {
    protected String lines[];

    public void printFile(){
        for (int i = 0; i < this.lines.length ; i++) {
            System.out.println("Linha " + i + ": " + this.lines[i]);
        }
    }

    public void readFile(String fileName){
        try {
            this.lines = new String[0];
            File file = new File(fileName);
            Scanner input = new Scanner(file);
            while (input.hasNextLine()) {
                String line = input.nextLine();
                this.lines = Util.appendArray(this.lines.length, this.lines, line);
            }
            input.close();
        } catch (Exception e) {
            System.out.println("Não foi possivel abrir o arquivo "+fileName+".");
        }
    }
}
