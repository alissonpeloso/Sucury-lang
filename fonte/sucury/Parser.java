package fonte.sucury;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Parser {
    public static int currentLine;
    protected Map<String, Variable> variables;
    
    public static void main(String[] args) {
        String[] oi = new String[2];

        oi[0] = "int a = 5";
        oi[1] = "a --";
        
        Parser alo = new Parser();
        alo.parseLines(oi);
        alo.printVariables();
    }

    Parser(){
        this.variables = new HashMap<>();
        currentLine = 1;
    }

    public void parseLines(String[] lines){
        for (int i = 0; i < lines.length ; i++) {

            //Verifica se tem print
            if(Pattern.compile("^\\s*print[\\s]*[(]").matcher(lines[i]).find()){
                printTreatment(lines[i]);
            }
            //------Verifica se é int-----//
            else if(Pattern.compile("^\\s*int\\s").matcher(lines[i]).find()){
                intTreatment(lines[i]);
            } 
            //------Verifica se é float-----//
            else if(Pattern.compile("^\\s*float\\s").matcher(lines[i]).find()){
                floatTreatment(lines[i]);
            } 
            //------Verifica se é double-----//
            else if(Pattern.compile("^\\s*double\\s").matcher(lines[i]).find()){
                doubleTreatment(lines[i]);
            }
            //------Verifica se é string-----//
            else if(Pattern.compile("^\\s*string\\s").matcher(lines[i]).find()){
                stringTreatment(lines[i]);
            }
            //------Verifica se é scan-----//
            else if(Pattern.compile("^\\s*scan[\\s]*[(]").matcher(lines[i]).find()){
                scanTreatment(lines[i]);
            }
            //------Verifica se é if-----//
            else if(Pattern.compile("^\\s*if[\\s]*[(]").matcher(lines[i]).find()){
                i = ifTreatment(lines, i);
            }
            //------Verifica se é for-----//
            else if(Pattern.compile("^\\s*for[\\s]*[(]").matcher(lines[i]).find()){
                i = forTreatment(lines, i);
            }
            //------Verifica se é while-----//
            else if(Pattern.compile("^\\s*while[\\s]*[(]").matcher(lines[i]).find()){
                i = whileTreatment(lines, i);
            }
            else if(!Pattern.compile("^\\s*$").matcher(lines[i]).find()){
                variableTreatment(lines[i]);
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

                        if(variables.containsKey(inComma[k])){
                            inComma[k] = (variables.get(inComma[k]).getValue()).toString();
                        }
                        else{
                            if(inComma[k].indexOf(".") != -1){
                                inQuotes = inQuotes.replaceFirst("\\{}", Operation.chooseOperation(inComma[k], "double", variables).toString());
                            } 
                            else {
                                inQuotes = inQuotes.replaceFirst("\\{}", Operation.chooseOperation(inComma[k], "int", variables).toString());
                            }
                        }
                        inQuotes = inQuotes.replaceFirst("\\{}", inComma[k]);
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
            int value = (int) Operation.chooseOperation(concatLine, "int", variables);
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
            float value = (float) Operation.chooseOperation(concatLine, "float", variables);
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
            double value = (double) Operation.chooseOperation(concatLine, "double", variables);
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
            String[] varName = Util.lineInWordArray(splitStr[0]);
            
            int first = line.indexOf("'");
            int second = line.lastIndexOf("'");
            
            String inQuotes = line.substring(first, second+1);
            
            if(inQuotes.indexOf("+") != -1){
                STRcomplete = Operation.concatString(inQuotes);
                integer = new VarString(varName[1], STRcomplete);
            }
            else{
                String splitQuotes[] = inQuotes.split("'");
                integer = new VarString(varName[1], splitQuotes[1]);
            }
        
        }
        else{
            String[] varName = Util.lineInWordArray(line);
            integer = new VarString(varName[1]);
        }     
        variables.put(integer.name, integer);
    }

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

    private int ifTreatment(String[] lines, int posLine){
        int countIf = 1;
        int firstParenth = lines[posLine].indexOf("(");
        int lastParenth = lines[posLine].lastIndexOf(")");
        String condition = lines[posLine].substring(firstParenth+1,lastParenth);
        String [] parseInsideIf = new String[0];
        String [] parseInsideElse = new String[0];

        boolean findElse = false; // diz se encontramos um else nas linhas a partir do if

        while(countIf > 0){
            posLine++;

            if(!findElse){
                parseInsideIf = Util.appendArray(parseInsideIf.length, parseInsideIf, lines[posLine]);
            }
            else{
                parseInsideElse = Util.appendArray(parseInsideElse.length, parseInsideElse, lines[posLine]);
            }

            if(lines[posLine].indexOf("else") != -1 && countIf == 1){
                findElse = true;
            }
            if(lines[posLine].indexOf("endif") != -1){
                countIf--;
            }
            else if(lines[posLine].indexOf("if") != -1){
                countIf++;
            }
        }
        if(Condition.isTrue(condition, variables)){
            parseInsideIf = Util.removeArray(parseInsideIf.length, parseInsideIf, parseInsideIf.length-1);
            parseLines(parseInsideIf);
        }
        else{
            parseInsideElse = Util.removeArray(parseInsideElse.length, parseInsideElse, parseInsideElse.length-1);
            parseLines(parseInsideElse);
        }
        if(posLine+1 > lines.length){
            return posLine;
        }
        return posLine++;
    }

    private int forTreatment(String[] lines, int posLine){
        int countFor = 1;
        int firstParenth = lines[posLine].indexOf("(");
        int lastParenth = lines[posLine].lastIndexOf(")");
        String [] forController = lines[posLine].substring(firstParenth+1,lastParenth).split(";");

        String [] parseInsideFor = new String[0];
        while(countFor > 0){
            posLine++;
            parseInsideFor = Util.appendArray(parseInsideFor.length, parseInsideFor, lines[posLine]);
            
            if(lines[posLine].indexOf("endfor") != -1){
                countFor--;
            }
            else if(lines[posLine].indexOf("for") != -1){
                countFor++;
            }
        }
        parseInsideFor = Util.removeArray(parseInsideFor.length, parseInsideFor, parseInsideFor.length-1);

        if(forController[0].indexOf("int") != -1){
            intTreatment(forController[0]);
        }
        else if(forController[0].indexOf("=") != -1){
            variableTreatment(forController[0]);
        }
        else if(!variables.containsKey(forController[0].trim())){
            System.out.println("Variavel não encontrada!");
            System.exit(0);
        }

        while(Condition.isTrue(forController[1], variables)){
            parseLines(parseInsideFor);
            variableTreatment(forController[2]);
        }
        if(forController[0].indexOf("int") != -1){
            String [] firstInstance = Util.lineInWordArray(forController[0].substring(0, forController[0].indexOf("=")));
            variables.remove(firstInstance[1]);
        }
        
        if(posLine+1 > lines.length){
            return posLine;
        }
        return posLine++;
    }

    private int whileTreatment(String[] lines, int posLine){
        int countWhile = 1;
        int firstParenth = lines[posLine].indexOf("(");
        int lastParenth = lines[posLine].lastIndexOf(")");
        String condition = lines[posLine].substring(firstParenth+1,lastParenth);

        String [] parseInsideWhile = new String[0];
        while(countWhile > 0){
            posLine++;
            parseInsideWhile = Util.appendArray(parseInsideWhile.length, parseInsideWhile, lines[posLine]);
            
            if(lines[posLine].indexOf("endwhile") != -1){
                countWhile--;
            }
            else if(lines[posLine].indexOf("while") != -1){
                countWhile++;
            }
        }
        parseInsideWhile = Util.removeArray(parseInsideWhile.length, parseInsideWhile, parseInsideWhile.length-1);

        while(Condition.isTrue(condition, variables)){
            parseLines(parseInsideWhile);
        }
        
        if(posLine+1 > lines.length){
            return posLine;
        }
        return posLine++;
    }

    private void variableTreatment(String line){
        String concatLine = "";
        String [] preOperator = new String[0];
        String[] posOperator = new String[0];
        int operatorPosition = 0;

        if(line.indexOf("+=") != -1){
            operatorPosition = line.indexOf("+=");
            preOperator = Util.lineInWordArray(line.substring(0, operatorPosition));
            posOperator = Util.lineInWordArray(line.substring(operatorPosition+2, line.length()));
            concatLine += preOperator[0]+"+";
            for(int j = 0 ; j < posOperator.length ; j++){  //coloca td expressao em uma unica string
                concatLine = concatLine.concat(posOperator[j]);
            }
        }

        else if(line.indexOf("-=") != -1){
            operatorPosition = line.indexOf("-=");
            preOperator = Util.lineInWordArray(line.substring(0, operatorPosition));
            posOperator = Util.lineInWordArray(line.substring(operatorPosition+2, line.length()));
            concatLine += preOperator[0]+"-";
            for(int j = 0 ; j < posOperator.length ; j++){  //coloca td expressao em uma unica string
                concatLine = concatLine.concat(posOperator[j]);
            }
        }

        else if(line.indexOf("*=") != -1){
            operatorPosition = line.indexOf("*=");
            preOperator = Util.lineInWordArray(line.substring(0, operatorPosition));
            posOperator = Util.lineInWordArray(line.substring(operatorPosition+2, line.length()));
            concatLine += preOperator[0]+"*";
            for(int j = 0 ; j < posOperator.length ; j++){  //coloca td expressao em uma unica string
                concatLine = concatLine.concat(posOperator[j]);
            }
        }

        else if(line.indexOf("/=") != -1){
            operatorPosition = line.indexOf("/=");
            preOperator = Util.lineInWordArray(line.substring(0, operatorPosition));
            posOperator = Util.lineInWordArray(line.substring(operatorPosition+2, line.length()));
            concatLine += preOperator[0]+"/";
            for(int j = 0 ; j < posOperator.length ; j++){  //coloca td expressao em uma unica string
                concatLine = concatLine.concat(posOperator[j]);
            }
        }

        else if(line.indexOf("=") != -1){
            operatorPosition = line.indexOf("=");
            preOperator = Util.lineInWordArray(line.substring(0, operatorPosition));
            posOperator = Util.lineInWordArray(line.substring(operatorPosition+1, line.length()));
            for(int j = 0 ; j < posOperator.length ; j++){  //coloca td expressao em uma unica string
                concatLine = concatLine.concat(posOperator[j]);
            }
        }

        else if(line.indexOf("++") != -1){
            operatorPosition = line.indexOf("++");
            preOperator = Util.lineInWordArray(line.substring(0, operatorPosition));
            concatLine += preOperator[0]+"+1";
        }

        else if(line.indexOf("--") != -1){
            operatorPosition = line.indexOf("--");
            preOperator = Util.lineInWordArray(line.substring(0, operatorPosition));
            concatLine += preOperator[0]+"-1";
        }

        Variable search = this.variables.get(preOperator[0]);
        if(search == null){
            System.out.println("Variavel não encontrada!");
            return;
        }

        else{
            if(operatorPosition != -1){ //Verifica se tem sinal de =
                if(search.type.equals("int")){
                    int value = (int) Operation.chooseOperation(concatLine, "int", variables);
                    search.setValue(value);
                }
                else if(search.type.equals("float")){
                    float value = (float) Operation.chooseOperation(concatLine, "float", variables);
                    search.setValue(value);
                }
                else if(search.type.equals("double")){
                    double value = (double) Operation.chooseOperation(concatLine, "double", variables);
                    search.setValue(value);
                }
                else if(search.type.equals("string")){
                    String value = Operation.concatAfterDeclaration(line);
                    search.setValue(value);
                }
            }
        }        
    }

    private void printVariables(){
        System.out.println(" Variáveis ");
        for (String key : variables.keySet()) {
            Variable variable = variables.get(key);
            System.out.println("Nome: "+key+" | Valor: "+variable.getValue()+" | Tipo: "+variable.type);
        }
    }
}

