public class SetVarInt {
    
    public String div="";
    public String var_name; 
    public String[] splitar; 
    public String[] separar;
    public int num_convertido;
    
    public void setInt(String a[]){
        if(a.length>=2){//verefica se tem sinal de =
            if(a[1].indexOf("=")>=0 || a[1].indexOf("=")<=0){ 
            
                for(int p=1;a.length-1>=p;p++){  //coloca td expressao em uma unica string
                    div =div.concat(a[p]);
                    }
                
                splitar=div.split("="); //separa o nome da variavel da equacao
                var_name=splitar[0];
                separar=splitar[1].split("(?!^)"); //separa tds elementos da equacao
                int num_original=Integer.parseInt(separar[0]); 
                
                if(separar.length==3){
                    if(separar[1].indexOf("+")>=0){
                        num_convertido=Integer.parseInt(separar[2]);
                        num_original+=num_convertido;
                    }
                    else if(separar[1].indexOf("-")>=0){
                        num_convertido=Integer.parseInt(separar[2]);
                        num_original-=num_convertido;
                    }
                    else if(separar[1].indexOf("*")>=0){
                        num_convertido=Integer.parseInt(separar[2]);
                        num_original*=num_convertido;
                    }
                    else if(separar[1].indexOf("/")>=0){
                        num_convertido=Integer.parseInt(separar[2]);
                        num_original/=num_convertido;
                    }
                }
                System.out.println(var_name+" = "+num_original);
                
            }
        }else{ //caso o usuario so tenha digitado o nome da variavel
            var_name=a[1];
            System.out.println(var_name);
        }
    }
    
}