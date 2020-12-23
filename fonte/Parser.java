import java.util.HashMap;
import java.util.Map;

public class Parser {
    public static int currentLine;
    protected Map<String, Variable> variables;

    public static void main(String[] args) {
        String[] oi = new String[2];
        oi[0] = "int a = 8282/2";
        oi[1] = "float b = 5.14 * 900";

        Parser alo = new Parser();
        alo.parseLines(oi);
        System.out.println(alo.variables.get("a").getValue());
        System.out.println(alo.variables.get("b").getValue());
    }

    Parser(){
        this.variables = new HashMap<>();
        currentLine = 1;
    }

    public void parseLines(String[] lines){
        for (int i = 0; i < lines.length ; i++) {
            String[] line = lines[i].split(" ");//splita nos espacos
        
            //------Verifica se e int-----//
            if("int".equals(line[0])){
                VarInt integer;
                String concatLine = "";
                if(line.length>2){ //Verifica se tem sinal de =
                    for(int j = 3 ; line.length-1 >= j ; j++){  //coloca td expressao em uma unica string
                        concatLine = concatLine.concat(line[j]);
                    }
                    int value = (int) Operation.chooseOperation(concatLine, "int");
                    integer = new VarInt(line[1], value);
                }
                else{
                    integer = new VarInt(line[1]);
                }
                variables.put(integer.name, integer);
            } 

            //------Verifica se e float-----//
            if("float".equals(line[0])){
                VarFloat pfloat;
                String concatLine = "";
                if(line.length>2){ //Verifica se tem sinal de =
                    for(int j = 3 ; line.length-1 >= j ; j++){  //coloca td expressao em uma unica string
                        concatLine = concatLine.concat(line[j]);
                    }
                    float value = (float) Operation.chooseOperation(concatLine, "float");
                    pfloat = new VarFloat(line[1], value);
                }
                else{
                    pfloat = new VarFloat(line[1]);
                }
                variables.put(pfloat.name, pfloat);
            } 

            //------Verifica se e double-----//
            if("double".equals(line[0])){
                VarDouble pfloat;
                String concatLine = "";
                if(line.length>2){ //Verifica se tem sinal de =
                    for(int j = 3 ; line.length-1 >= j ; j++){  //coloca td expressao em uma unica string
                        concatLine = concatLine.concat(line[j]);
                    }
                    double value = (double) Operation.chooseOperation(concatLine, "double");
                    pfloat = new VarDouble(line[1], value);
                }
                else{
                    pfloat = new VarDouble(line[1]);
                }
                variables.put(pfloat.name, pfloat);
            } 
        }
    }
}

