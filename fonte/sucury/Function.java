package fonte.sucury;

// Tratamento de funções da Sucury

// Alisson Peloso     <alisson.luan2000@gmail.com>
// Eduardo Lazaretti  <eduardolazaretti3@gmail.com>
// Guilherme Graeff   <guilherme.rafael.graeff@gmail.com>
// Stefani Meneghetti <meneghettistefani@gmail.com> 

import java.util.Map;
import java.util.LinkedHashMap;

public class Function {
    protected String returnType;
    protected String name;
    protected Map<String, Variable> parameters;
    protected String[] lines;
    protected Variable functionReturn;
    private int initialLine;

    Function(String returnType, String name, Map<String, Variable> parameters, String[] lines, int initialLine){
        this.returnType = returnType;
        this.name = name;
        this.parameters = new LinkedHashMap<>();
        this.parameters.putAll(parameters);
        this.lines = lines;
        this.initialLine = initialLine;
    }

    protected void runFuncion(Map<String, Function> functions){
        Parser functionParser = new Parser(parameters,functions,returnType);
        
        if(this.returnType.equals("int")){
            VarInt var = new VarInt("return");
            functionParser.variables.put("return", var);
        }
        else if(this.returnType.equals("double")){
            VarDouble var = new VarDouble("return");
            functionParser.variables.put("return", var);
        }
        else if(this.returnType.equals("float")){
            VarFloat var = new VarFloat("return");
            functionParser.variables.put("return", var);
        }
        else if(this.returnType.equals("string")){
            VarString var = new VarString("return");

            functionParser.variables.put("return", var);
        }

        int summonerLine = Parser.currentLine;
        Parser.currentLine = initialLine;
        functionParser.parseLines(lines);
        this.functionReturn = functionParser.variables.get("return");
        Parser.currentLine = summonerLine;
        Parser.findReturn = false;
    }
}
