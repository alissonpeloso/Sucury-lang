package fonte.sucury;
import java.util.Map;
import java.util.regex.Pattern;

public class Operation {
    public static String[] treatment(String content){
        String[] aux = content.split("");
        String[] newContent = new String[0];
        String concat = "";
        
        for(int i = 0; i < aux.length; i++){
            if(aux[i].equals("+") || aux[i].equals("-") || aux[i].equals("*") || aux[i].equals("/") || aux[i].equals("%") || aux[i].equals("(") || aux[i].equals(")")){
                if(!concat.equals("")){
                    newContent = Util.appendArray(newContent.length, newContent, concat);
                    concat = "";
                }
                newContent = Util.appendArray(newContent.length, newContent, aux[i]);
            }else{
                concat = concat.concat(aux[i]);
            }
        }
        newContent = Util.appendArray(newContent.length, newContent, concat);
        if(newContent[0].equals("+") || newContent[0].equals("-")){
            newContent[1] = newContent[0].concat(newContent[1]);
            newContent = Util.removeArray(newContent.length, newContent, 0);
        }
        for(int i = 0 ; i < newContent.length; i++){
            if(newContent[i].equals("(")){
                if(newContent[i+1].equals("+") || newContent[i+1].equals("-")){
                    newContent[i+2] = newContent[i+1].concat(newContent[i+2]);
                    newContent = Util.removeArray(newContent.length, newContent, i+1);
                }
            }
        }
        return newContent;
    }

    public static Object chooseOperation(String content, String type, Map<String, Variable> variables){
        String [] values = treatment(content);
        Object result = new Object();
        
        if(content.indexOf("(") != -1){
            for(int i = values.length-1; i >= 0 ; i--){
                if(values[i].equals("(")){
                    int j;
                    String part = "";
                    for(j = i+1; !values[j].equals(")"); j++){
                        part = part.concat(values[j]);
                    }
                    Object parenthResult = chooseOperation(part, type, variables);
                    values[i] = parenthResult.toString();
                    for(j = i+1; !values[j].equals(")"); j++){
                        values = Util.removeArray(values.length, values, j);
                        j--;
                    }                 
                    values = Util.removeArray(values.length, values, j);
                }
            }
        }
        
        if(content.indexOf("+") != -1 || content.indexOf("-") != -1 || content.indexOf("*") != -1 || content.indexOf("/") != -1 || content.indexOf("%") != -1){
            for(int i = 0; i < values.length; i++){
                if(values[i].equals("*")){
                    values[i+1] = Operation.variableReplacement(values[i+1], variables);
                    values[i-1] = Operation.variableReplacement(values[i-1],variables);
                    result = mult(values, type, i-1, i+1);
                    values[i-1] = result.toString();
                    values = Util.removeArray(values.length, values, i);
                    values = Util.removeArray(values.length, values, i);
                    i--;
                }
            }
            for(int i = 0; i < values.length; i++){
                if(values[i].equals("/")){
                    values[i+1] = Operation.variableReplacement(values[i+1], variables);
                    values[i-1] = Operation.variableReplacement(values[i-1],variables);
                    result = div(values, type, i-1, i+1);
                    values[i-1] = result.toString();
                    values = Util.removeArray(values.length, values, i);
                    values = Util.removeArray(values.length, values, i);
                    i--;
                }
            }
            for(int i = 0; i < values.length; i++){
                if(values[i].equals("%")){
                    values[i+1] = Operation.variableReplacement(values[i+1], variables);
                    values[i-1] = Operation.variableReplacement(values[i-1],variables);
                    result = mod(values, type, i-1, i+1);
                    values[i-1] = result.toString();
                    values = Util.removeArray(values.length, values, i);
                    values = Util.removeArray(values.length, values, i);
                    i--;
                }
            }
            for(int i = 0; i < values.length; i++){
                if(values[i].equals("+")){
                    values[i+1] = Operation.variableReplacement(values[i+1], variables);
                    values[i-1] = Operation.variableReplacement(values[i-1],variables);
                    result = sum(values, type, i-1, i+1);
                    values[i-1] = result.toString();
                    values = Util.removeArray(values.length, values, i);
                    values = Util.removeArray(values.length, values, i);
                    i--;
                    
                }
            }
            for(int i = 0; i < values.length; i++){
                if(values[i].equals("-")){
                    values[i+1] = Operation.variableReplacement(values[i+1], variables);
                    values[i-1] = Operation.variableReplacement(values[i-1],variables);
                    result = sub(values, type, i-1, i+1);
                    values[i-1] = result.toString();
                    values = Util.removeArray(values.length, values, i);
                    values = Util.removeArray(values.length, values, i);
                    i--;
                }
            }
        }
        values[0] = Operation.variableReplacement(values[0], variables);
        if(type.equals("float")){
            result = Float.parseFloat(values[0]);
        }
        if(type.equals("int")){
            result = Integer.parseInt(values[0]);
        }
        if(type.equals("double")){
            result = Double.parseDouble(values[0]);
        }
        return result;
    }

    public static Object sum(String[] values, String type, int pos1, int pos2){
        if(type.equals("float")){
            float result = Float.parseFloat(values[pos1]) + Float.parseFloat(values[pos2]);
            return result;
        }
        if(type.equals("int")){
            int result = Integer.parseInt(values[pos1]) + Integer.parseInt(values[pos2]);
            return result;
        }
        if(type.equals("double")){
            double result = Double.parseDouble(values[pos1]) + Double.parseDouble(values[pos2]);
            return result;
        }
        return 0;
    }

    public static Object sub(String[] values, String type, int pos1, int pos2){
        if(type.equals("float")){
            float result = Float.parseFloat(values[pos1]) - Float.parseFloat(values[pos2]);
            return result;
        }
        if(type.equals("int")){
            int result = Integer.parseInt(values[pos1]) - Integer.parseInt(values[pos2]);
            return result;
        }
        if(type.equals("double")){
            double result = Double.parseDouble(values[pos1]) - Double.parseDouble(values[pos2]);
            return result;
        }
        return 0;
    }

    public static Object mult(String[] values, String type, int pos1, int pos2){
        if(type.equals("float")){
            float result = Float.parseFloat(values[pos1]) * Float.parseFloat(values[pos2]);
            return result;
        }
        if(type.equals("int")){
            int result = Integer.parseInt(values[pos1]) * Integer.parseInt(values[pos2]);
            return result;
        }
        if(type.equals("double")){
            double result = Double.parseDouble(values[pos1]) * Double.parseDouble(values[pos2]);
            return result;
        }
        return 0;
    }

    public static Object div(String[] values, String type, int pos1, int pos2){
        if(type.equals("float")){
            float result = Float.parseFloat(values[pos1]) / Float.parseFloat(values[pos2]);
            return result;
        }
        if(type.equals("int")){
            int result = Integer.parseInt(values[pos1]) / Integer.parseInt(values[pos2]);
            return result;
        }
        if(type.equals("double")){
            double result = Double.parseDouble(values[pos1]) / Double.parseDouble(values[pos2]);
            return result;
        }
        return 0;
    }

    public static Object mod(String[] values, String type, int pos1, int pos2){
        if(type.equals("float")){
            float result = Float.parseFloat(values[pos1]) % Float.parseFloat(values[pos2]);
            return result;
        }
        if(type.equals("int")){
            int result = Integer.parseInt(values[pos1]) % Integer.parseInt(values[pos2]);
            return result;
        }
        if(type.equals("double")){
            double result = Double.parseDouble(values[pos1]) % Double.parseDouble(values[pos2]);
            return result;
        }
        return 0;
    }
    public static String concatString(String inQuotes){
        String concatenada="";
        String splitQuotes[] = inQuotes.split("'");
            for(int i=1;i<=splitQuotes.length-1;i+=2){
                concatenada= concatenada.concat(splitQuotes[i]);
                concatenada= concatenada.concat(" ");
        }
        return concatenada;
    }
    public static String concatAfterDeclaration(String line){
        String STRcomplete;
        int first = line.indexOf("'");
        int second = line.lastIndexOf("'");
            
        String inQuotes = line.substring(first, second+1);
            
        if(inQuotes.indexOf("+") != -1){
            STRcomplete= Operation.concatString(inQuotes);   
            return STRcomplete;
        }
        else{
            String splitQuotes[] = inQuotes.split("'");    
            return splitQuotes[1];
        }
    }
    private static String variableReplacement(String operand, Map<String, Variable> variables){
        if (variables.containsKey(operand)){
            return variables.get(operand).getValue().toString();
        } else if(!Pattern.compile("^[\\-]{0,1}[0-9]*[.]{0,1}[0-9]+$").matcher(operand).find()){
            System.out.println("Variável não encontrada "+operand);
            System.exit(0);
        }
        return operand;
    }
}
