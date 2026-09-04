/**
 * COSC 4400 - Project #
 * Scanner accepts input from user
 * @authors [Aleksandro Zhaka, Christian Guzman ]
 * Instructor [Dennis Brylow]
 * TA-BOT:MAILTO [aleksandro.zhaka@marquette.edu, christian.guzmanrivas@marquette.edu]
 **/
import java.util.Scanner;
public class Main{
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a string: ");
        while (scanner.hasNext()) {
            String text = scanner.next();
                char ch = text.charAt(0);
                if (Character.isLetter(ch)) {
                    ID(text);
                } //else if (Character.isDigit(ch)) {
                    //Num(ch);} 
                else {
                    System.out.print("Error");
                }
            
        }
        System.out.println();
    }

    String[] word = new String[3];
    public static boolean ID(String text) {
            for (int i = 0; i < text.length(); i++) {
                char ch = text.charAt(i);
                if (Character.isLetter(ch) || Character.isDigit(ch)) {
                    System.out.print(ch);
                }
        }
        System.out.println();
        return true; 
    }
/*
    public static boolean Num(char ch) {
        if (Character.isDigit(ch)) {
                System.out.print(ch);
                while ( (ch = text.charAt(i)) != '\0' && (Character.isLetter(ch) || Character.isDigit(ch))) {
                    word[i] = ch;
                }else{
                    break;
                }
            }
                
    }
  */  
        
}
