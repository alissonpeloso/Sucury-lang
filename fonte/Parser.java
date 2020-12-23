import java.util.HashMap;
import java.util.Map;

public class Parser {
    public static int currentLine;
    protected Map<String, Variable> variables;

    public static void main(String[] args) {
        String[] oi = new String[1];
        oi[0] = "double a= 2+498494";

        Parser alo = new Parser();
        alo.parseLines(oi);
        System.out.println(alo.variables.get("a").getValue());
    }

    Parser(){
        this.variables = new HashMap<>();
        currentLine = 1;
    }

    public void parseLines(String[] lines){
        for (int i = 0; i < lines.length ; i++) {
        
            //------Verifica se e int-----//
            if(lines[i].indexOf("int") != -1){
                VarInt integer;
                String concatLine = "";
                int equalPosition = lines[i].indexOf("=");
                String [] preEquals = Util.lineInWordArray(lines[i].substring(0, equalPosition));

                if(equalPosition != -1){ //Verifica se tem sinal de =
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
            if(lines[i].indexOf("float") != -1){
                VarFloat pfloat;
                String concatLine = "";
                int equalPosition = lines[i].indexOf("=");
                String [] preEquals = Util.lineInWordArray(lines[i].substring(0, equalPosition));

                if(equalPosition != -1){ //Verifica se tem sinal de =
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
            if(lines[i].indexOf("double") != -1){
                VarDouble pfloat;
                String concatLine = "";
                int equalPosition = lines[i].indexOf("=");
                String [] preEquals = Util.lineInWordArray(lines[i].substring(0, equalPosition));

                if(equalPosition != -1){ //Verifica se tem sinal de =
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
        }
    }
}

