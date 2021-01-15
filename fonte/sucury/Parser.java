package fonte.sucury;

// Analisa as linhas escaneadas e chama os métodos necessários

// Alisson Peloso     <alisson.luan2000@gmail.com>
// Eduardo Lazaretti  <eduardolazaretti3@gmail.com>
// Guilherme Graeff   <guilherme.rafael.graeff@gmail.com>
// Stefani Meneghetti <meneghettistefani@gmail.com> 

import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.LinkedHashMap;

public class Parser {
    public static boolean findReturn;
    public static int currentLine;
    protected Map<String, Variable> variables;
    protected Map<String, Function> functions;
    protected String returnType;

    Parser(){
        this.variables = new LinkedHashMap<>();
        this.functions = new LinkedHashMap<>();
        currentLine = 1;
        this.returnType = "void";
        this.findReturn = false;
    }

    Parser(Map<String, Variable> variables, Map<String, Function> functions){
        this.variables = new LinkedHashMap<>();
        this.functions = new LinkedHashMap<>();
        this.variables.putAll(variables);
        this.functions.putAll(functions);
        this.returnType = "void";
        this.findReturn = false;

    }

    Parser(Map<String, Variable> variables, Map<String, Function> functions, String returnType){
        this.variables = new LinkedHashMap<>();
        this.functions = new LinkedHashMap<>();
        this.variables.putAll(variables);
        this.functions.putAll(functions);
        this.returnType = returnType;
        this.findReturn = false;

    }

    public static void main(String[] args) {
        String a = "'oi' + 'boi'";
        Matcher b = Pattern.compile("'*\\s*[\\+]\\s*'*").matcher(a);
        if(b.find()){
            System.out.println(b.group());
        }
    }

    public void parseLines(String[] lines){
        try {
            for (int i = 0; i < lines.length ; i++) {
                if(findReturn){
                    break;
                }

                lines[i] = verifyFunction(lines[i]);

                //------Verifica se é print-----//
                if(Pattern.compile("^\\s*print[\\s]*[(]").matcher(lines[i]).find()){
                    printTreatment(lines[i],false);
                }
                //------Verifica se é println-----//
                else if(Pattern.compile("^\\s*println[\\s]*[(]").matcher(lines[i]).find()){
                    printTreatment(lines[i],true);
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
                else if(Pattern.compile("^\\s*def\\s").matcher(lines[i]).find()){
                    i = defTreatment(lines, i);
                }
                else if(Pattern.compile("^\\s*return\\s*").matcher(lines[i]).find()){
                    returnTreatment(lines[i]);
                    findReturn = true;
                }
                else if(!Pattern.compile("^\\s*$").matcher(lines[i]).find()){
                    variableTreatment(lines[i]);
                }
                currentLine++;
            }
        } catch (SucuryException e) {
            e.printException(currentLine);
            System.exit(1);
        }
    }

    private void printTreatment(String line, boolean withLn) throws SucuryException {
        if(Pattern.compile("^\\s*println[\\s]*[(]+[])]").matcher(line).find() || Pattern.compile("^\\s*print[\\s]*[(]+[])]").matcher(line).find()){
            System.out.printf("");
        }
        else if(line.indexOf("'") != -1 && !Pattern.compile("'*\\s*[\\+]\\s*'*").matcher(line).find()){
            int first = line.indexOf("'");
            int second = line.indexOf("'", first+1);
            String inQuotes = line.substring(first+1, second);
            
            if(Pattern.compile("%[ifs]").matcher(inQuotes).find()){
                if(line.indexOf(",", second) == -1){
                    SucuryException exception = new SucuryException("Não foram encontradas variáveis para imprimir");
                    throw exception;
                }
                String [] inComma = (line.substring(line.indexOf(",", second), line.length()-1)).split(",");
                for(int j = 1; j < inComma.length; j++){
                    inComma[j] = inComma[j].replaceAll("\\s+", "").trim();
                }
 
                Matcher braces = Pattern.compile("%[ifs]").matcher(inQuotes);

                for(int k = 1; k < inComma.length; k++){
                    if(braces.find()){
                        String findType = braces.group();

                        if(findType.indexOf("f") != -1){
                            inQuotes = inQuotes.replaceFirst(findType, Operation.chooseOperation(inComma[k], "double", variables).toString());
                        }
                        if(findType.indexOf("i") != -1){
                            inQuotes = inQuotes.replaceFirst(findType, Operation.chooseOperation(inComma[k], "int", variables).toString());
                        }
                        if(findType.indexOf("s") != -1){
                            inQuotes = inQuotes.replaceFirst(findType, Operation.chooseOperation(inComma[k], "string", variables).toString());
                        }
                    }
                    else{
                        SucuryException exception = new SucuryException("Não foi possível imprimir");
                        throw exception;
                    }
                }
            }
            inQuotes = inQuotes.replace("\\n", "\n");
            inQuotes = inQuotes.replace("\\t", "\t");

            System.out.printf(inQuotes);
        }
        else{
            String result = "";
            int first = line.indexOf("(");
            int second = line.indexOf(")", first+1);
            String expression = line.substring(first+1, second);
            expression = expression.replaceAll("\\s+[\\+]\\s+", "+").trim();
            
            String findVariables = expression;
            findVariables = findVariables.replaceAll("\\+", " ");
            findVariables = findVariables.replaceAll("\\-", " ");
            findVariables = findVariables.replaceAll("\\/", " ");
            findVariables = findVariables.replaceAll("\\*", " ");
            findVariables = findVariables.replaceAll("\\(", " ");
            findVariables = findVariables.replaceAll("\\)", " ");
            String [] variableVerification = Util.lineInWordArray(findVariables);

            boolean haveInt = false;
            boolean haveFloat = false;
            boolean haveString = false;

            for(int i = 0; i < variableVerification.length; i++){
                if(variables.containsKey(variableVerification[i])){
                    if(variables.get(variableVerification[i]).type.equals("string")){
                        variableVerification[i] = "'"+variables.get(variableVerification[i]).getValue().toString()+"'";
                    }
                    else{
                        variableVerification[i] = variables.get(variableVerification[i]).getValue().toString();
                    }
                }
                if(isInt(variableVerification[i])){
                    haveInt = true;
                }
                else if(isFloat(variableVerification[i])){
                    haveFloat = true;
                }
                else if(isString(variableVerification[i])){
                    haveString = true;
                }
            }

            if(haveInt && !haveString && !haveFloat){
                result = Operation.chooseOperation(expression, "int", variables).toString();
            }
            else if(!haveString && haveFloat){
                result = Operation.chooseOperation(expression, "double", variables).toString();
            }
            else if(haveString){
                result = Operation.chooseOperation(expression, "string", variables).toString();
            }
            else{
                SucuryException exception = new SucuryException("Não foi possível imprimir");
                throw exception;
            }
            System.out.printf(result);
        } 
        if(withLn){
            System.out.println();
        }
    }

    private void intTreatment(String line) throws SucuryException {
        VarInt integer;
        String concatLine = "";
        int equalPosition = line.indexOf("=");
        String [] preEquals =Util.lineInWordArray(line);
        varNameValidate(preEquals[1]);
        
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

    private void floatTreatment(String line) throws SucuryException {
        VarFloat pfloat;
        String concatLine = "";
        int equalPosition = line.indexOf("=");
        String [] preEquals =Util.lineInWordArray(line);
        varNameValidate(preEquals[1]);

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

    private void doubleTreatment(String line) throws SucuryException {
        VarDouble pfloat;
        String concatLine = "";
        int equalPosition = line.indexOf("=");
        String [] preEquals =Util.lineInWordArray(line);
        varNameValidate(preEquals[1]);


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
    
    private void stringTreatment(String line) throws SucuryException {
        VarString string;
        if(line.indexOf("=") != -1){
            
            String STRcomplete;
            String[] splitStr = line.split("=");
            String[] varName = Util.lineInWordArray(splitStr[0]);
            varNameValidate(varName[1]);
            
            STRcomplete = (String) Operation.chooseOperation(splitStr[1], "string", variables);

            string = new VarString(varName[1], STRcomplete);
        }
        else{
            String[] varName = Util.lineInWordArray(line);
            string = new VarString(varName[1]);
        }     
        variables.put(string.name, string);
    }

    private void scanTreatment(String line) throws SucuryException {
        String variable = getParenthesisContent(line);
        if(variables.containsKey(variable)){
            Scanner scan = new Scanner(System.in);
            String lineScan = scan.nextLine();

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
            SucuryException exception = new SucuryException("Variável não encontrada");
            throw exception;
        }
    }

    private int ifTreatment(String[] lines, int posLine) throws SucuryException {
        Parser newParser = new Parser(variables, functions, returnType);
        int countIf = 1;
        String condition =  getParenthesisContent(lines[posLine]);
        String [] parseInsideIf = new String[0];
        String [] parseInsideElse = new String[0];

        boolean findElse = false; // diz se encontramos um else nas linhas a partir do if

        while(countIf > 0){
            posLine++;
            if(posLine >= lines.length){
                SucuryException exception = new SucuryException("Erro de sintaxe", "endif não encontrado");
                throw exception;
            }
            if(!findElse){
                parseInsideIf = Util.appendArray(parseInsideIf.length, parseInsideIf, lines[posLine]);
            }
            else{
                parseInsideElse = Util.appendArray(parseInsideElse.length, parseInsideElse, lines[posLine]);
            }

            if(Pattern.compile("^\\s*else[\\s]*$").matcher(lines[posLine]).find() && countIf == 1){
                findElse = true;
            }
            if(Pattern.compile("^\\s*endif[\\s]*$").matcher(lines[posLine]).find()){
                countIf--;
            }
            else if(Pattern.compile("^\\s*if[\\s]*[(]").matcher(lines[posLine]).find()){
                countIf++;
            }
        }
        currentLine++;

        if(Condition.isTrue(condition, newParser.variables)){
            newParser.parseLines(Util.removeArray(parseInsideIf.length, parseInsideIf, parseInsideIf.length-1));
            currentLine += parseInsideElse.length;
        }
        
        else{
            currentLine += parseInsideIf.length;
            newParser.parseLines(Util.removeArray(parseInsideElse.length, parseInsideElse, parseInsideElse.length-1));
            if(parseInsideElse.length == 0){
                currentLine--;
            }
        }

        for (String key : variables.keySet()) {
            variables.get(key).setValue(newParser.variables.get(key).getValue());
        }
        
        return posLine;
    }

    private int forTreatment(String[] lines, int posLine) throws SucuryException {
        int forInitialLine = currentLine+1;
        Parser newParser = new Parser(variables, functions, returnType);
        int countFor = 1;
        String [] forController =  getParenthesisContent(lines[posLine]).split(";");
        forParametersValidate(forController, lines[posLine]);

        String [] parseInsideFor = new String[0];
        while(countFor > 0){
            posLine++;
            if(posLine >= lines.length){
                SucuryException exception = new SucuryException("Erro de sintaxe", "endfor não encontrado");
                throw exception;
            }
            parseInsideFor = Util.appendArray(parseInsideFor.length, parseInsideFor, lines[posLine]);
            
            if(Pattern.compile("^\\s*endfor[\\s]*$").matcher(lines[posLine]).find()){
                countFor--;
            }
            else if(Pattern.compile("^\\s*for[\\s]*[(]").matcher(lines[posLine]).find()){
                countFor++;
            }
        }
        parseInsideFor = Util.removeArray(parseInsideFor.length, parseInsideFor, parseInsideFor.length-1);

        if(Pattern.compile("^\\s*int\\s").matcher(forController[0]).find()){
            newParser.intTreatment(forController[0]);
        }
        else if(forController[0].indexOf("=") != -1){
            newParser.variableTreatment(forController[0]);
        }
        else if(!variables.containsKey(forController[0].trim())){
            SucuryException exception = new SucuryException("Variável não encontrada");
            throw exception;
        }
        while(Condition.isTrue(forController[1], newParser.variables)){
            currentLine = forInitialLine;
            newParser.parseLines(parseInsideFor);
            newParser.variableTreatment(forController[2]);
        }
        for (String key : variables.keySet()) {
            variables.get(key).setValue(newParser.variables.get(key).getValue());
        }
        if(posLine+1 >= lines.length){
            return posLine;
        }
        return posLine++;
    }

    private int whileTreatment(String[] lines, int posLine) throws SucuryException {
        int whileInitialLine = currentLine+1;
        Parser newParser = new Parser(variables, functions, returnType);
        int countWhile = 1;
        String condition =  getParenthesisContent(lines[posLine]);

        String [] parseInsideWhile = new String[0];
        while(countWhile > 0){
            posLine++;
            if(posLine >= lines.length){
                SucuryException exception = new SucuryException("Erro de sintaxe", "endwhile não encontrado");
                throw exception;
            }
            parseInsideWhile = Util.appendArray(parseInsideWhile.length, parseInsideWhile, lines[posLine]);
            
            if(Pattern.compile("^\\s*endwhile[\\s]*$").matcher(lines[posLine]).find()){
                countWhile--;
            }
            else if(Pattern.compile("^\\s*while[\\s]*[(]").matcher(lines[posLine]).find()){
                countWhile++;
            }
        }
        parseInsideWhile = Util.removeArray(parseInsideWhile.length, parseInsideWhile, parseInsideWhile.length-1);

        while(Condition.isTrue(condition, newParser.variables)){
            currentLine = whileInitialLine;
            newParser.parseLines(parseInsideWhile);
        }

        for (String key : variables.keySet()) {
            variables.get(key).setValue(newParser.variables.get(key).getValue());
        }
        
        if(posLine+1 > lines.length){
            return posLine;
        }
        return posLine++;
    }

    private void variableTreatment(String line) throws SucuryException {
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
        else {
            SucuryException exception = new SucuryException("Sintaxe inválida", line);
            throw exception;
        }

        Variable search = this.variables.get(preOperator[0]);
        if(search == null){
            SucuryException exception = new SucuryException("Variável não encontrada", preOperator[0]);
            throw exception;
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
                    String value = (String) Operation.chooseOperation(concatLine, "string", variables);
                    search.setValue(value);
                }
            }
        }        
    }

    public void printVariables(){
        System.out.println(" Variáveis ");
        for (String key : variables.keySet()) {
            Variable variable = variables.get(key);
            System.out.println("Nome: "+key+" | Valor: "+variable.getValue()+" | Tipo: "+variable.type);
        }
    }

    private void printFunctions(){
        System.out.println(" Funções ");
        for (String key : functions.keySet()) {
            Function function = functions.get(key);
            System.out.println("Nome: "+key+" | Tipo de retorno: "+function.returnType);
        }
    }

    private String getParenthesisContent(String line) throws SucuryException {
        int firstParenth = line.indexOf("(");
        int lastParenth = line.lastIndexOf(")");

        if (firstParenth == -1 || lastParenth == -1) {
            SucuryException exception = new SucuryException("Erro de sintaxe", line.trim() ,"Número incorreto de parênteses");
            throw exception;
        }

        String betweenParenthesis = line.substring(firstParenth+1,lastParenth);
        return betweenParenthesis.replaceAll("\\s+", " ").trim();
    }

    private String getFunctionParameters(String line) {
        int countParenthesis = 1;
        int firstParenth = 0;
        int lastParenth = -1;

        for(int i = 1; i < line.length(); i++){
            if(line.charAt(i) == '('){
                countParenthesis++;
            }
            else if(line.charAt(i) == ')'){
                countParenthesis--;
            }
            if(countParenthesis == 0){
                lastParenth = i;
                break;
            }
        }
        return line.substring(firstParenth+1,lastParenth);
    }

    private void varNameValidate(String varName) throws SucuryException {
        if(Pattern.compile("^for$|^while$|^if$|^endfor$|^endwhile$|^endif$|^print$|^println$|^scan$|^int$|^float$|^string$|^double$|^return$|^def$|^enddef$").matcher(varName).find()){
            SucuryException exception = new SucuryException("Não é possível declarar uma variável com o nome", varName, "O nome inserido é uma palavra reservada");
            throw exception;
        } 
        if(Pattern.compile("[^a-zA-Z0-9_]").matcher(varName).find()){
            SucuryException exception = new SucuryException("Não é possível declarar uma variável com o nome", varName, "O nome inserido possui caracteres inválidos");
            throw exception;
        } 
        if(Pattern.compile("^[0-9]").matcher(varName).find()){
            SucuryException exception = new SucuryException("Não é possível declarar uma variável com o nome", varName, "Nomes de variáveis não podem iniciar com números");
            throw exception;
        }
    }

    private void forParametersValidate(String [] forController, String forLine) throws SucuryException {
        if(forController.length != 3) {
            SucuryException exception = new SucuryException("Erro de sintaxe", forLine ,"Número incorreto de parâmetros");
            throw exception;
        }
        if(Pattern.compile("^\\s*$").matcher(forController[0]).find() ||
           Pattern.compile("^\\s*$").matcher(forController[1]).find() ||
           Pattern.compile("^\\s*$").matcher(forController[2]).find()) {
            SucuryException exception = new SucuryException("Erro de sintaxe", forLine ,"Não são permitidos parâmetros vazios");
            throw exception;
        }        
    }

    private int defTreatment(String [] lines, int position) throws SucuryException{
        String[] parameters = getParenthesisContent(lines[position]).split(",");
        String[] functionDef = Util.lineInWordArray(lines[position].substring(0,lines[position].indexOf("(")));
        String[] functionLines = new String[0];

        Parser defParser = new Parser();
        defParser.parseLines(parameters);
        currentLine -= parameters.length-1;

        while(!Pattern.compile("^\\s*enddef[\\s]*$").matcher(lines[position]).find()){
            position++;
            if(position >= lines.length){
                SucuryException exception = new SucuryException("Erro de sintaxe", "enddef não encontrado");
                throw exception;
            }
            functionLines = Util.appendArray(functionLines.length, functionLines, lines[position]);
        }
        functionLines = Util.removeArray(functionLines.length, functionLines, functionLines.length-1);

        Function function = new Function(functionDef[1], functionDef[2], defParser.variables, functionLines, currentLine);
        this.functions.put(function.name, function);

        currentLine+=functionLines.length;

        if(position+1 > lines.length){
            return position;
        }
        return position++;
    }

    private void returnTreatment(String line) throws SucuryException{ 
        line = line.trim(); 
        if(this.returnType.equals("void")){
            VarString var = new VarString("return");
            variables.put("return", var);
        }
        else{
            String operation = line.substring(line.indexOf(" "), line.length());
    
            if(this.returnType.equals("int")){
                VarInt var = new VarInt("return", (int) Operation.chooseOperation(operation.replaceAll("\\s+", ""), "int", variables));
                variables.put("return", var);
            }
            else if(this.returnType.equals("double")){
                VarDouble var = new VarDouble("return", (double) Operation.chooseOperation(operation.replaceAll("\\s+", ""), "double", variables));
                variables.put("return", var);
            }
            else if(this.returnType.equals("float")){
                VarFloat var = new VarFloat("return", (float) Operation.chooseOperation(operation.replaceAll("\\s+", ""), "float", variables));
                variables.put("return", var);
            }
            else if(this.returnType.equals("string")){
                operation = operation.replaceAll("\\s+[\\+]\\s+", "+").trim();
                VarString var = new VarString("return", (String) Operation.chooseOperation(operation, "string", variables));

                variables.put("return", var);
            }
        }
        
    }

    private String verifyFunction(String line) throws SucuryException{
        for (String key : functions.keySet()) {            
            String regex = "[^0-9a-zA-ZÁÀÂÃÉÈÊÍÏÓÔÕÖÚÇÑáàâãéèêíïóôõöúçñ]{0,1}"+key+"[\\s]*[(]";
            Matcher getFunction = Pattern.compile(regex).matcher(line);                          

            while(getFunction.find()){
                String completeParameters = getFunctionParameters(line.substring(getFunction.end()-1));
                String [] functionParameters = completeParameters.split(",");
                Function function = functions.get(key);
                String functionSummon = getFunction.group() + completeParameters + ")";
                if(!Pattern.compile("^\\s*$").matcher(completeParameters).find()){
                    if(Pattern.compile("^[^a-zA-Z_]").matcher(functionSummon).find()){
                        functionSummon = functionSummon.substring(1);
                    }                
                    int i = 0;
    
                    if(functionParameters.length != function.parameters.size()){
                        SucuryException exception = new SucuryException("Erro de sintaxe", line ,"Número incorreto de parâmetros");
                        throw exception;
                    }
    
                    for(String parameterName : function.parameters.keySet()){
                        functionParameters[i] = functionParameters[i].replaceAll("\\s+", "");
                        function.parameters.get(parameterName).setValue(Operation.chooseOperation(functionParameters[i], function.parameters.get(parameterName).type, variables));
                        i++;
                    }
                }
                function.runFuncion(functions);

                if(line.indexOf("=") == -1 || function.returnType.equals("void")){ //? Tá verificando se tem sinal de igual ou
                    line = line.replace(functionSummon, " ");                        //? é void pra substituir por vazio
                }

                else{
                    line = line.replace(functionSummon, function.functionReturn.getValue().toString());
                }
                getFunction = Pattern.compile(regex).matcher(line);
            }

        }
        return line;
    }

    private boolean isInt(String expression){
        if(Pattern.compile("^[\\-]{0,1}[0-9]+$").matcher(expression).find()){
            return true;
        }
        return false;
    }

    private boolean isFloat(String expression){
        if(Pattern.compile("^[\\-]{0,1}[0-9]*[.]{0,1}[0-9]+$").matcher(expression).find()){
            return true;
        }
        return false;
    }

    private boolean isString(String expression){
        if(!isInt(expression) && !isFloat(expression) && expression.indexOf("'") != -1){
            return true;
        }
        return false;
    }
}
