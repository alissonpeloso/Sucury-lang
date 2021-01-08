package fonte.sucury;
import java.util.Map;
import java.util.regex.Pattern;

public class Condition {

    public static boolean isTrue(String condition, Map<String, Variable> variables){
        condition = condition.trim(); //remove espaços do início e do fim
        if(condition.indexOf("&&") != -1){
            int andPosition = condition.indexOf("&&");
            String rightCondition = condition.substring(0, andPosition);
            String leftCondition = condition.substring(andPosition+2, condition.length());
            boolean rightIsTrue = isTrue(rightCondition, variables);
            boolean leftIsTrue = isTrue(leftCondition, variables);

            if(rightIsTrue && leftIsTrue){
                return true;
            }
            return false;
        }
        if(condition.indexOf("||") != -1){
            int orPosition = condition.indexOf("||");
            String rightCondition = condition.substring(0, orPosition);
            String leftCondition = condition.substring(orPosition+2, condition.length());
            boolean rightIsTrue = isTrue(rightCondition, variables);
            boolean leftIsTrue = isTrue(leftCondition, variables);

            if(rightIsTrue || leftIsTrue){
                return true;
            }
            return false;
        }
        boolean result = false;
        boolean isDenied = false;
        if(Pattern.compile("^!").matcher(condition).find()){
            isDenied = true;
            condition = condition.replaceFirst("^!", "");
        }

        if(condition.indexOf("==") != -1 ){
            String [] expressions = condition.split("==");
            String [] expressionLeft = Util.lineInWordArray(expressions[0]);
            String [] expressionRight = Util.lineInWordArray(expressions[1]);
            String left = "", right = "";

            for(int i = 0; i < expressionLeft.length; i++){
                left+=expressionLeft[i];
            }
            for(int i = 0; i < expressionRight.length; i++){
                right+=expressionRight[i];
            }
            double rightValue = (double) Operation.chooseOperation(right, "double", variables);
            double leftValue = (double) Operation.chooseOperation(left, "double", variables);

            if(leftValue == rightValue){
                result = true;
            }
        }
        else if(condition.indexOf("!=") != -1 ){
            String [] expressions = condition.split("!=");
            String [] expressionLeft = Util.lineInWordArray(expressions[0]);
            String [] expressionRight = Util.lineInWordArray(expressions[1]);
            String left = "", right = "";

            for(int i = 0; i < expressionLeft.length; i++){
                left+=expressionLeft[i];
            }
            for(int i = 0; i < expressionRight.length; i++){
                right+=expressionRight[i];
            }
            double rightValue = (double) Operation.chooseOperation(right, "double", variables);
            double leftValue = (double) Operation.chooseOperation(left, "double", variables);

            if(leftValue != rightValue){
                result = true;
            }
        }
        else if(condition.indexOf("<=") != -1 ){
            String [] expressions = condition.split("<=");
            String [] expressionLeft = Util.lineInWordArray(expressions[0]);
            String [] expressionRight = Util.lineInWordArray(expressions[1]);
            String left = "", right = "";

            for(int i = 0; i < expressionLeft.length; i++){
                left+=expressionLeft[i];
            }
            for(int i = 0; i < expressionRight.length; i++){
                right+=expressionRight[i];
            }
            double rightValue = (double) Operation.chooseOperation(right, "double", variables);
            double leftValue = (double) Operation.chooseOperation(left, "double", variables);

            if(leftValue <= rightValue){
                result = true;
            }
        }
        else if(condition.indexOf(">=") != -1 ){
            String [] expressions = condition.split(">=");
            String [] expressionLeft = Util.lineInWordArray(expressions[0]);
            String [] expressionRight = Util.lineInWordArray(expressions[1]);
            String left = "", right = "";

            for(int i = 0; i < expressionLeft.length; i++){
                left+=expressionLeft[i];
            }
            for(int i = 0; i < expressionRight.length; i++){
                right+=expressionRight[i];
            }
            double rightValue = (double) Operation.chooseOperation(right, "double", variables);
            double leftValue = (double) Operation.chooseOperation(left, "double", variables);

            if(leftValue >= rightValue){
                result = true;
            }
        }
        else if(condition.indexOf(">") != -1 ){
            String [] expressions = condition.split(">");
            String [] expressionLeft = Util.lineInWordArray(expressions[0]);
            String [] expressionRight = Util.lineInWordArray(expressions[1]);
            String left = "", right = "";

            for(int i = 0; i < expressionLeft.length; i++){
                left+=expressionLeft[i];
            }
            for(int i = 0; i < expressionRight.length; i++){
                right+=expressionRight[i];
            }
            double rightValue = (double) Operation.chooseOperation(right, "double", variables);
            double leftValue = (double) Operation.chooseOperation(left, "double", variables);

            if(leftValue > rightValue){
                result = true;
            }
        }
        else if(condition.indexOf("<") != -1 ){
            String [] expressions = condition.split("<");
            String [] expressionLeft = Util.lineInWordArray(expressions[0]);
            String [] expressionRight = Util.lineInWordArray(expressions[1]);
            String left = "", right = "";

            for(int i = 0; i < expressionLeft.length; i++){
                left+=expressionLeft[i];
            }
            for(int i = 0; i < expressionRight.length; i++){
                right+=expressionRight[i];
            }
            double rightValue = (double) Operation.chooseOperation(right, "double", variables);
            double leftValue = (double) Operation.chooseOperation(left, "double", variables);

            if(leftValue < rightValue){
                result = true;
            }
        }
        if(isDenied){
            return !result;
        }
        return result;
    }
}
