package sucury;

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

    public static Object chooseOperation(String content, String type){
        String [] values = treatment(content);
        Object result = new Object();

        if(content.indexOf("(") != -1){
            for(int i = 0; i < values.length; i++){
                if(values[i].equals("(")){
                    int j;
                    String part = "";
                    for(j = i+1; !values[j].equals(")"); j++){
                        part = part.concat(values[j]);
                    }
                    Object parenthResult = chooseOperation(part, type);
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
                    result = mult(values, type, i-1, i+1);
                    values[i-1] = result.toString();
                    values = Util.removeArray(values.length, values, i);
                    values = Util.removeArray(values.length, values, i);
                    i--;
                }
            }
            for(int i = 0; i < values.length; i++){
                if(values[i].equals("/")){
                    result = div(values, type, i-1, i+1);
                    values[i-1] = result.toString();
                    values = Util.removeArray(values.length, values, i);
                    values = Util.removeArray(values.length, values, i);
                    i--;
                }
            }
            for(int i = 0; i < values.length; i++){
                if(values[i].equals("%")){
                    result = mod(values, type, i-1, i+1);
                    values[i-1] = result.toString();
                    values = Util.removeArray(values.length, values, i);
                    values = Util.removeArray(values.length, values, i);
                    i--;
                }
            }
            for(int i = 0; i < values.length; i++){
                if(values[i].equals("+")){
                    result = sum(values, type, i-1, i+1);
                    values[i-1] = result.toString();
                    values = Util.removeArray(values.length, values, i);
                    values = Util.removeArray(values.length, values, i);
                    i--;
                }
            }
            for(int i = 0; i < values.length; i++){
                if(values[i].equals("-")){
                    result = sub(values, type, i-1, i+1);
                    values[i-1] = result.toString();
                    values = Util.removeArray(values.length, values, i);
                    values = Util.removeArray(values.length, values, i);
                    i--;
                }
            }
        }
    
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
}