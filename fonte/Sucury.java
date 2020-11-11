public class Sucury {
    public static void main(String[] args) {
        if ( args.length !=1 ) {
            System.out.println("Rode: java Sucury nomeArquivo.sy");
            return;
        }

        String fileName = args[0];
        Scan Teste = new Scan();
        Teste.readFile(fileName);
        Teste.printFile();

    }
}

//java Sucury.java "blah.sy"