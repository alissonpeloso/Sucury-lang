package fonte.sucury;

// Main do programa, realiza as chamadas iniciais

// Alisson Peloso     <alisson.luan2000@gmail.com>
// Eduardo Lazaretti  <eduardolazaretti3@gmail.com>
// Guilherme Graeff   <guilherme.rafael.graeff@gmail.com>
// Stefani Meneghetti <meneghettistefani@gmail.com> 

public class Sucury {
    public static void main(String[] args) {
        if ( args.length !=1 ) {
            System.out.println("Execute: java -jar sucury.jar caminhoDoArquivo.sy");
            return;
        }

        String fileName = args[0];
        Scan scanner = new Scan();
        Parser parser = new Parser();
        scanner.readFile(fileName);
        parser.parseLines(scanner.lines);
    }
}
