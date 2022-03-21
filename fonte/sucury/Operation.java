package fonte.sucury;

// Resolve expressões matemáticas

// Alisson Peloso     <alisson.luan2000@gmail.com>
// Eduardo Lazaretti  <eduardolazaretti3@gmail.com>
// Guilherme Graeff   <guilherme.rafael.graeff@gmail.com>
// Stefani Meneghetti <meneghettistefani@gmail.com> 

import java.util.Map;
import java.util.regex.Pattern;

public class Operation {
    public static String[] treatment(String content){
        String[] aux = content.split("");
        String[] newContent = new String[0];
        String concat = "";
        
        for(int i = 0; i < aux.length; i++){
            if(aux[i].equals("+") || aux[i].equals("-") || aux[i].equals("*") || aux[i].equals("/") || aux[i].equals("%") || aux[i].equals("(") || aux[i].equals(")") || aux[i].equals("^")){
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

    public static Object chooseOperation(String content, String type, Map<String, Variable> variables) throws SucuryException {
        String [] values = treatment(content);
        Object result = new Object();

        if(type.equals("string")){
            return stringOperation(content, variables);
        }
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
        
        if(content.indexOf("+") != -1 || content.indexOf("-") != -1 || content.indexOf("*") != -1 || content.indexOf("/") != -1 || content.indexOf("%") != -1 || content.indexOf("^") != -1){
            for(int i = 0; i < values.length; i++){
                if(values[i].equals("*")){
                    values[i+1] = Operation.variableReplacement(values[i+1], variables, type);
                    values[i-1] = Operation.variableReplacement(values[i-1],variables, type);
                    result = mult(values, type, i-1, i+1);
                    values[i-1] = result.toString();
                    values = Util.removeArray(values.length, values, i);
                    values = Util.removeArray(values.length, values, i);
                    i--;
                }
            }
            for(int i = 0; i < values.length; i++){
                if(values[i].equals("/")){
                    values[i+1] = Operation.variableReplacement(values[i+1], variables, type);
                    values[i-1] = Operation.variableReplacement(values[i-1],variables, type);
                    result = div(values, type, i-1, i+1);
                    values[i-1] = result.toString();
                    values = Util.removeArray(values.length, values, i);
                    values = Util.removeArray(values.length, values, i);
                    i--;
                }
            }
            for(int i = 0; i < values.length; i++){
                if(values[i].equals("%")){
                    values[i+1] = Operation.variableReplacement(values[i+1], variables, type);
                    values[i-1] = Operation.variableReplacement(values[i-1],variables, type);
                    result = mod(values, type, i-1, i+1);
                    values[i-1] = result.toString();
                    values = Util.removeArray(values.length, values, i);
                    values = Util.removeArray(values.length, values, i);
                    i--;
                }
            }
            for(int i = 0; i < values.length; i++){
                if(values[i].equals("+")){
                    values[i+1] = Operation.variableReplacement(values[i+1], variables, type);
                    values[i-1] = Operation.variableReplacement(values[i-1],variables, type);
                    result = sum(values, type, i-1, i+1);
                    values[i-1] = result.toString();
                    values = Util.removeArray(values.length, values, i);
                    values = Util.removeArray(values.length, values, i);
                    i--;
                    
                }
            }
            for(int i = 0; i < values.length; i++){
                if(values[i].equals("-")){
                    values[i+1] = Operation.variableReplacement(values[i+1], variables, type);
                    values[i-1] = Operation.variableReplacement(values[i-1],variables, type);
                    result = sub(values, type, i-1, i+1);
                    values[i-1] = result.toString();
                    values = Util.removeArray(values.length, values, i);
                    values = Util.removeArray(values.length, values, i);
                    i--;
                }
            }
            for(int i = 0; i < values.length; i++){
                if(values[i].equals("^")){
                    values[i+1] = Operation.variableReplacement(values[i+1], variables, type);
                    values[i-1] = Operation.variableReplacement(values[i-1],variables, type);
                    result = pow(values, type, i-1, i+1);
                    values[i-1] = result.toString();
                    values = Util.removeArray(values.length, values, i);
                    values = Util.removeArray(values.length, values, i);
                    i--;
                }
            }
        }
        values[0] = Operation.variableReplacement(values[0], variables, type);
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

    public static Object sum(String[] values, String type, int pos1, int pos2) throws SucuryException{
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
    
    public static Object pow(String[] values, String type, int pos1, int pos2){
        double aux = 1;
        for(int i = 0; i < Integer.parseInt(values[pos2]); i++){
            aux = aux * Double.parseDouble(values[pos1]);
        }
        if(type.equals("float")){
            float result = (float) aux;
            return result;
        }
        if(type.equals("int")){
            int result = (int) aux;
            return result; 
        }
        if(type.equals("double")){
            double result = aux;
            return result;
        }
        return 0;
    }

    public static String stringOperation(String expression, Map<String, Variable> variables) throws SucuryException{
        expression = expression.trim();
        String result = "";

        boolean close = true;
        
        for(int i = 0; i < expression.length(); i++){
            if(expression.charAt(i) == '\''){
                close = !close;
            }
            if(expression.charAt(i) == '+' && close){
                String firstOperation = expression.substring(0, i);
                String secondOperation = expression.substring(i+1, expression.length());

                result = stringOperation(firstOperation, variables) + stringOperation(secondOperation, variables);
                return result;
            }
        }
        if(!close){
            SucuryException exception = new SucuryException("Erro de Síntaxe", expression);
            throw exception;
        }
        if (variables.containsKey(expression)){
            return variables.get(expression).getValue().toString();
        }
        if (expression.indexOf("'") == -1){
            SucuryException exception = new SucuryException("Erro de Síntaxe", expression);
            throw exception;
        }
        return expression.replace("'", "");
    }

    private static String variableReplacement(String operand, Map<String, Variable> variables, String type) throws SucuryException {
        if (variables.containsKey(operand)){
            return variables.get(operand).getValue().toString();
        }
        else if(type.equals("int")){
            if(!Pattern.compile("^[\\-]{0,1}[0-9]+$").matcher(operand).find()){
                SucuryException exception = new SucuryException("Valor incompatível com o tipo esperado", operand);
                throw exception;
            }
        }
        else if(type.equals("double") || type.equals("float")){
            if(!Pattern.compile("^[\\-]{0,1}[0-9]*[.]{0,1}[0-9]+$").matcher(operand).find()){
                SucuryException exception = new SucuryException("Valor incompatível com o tipo esperado", operand);
                throw exception;
            }
        }
        return operand;
    }
}
