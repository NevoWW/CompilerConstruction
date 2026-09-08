/**
 * COSC 4400 - Project #
 * Scanner accepts input from user
 * @authors [Aleksandro Zhaka, Christian Guzman ]
 * Instructor [Dennis Brylow]
 * TA-BOT:MAILTO [aleksandro.zhaka@marquette.edu, christian.guzmanrivas@marquette.edu]
 */
//import java.util.Scanner;

enum TokenType {

        // Logical binary operators
        AND,                // &&
        OR,                 // ||

        // Bitwise operators
        BITWISE_AND,        // &
        BITWISE_OR,         // |
        XOR,                // ^
        BITWISE_NOT,        // ~

        // Mathematical operators
        PLUS,               // +
        MINUS,              // -
        MULTIPLY,           // *
        DIVIDE,             // /

        // Comparison operators
        LESS_THAN,          // <
        GREATER_THAN,       // >
        EQUAL,              // ==
        NOT_EQUAL,          // !=

        // Unary operators
        NOT,                // !

        // Punctuation
        LPAREN,             // (
        RPAREN,             // )
        LBRACKET,           // [
        RBRACKET,           // ]
        LBRACE,             // {
        RBRACE,             // }
        COMMA,              // ,
        DOT,                // .

        // Other tokens
        IDENTIFIER,
        DECIMAL_INTEGER,
        OCTAL_INTEGER,
        HEX_INTEGER,
        STRING,

        // Xinu functions
        XINU_PRINT,
        XINU_PRINTLN,
        XINU_PRINTINT,
        XINU_READINT,

        // Reserved word
        RESERVED_WORD,

        // End of file
        EOF,

        // Error
        ERROR
    }

public class Scanner{


    public static void main(String[] args){
        String word;
        java.util.Scanner reader = new java.util.Scanner(System.in);

        while(reader.hasNext()){
            word = reader.next();

            while (word.length() > 0) {
                char firstChar = word.charAt(0);
                if(Character.isLetter(firstChar)){
                    word = checkID(word);
                }else if(Character.isDigit(firstChar)){
                    word = checkNumber(word);
                }else{
                    word = checkSpecial(word);
                }    
            }                       
        }
        System.out.println("EOF");
    }

public static String checkNumber(String word){
    String tempWord = "";
    for(int i = 0; i < word.length();i++){

        char c = word.charAt(i);

        if(Character.isDigit(c)){
            tempWord = tempWord+c;

        }else if(Character.isLetter(c)){
            if(tempWord.equals("0") && (c == 'x' || c == 'X')){
                tempWord = tempWord+c;
                if (i+1 < word.length()){
                    char nextChar = word.charAt(i+1);
                    if((nextChar >= '0' && nextChar <= '9') ||
                        (nextChar >= 'a' && nextChar <= 'f') ||
                        (nextChar >= 'A' && nextChar <= 'F')) {

                        i++;
                    } else {
                        System.out.println("ERROR");
                        tempWord = "";
                        return "";
                        }
                }
            } else {
                System.out.println("ERROR");
                tempWord = "";
                return "";
            }
            
        }else{
            // Add tempword to array before entering the method
            System.out.println(tempWord);
            return word.substring(i);
        }
    }
    System.out.println(tempWord);
    return "";
}



public static String checkID(String word){
    String tempWord = "";
    for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                if (Character.isLetterOrDigit(c) || c == '_') {
                    tempWord = tempWord + c;
                }else {
                    System.out.println(tempWord);
                    return word.substring(i);
                }
        }
        System.out.println(tempWord);
        return "";
}


public static String checkSpecial(String word){
    for (int i = 0; i < word.length(); i++) {

            char c = word.charAt(i);

            if (c == '+') {

                System.out.println("PLUS");

            } else if (c == '-') {

                System.out.println("MINUS");

            } else if (c == '*') {

                System.out.println("MULTIPLY");

            } else if (c == '/') {

                System.out.println("DIVIDE");

            } else if (c == '&') {

                if (i + 1 < word.length() && word.charAt(i + 1) == '&') {
                    System.out.println("AND");
                    i++;
                } else {
                    System.out.println("BITWISE_AND");
                }

            } else if (c == '|') {

                if (i + 1 < word.length() && word.charAt(i + 1) == '|') {
                    System.out.println("OR");
                    i++;
                } else {
                    System.out.println("BITWISE_OR");
                }

            } else if (c == '^') {

                System.out.println("XOR");

            } else if (c == '~') {

                System.out.println("BITWISE_NOT");

            } else if (c == '<') {

                System.out.println("LESS_THAN");

            } else if (c == '>') {

                System.out.println("GREATER_THAN");

            } else if (c == '=') {

                if (i + 1 < word.length() && word.charAt(i + 1) == '=') {
                    System.out.println("EQUAL");
                    i++;
                } else {
                    System.out.println("INVALID CHARACTER");
                }

            } else if (c == '!') {

                if (i + 1 < word.length() && word.charAt(i + 1) == '=') {
                    System.out.println("NOT_EQUAL");
                    i++;
                } else {
                    System.out.println("NOT");
                }

            } else if (c == '(') {

                System.out.println("LPAREN");

            } else if (c == ')') {

                System.out.println("RPAREN");

            } else if (c == '[') {

                System.out.println("LBRACKET");

            } else if (c == ']') {

                System.out.println("RBRACKET");

            } else if (c == '{') {

                System.out.println("LBRACE");

            } else if (c == '}') {

                System.out.println("RBRACE");

            } else if (c == ',') {

                System.out.println("COMMA");

            } else if (c == '.') {

                System.out.println("DOT");

            }
        }

        return word.substring(1);
    }
}
