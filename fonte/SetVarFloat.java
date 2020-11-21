public class SetVarFloat extends VarFloat{ 
    public String div="";
    public String var_name; 
    public String[] splitar; 
    public String[] separar;
    public float num_convertido;
    public float num_original;

    public void setFloat(String a[]){
        if(a.length>=2){//verefica se tem sinal de =
            if(a[1].indexOf("=")>=0 || a[1].indexOf("=")<=0){ 
            
                for(int p=1;a.length-1>=p;p++){  //coloca td expressao em uma unica string
                    div =div.concat(a[p]);
                    }
                
                splitar=div.split("="); //separa o nome da variavel da equacao
                var_name=splitar[0];
                
                if(splitar[1].indexOf("+")>=0){
                    separar=splitar[1].split("\\+");
                    num_original=Float.parseFloat(separar[0]); 
                }
                else if(splitar[1].indexOf("-")>=0){
                    separar=splitar[1].split("\\-");
                    num_original=Float.parseFloat(separar[0]); 
                }
                else if(splitar[1].indexOf("*")>=0){
                    separar=splitar[1].split("\\*");
                    num_original=Float.parseFloat(separar[0]); 
                }
                else if(splitar[1].indexOf("/")>=0){
                    separar=splitar[1].split("\\/");
                    num_original=Float.parseFloat(separar[0]); 
                }
                else{
                    separar=splitar[1].split("(?!^)");
                    num_original=Float.parseFloat(splitar[1]); 
                }
                
                
                
                if(separar.length==2){
                    if(splitar[1].indexOf("+")>=0){
                        num_convertido=Float.parseFloat(separar[1]);
                        num_original+=num_convertido;
                    }
                    else if(splitar[1].indexOf("-")>=0){
                        num_convertido=Float.parseFloat(separar[1]);
                        num_original-=num_convertido;
                    }
                    else if(splitar[1].indexOf("*")>=0){
                        num_convertido=Float.parseFloat(separar[1]);
                        num_original*=num_convertido;
                    }
                    else if(splitar[1].indexOf("/")>=0){
                        num_convertido=Float.parseFloat(separar[1]);
                        num_original/=num_convertido;
                    }
                }
                VarFloat1(var_name, num_original);
                
            }
        }else{ //caso o usuario so tenha digitado o nome da variavel
            var_name=a[1];
            VarFloat1(var_name);
            
        }
    }
    

}






