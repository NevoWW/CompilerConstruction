/**
 * COSC 4400 - Project #
 * Scanner accepts input from user
 * @authors [Aleksandro Zhaka, Christian Guzman ]
 * Instructor [Dennis Brylow]
 * TA-BOT:MAILTO [aleksandro.zhaka@marquette.edu, christian.guzmanrivas@marquette.edu]
 */
import java.util.Scanner;


public class scanner{
    public static void main(String[] args){
        String word;
        Scanner reader = new Scanner(System.in);

        while(reader.hasNext()){

            word = reader.next();
            char firstChar = word.charAt(0);

            if(Character.isDigit(firstChar)){
                checkNumber(word);
            }else if(Character.isLetter(firstChar)){
                checkID(word);
            }else if(!Character.isLetterOrDigit(firstChar) && !Character.isWhitespace(firstChar)){
                checkSpecial(firstChar);
            }else{
                System.out.println("Error");
            }                           
        }
        System.out.println("EOF");
    }

public static void checkNumber(String word){
    String tempWord = "";
    for(int i = 0; i < word.length();i++){
        char c = word.charAt(i);
        if(Character.isDigit(c)){
            tempWord = tempWord+c;
        }else if(Character.isLetter(c)){
            System.out.println("Invalid number");
            tempWord = "";
            return;
        }else{
            // Add tempword to array before entering the method
            System.out.println(tempWord);
            checkSpecial(c);
            if(i + 1 < word.length()){
                String sendingWord = word.substring(i + 1);
                checkID(sendingWord);
            }

            return;
        }
    }
    System.out.println(tempWord);
}
public static void checkID(String word){
    for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                if (Character.isLetter(c) || Character.isDigit(c)) {
                    System.out.print(c);
                }
        }
        System.out.println();
}
public static void checkSpecial(char c){
    if (c == '+') {
        System.out.println("PLUS");
    } else if (c == '-') {
        System.out.println("MINUS");
    } else if (c == '*') {
        System.out.println("MULTIPLY");
    } else if (c == '/') {
        System.out.println("DIVIDE");
    } else {
        System.out.println("INVALID CHARACTER");
    }
    return;
    }
}
