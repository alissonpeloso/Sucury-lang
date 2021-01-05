package fonte.sucury;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Parser {
    public static int currentLine;
    protected Map<String, Variable> variables;

    public static void main(String[] args) {
        String[] oi = new String[2];
        oi[0] = "String a";
        oi[1] = "a ='3'";
        Parser alo = new Parser();
        alo.parseLines(oi);
        // System.out.println(alo.variables.get("a").getValue());
    }

    Parser(){
        this.variables = new HashMap<>();
        currentLine = 1;
    }

    public void parseLines(String[] lines){
        for (int i = 0; i < lines.length ; i++) {
        
            //Verifica se tem print
            if(lines[i].indexOf("print") != -1){
                printTreatment(lines[i]);
            }

            //------Verifica se e int-----//
            else if(lines[i].indexOf("int") != -1){
                intTreatment(lines[i]);
            } 

            //------Verifica se e float-----//
            else if(lines[i].indexOf("float") != -1){
                floatTreatment(lines[i]);
            } 

            //------Verifica se e double-----//
            else if(lines[i].indexOf("double") != -1){
                doubleTreatment(lines[i]);
            }
            //------Verifica se e String-----//
            else if(lines[i].indexOf("String") !=-1){
                stringTreatment(lines[i]);
            }
            //------Verifica se eh scan-----//
            else if(lines[i].indexOf("scan") != -1){
                scanTreatment(lines[i]);
            }

            else{
                String concatLine = "";
                int equalPosition = lines[i].indexOf("=");
                String [] preEquals = Util.lineInWordArray(lines[i].substring(0, equalPosition));
                Variable search = this.variables.get(preEquals[0]);
                if(search == null){
                    System.out.println("Variavel não encontrada!");
                    
                    return;
                }
                else{
                    if(equalPosition != -1){ //Verifica se tem sinal de =
                        String[] posEquals = Util.lineInWordArray(lines[i].substring(equalPosition+1, lines[i].length()));
                        for(int j = 0 ; j < posEquals.length ; j++){  //coloca td expressao em uma unica string
                            concatLine = concatLine.concat(posEquals[j]);
                        }
                        if(search.type.equals("int")){
                            int value = (int) Operation.chooseOperation(concatLine, "int");
                            search.setValue(value);
                        }
                        else if(search.type.equals("float")){
                            float value = (float) Operation.chooseOperation(concatLine, "float");
                            search.setValue(value);
                        }
                        else if(search.type.equals("double")){
                            double value = (double) Operation.chooseOperation(concatLine, "double");
                            search.setValue(value);
                        }
                        else if(search.type.equals("string")){
                            String value = Operation.concatAfterDeclaration(lines[i]);
                            search.setValue(value);
                            System.out.println(value);
                        }
                    }
                }
            }
        }
    }

    private void printTreatment(String line){
        if(line.indexOf("(") != -1 && line.indexOf(")") != -1 ){
            int first = line.indexOf("'");
            int second = line.indexOf("'", first+1);
            String inQuotes = line.substring(first+1, second);
            
            if(inQuotes.indexOf("{}") != -1){
                if(line.indexOf(",", second) == -1){
                    System.out.println("Não foram encontradas variaveis para imprimir");
                    System.exit(0);
                }
                String [] inComma = (line.substring(line.indexOf(",", second), line.length()-1)).split(",");
                for(int j = 1; j < inComma.length; j++){
                    inComma[j] = inComma[j].trim();
                }

                for(int k = 1; k < inComma.length; k++){
                    if(inQuotes.indexOf("{}") != -1){
                        //Function da Stefani. Se chamar essa function, n precisa do if abaixo :D

                        if(variables.containsKey(inComma[k])){
                            inComma[k] = (variables.get(inComma[k]).getValue()).toString();
                        }
                        if(inComma[k].indexOf(".") != -1){
                            inQuotes = inQuotes.replaceFirst("\\{}", Operation.chooseOperation(inComma[k], "double").toString());
                        } 
                        else {
                            inQuotes = inQuotes.replaceFirst("\\{}", Operation.chooseOperation(inComma[k], "int").toString());
                        }
                    }
                    else{
                        System.out.println("Não foi possível imprimir");
                        System.exit(0);
                    }
                }
            }
            inQuotes = inQuotes.replace("\\n", "\n");
            System.out.printf(inQuotes);
        } 
    }

    //TODO: juntar tudo as função pia
    private void intTreatment(String line){
        VarInt integer;
        String concatLine = "";
        int equalPosition = line.indexOf("=");
        String [] preEquals =Util.lineInWordArray(line);
        
        if(equalPosition != -1){ //Verifica se tem sinal de =
            preEquals = Util.lineInWordArray(line.substring(0, equalPosition));
            String[] posEquals = Util.lineInWordArray(line.substring(equalPosition+1, line.length()));
            for(int j = 0 ; j < posEquals.length ; j++){  //coloca td expressao em uma unica string
                concatLine = concatLine.concat(posEquals[j]);
            }
            int value = (int) Operation.chooseOperation(concatLine, "int");
            integer = new VarInt(preEquals[1], value);
        }
        else{
            integer = new VarInt(preEquals[1]);
        }
        variables.put(integer.name, integer);
    }

    private void floatTreatment(String line){
        VarFloat pfloat;
        String concatLine = "";
        int equalPosition = line.indexOf("=");
        String [] preEquals =Util.lineInWordArray(line);

        if(equalPosition != -1){ //Verifica se tem sinal de =
            preEquals = Util.lineInWordArray(line.substring(0, equalPosition));
            String[] posEquals = Util.lineInWordArray(line.substring(equalPosition+1, line.length()));
            for(int j = 0 ; j < posEquals.length ; j++){  //coloca td expressao em uma unica string
                concatLine = concatLine.concat(posEquals[j]);
            }
            float value = (float) Operation.chooseOperation(concatLine, "float");
            pfloat = new VarFloat(preEquals[1], value);
        }
        else{
            pfloat = new VarFloat(preEquals[1]);
        }
        variables.put(pfloat.name, pfloat);
    }

    private void doubleTreatment(String line){
        VarDouble pfloat;
        String concatLine = "";
        int equalPosition = line.indexOf("=");
        String [] preEquals =Util.lineInWordArray(line);

        if(equalPosition != -1){ //Verifica se tem sinal de =
            preEquals = Util.lineInWordArray(line.substring(0, equalPosition));
            String[] posEquals = Util.lineInWordArray(line.substring(equalPosition+1, line.length()));
            for(int j = 0 ; j < posEquals.length ; j++){  //coloca td expressao em uma unica string
                concatLine = concatLine.concat(posEquals[j]);
            }
            double value = (double) Operation.chooseOperation(concatLine, "double");
            pfloat = new VarDouble(preEquals[1], value);
        }
        else{
            pfloat = new VarDouble(preEquals[1]);
        }
        variables.put(pfloat.name, pfloat);
    }
    
    private void stringTreatment(String line){
        VarString integer;
        if(line.indexOf("=") != -1){
            
            String STRcomplete;
            String[] splitStr = line.split("=");
            String SplitSpaces = splitStr[0].replaceAll(""," ");
            String[] varName = SplitSpaces.split(" ");
            
            int first = line.indexOf("'");
            int second = line.lastIndexOf("'");
            
            String inQuotes = line.substring(first, second+1);
            
            if(inQuotes.indexOf("+") != -1){
                STRcomplete= Operation.concatString(inQuotes);
                integer = new VarString(varName[1], STRcomplete);
                System.out.println(STRcomplete);
            }
            else{
                String splitQuotes[] = inQuotes.split("'");
                integer = new VarString(varName[1], splitQuotes[1]);
                System.out.println(splitQuotes[1]);
            }
        
        }
        else{
            String SplitName = line.replaceAll(" ", "");
            String[] varName = SplitName.split("String");
            integer = new VarString(varName[1]);
        }     
        variables.put(integer.name, integer);
    }
    //TODO: tratamento para Strings
    private void scanTreatment(String line){
        String variable = line.substring(line.indexOf("(")+1, line.indexOf(")"));
        if(variables.containsKey(variable)){
            Scanner scan = new Scanner(System.in);
            String lineScan = scan.nextLine();
            scan.close();

            if (variables.get(variable).type.equals("int")) {
                variables.get(variable).setValue(Integer.parseInt(lineScan));
            }
            else if (variables.get(variable).type.equals("float")) {
                variables.get(variable).setValue(Float.parseFloat(lineScan));
            }
            else if (variables.get(variable).type.equals("double")) {
                variables.get(variable).setValue(Double.parseDouble(lineScan));
            }
            else {
                variables.get(variable).setValue(lineScan);
            }
        }
        else{
            System.out.println("Variável não encontrada");
            System.exit(0);
        }
    }
}

