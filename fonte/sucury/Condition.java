package fonte.sucury;
import java.util.Map;

public class Condition {

    public static boolean isTrue(String condition, Map<String, Variable> variables){
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
                return true;
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
                return true;
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
                return true;
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
                return true;
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
                return true;
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
                return true;
            }
        }
        
        return false;
    }
}
