public class Operation {  
    public static Object chooseOperation(String content, String type){
        String[] values;
        if(content.indexOf("+")>=0){
            values=content.split("\\+");
            return Operation.sum(values,type);
        }
        else if(content.indexOf("-")>=0){
            values=content.split("\\-");
            return Operation.sub(values,type);
        }
        else if(content.indexOf("*")>=0){
            values=content.split("\\*");
            return Operation.mult(values,type);
        }
        else if(content.indexOf("/")>=0){
            values=content.split("\\/");
            return Operation.div(values,type);
        }
        else{
            if(type.equals("float")){
                float result = Float.parseFloat(content);
                return result;
            }
            if(type.equals("int")){
                int result = Integer.parseInt(content);
                return result;
            }
            if(type.equals("double")){
                double result = Double.parseDouble(content);
                return result;
            }
        }
        return 0;
    }

    public static Object sum(String[] values, String type){
        if(type.equals("float")){
            float result = Float.parseFloat(values[0]) + Float.parseFloat(values[1]);
            return result;
        }
        if(type.equals("int")){
            int result = Integer.parseInt(values[0]) + Integer.parseInt(values[1]);
            return result;
        }
        if(type.equals("double")){
            double result = Double.parseDouble(values[0]) + Double.parseDouble(values[1]);
            return result;
        }
        return 0;
    }

    public static Object sub(String[] values, String type){
        if(type.equals("float")){
            float result = Float.parseFloat(values[0]) - Float.parseFloat(values[1]);
            return result;
        }
        if(type.equals("int")){
            int result = Integer.parseInt(values[0]) - Integer.parseInt(values[1]);
            return result;
        }
        if(type.equals("double")){
            double result = Double.parseDouble(values[0]) - Double.parseDouble(values[1]);
            return result;
        }
        return 0;
    }

    public static Object mult(String[] values, String type){
        if(type.equals("float")){
            float result = Float.parseFloat(values[0]) * Float.parseFloat(values[1]);
            return result;
        }
        if(type.equals("int")){
            int result = Integer.parseInt(values[0]) * Integer.parseInt(values[1]);
            return result;
        }
        if(type.equals("double")){
            double result = Double.parseDouble(values[0]) * Double.parseDouble(values[1]);
            return result;
        }
        return 0;
    }

    public static Object div(String[] values, String type){
        if(type.equals("float")){
            float result = Float.parseFloat(values[0]) / Float.parseFloat(values[1]);
            return result;
        }
        if(type.equals("int")){
            int result = Integer.parseInt(values[0]) / Integer.parseInt(values[1]);
            return result;
        }
        if(type.equals("double")){
            double result = Double.parseDouble(values[0]) / Double.parseDouble(values[1]);
            return result;
        }
        return 0;
    }
}
