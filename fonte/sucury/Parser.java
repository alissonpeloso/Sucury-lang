package fonte.sucury;
import java.util.HashMap;
import java.util.Map;

public class Parser {
    public static int currentLine;
    protected Map<String, Variable> variables;

    public static void main(String[] args) {
        String[] oi = new String[2];
        oi[0] = "int a = 4598";
        oi[1] = "print('else fodase {} {} teu pai é corno\n', 5.555, a )";

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
                if(lines[i].indexOf("(") != -1 && lines[i].indexOf(")") != -1 ){
                    int first = lines[i].indexOf("'");
                    int second = lines[i].indexOf("'", first+1);
                    String inQuotes = lines[i].substring(first+1, second);
                    
                    if(inQuotes.indexOf("{}") != -1){
                        if(lines[i].indexOf(",", second) == -1){
                            System.out.println("Não foram encontradas variaveis para imprimir");
                            System.exit(0);
                        }
                        String [] inComma = (lines[i].substring(lines[i].indexOf(",", second), lines[i].length()-1)).split(",");
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

            //------Verifica se e int-----//
            else if(lines[i].indexOf("int") != -1){
                VarInt integer;
                String concatLine = "";
                int equalPosition = lines[i].indexOf("=");
                String [] preEquals =Util.lineInWordArray(lines[i]);
                
                if(equalPosition != -1){ //Verifica se tem sinal de =
                    preEquals = Util.lineInWordArray(lines[i].substring(0, equalPosition));
                    String[] posEquals = Util.lineInWordArray(lines[i].substring(equalPosition+1, lines[i].length()));
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

            //------Verifica se e float-----//
            else if(lines[i].indexOf("float") != -1){
                VarFloat pfloat;
                String concatLine = "";
                int equalPosition = lines[i].indexOf("=");
                String [] preEquals =Util.lineInWordArray(lines[i]);

                if(equalPosition != -1){ //Verifica se tem sinal de =
                    preEquals = Util.lineInWordArray(lines[i].substring(0, equalPosition));
                    String[] posEquals = Util.lineInWordArray(lines[i].substring(equalPosition+1, lines[i].length()));
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

            //------Verifica se e double-----//
            else if(lines[i].indexOf("double") != -1){
                VarDouble pfloat;
                String concatLine = "";
                int equalPosition = lines[i].indexOf("=");
                String [] preEquals =Util.lineInWordArray(lines[i]);

                if(equalPosition != -1){ //Verifica se tem sinal de =
                    preEquals = Util.lineInWordArray(lines[i].substring(0, equalPosition));
                    String[] posEquals = Util.lineInWordArray(lines[i].substring(equalPosition+1, lines[i].length()));
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
                    }
                }
            }
        }
    }
}

