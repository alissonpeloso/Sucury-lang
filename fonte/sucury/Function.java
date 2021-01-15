package fonte.sucury;
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
        int summonerLine = Parser.currentLine;
        Parser.currentLine = initialLine;
        functionParser.parseLines(lines);
        this.functionReturn = functionParser.variables.get("return");
        Parser.currentLine = summonerLine;
    }
}
