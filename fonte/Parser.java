import java.util.HashMap;
import java.util.Map;

public class Parser {
    public static int currentLine;
    protected Map<String, Variable> variables;

    Parser(){
        this.variables = new HashMap<>();
        currentLine = 1;
    }
}

