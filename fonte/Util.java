import java.util.*;

public class Util {
    public static String[] appendArray (int size, String[] Array, String newElement) {
        String[] newArray = Arrays.copyOf(Array, size+1);
        newArray[newArray.length-1] = newElement;
        return newArray;
    }

    public static String[] lineInWordArray (String line){
        String words[] = line.split(" ");
        String withoutSpaces[] = new String[0];
        for(int i = 0; i < words.length; i++){
            if(!" ".equals(words[i])){
                withoutSpaces = appendArray(withoutSpaces.length, withoutSpaces, words[i]);
            }
        }
        return withoutSpaces;
    }
}
