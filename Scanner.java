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
                i++;
                String hexWord = checkForHex(word.substring(i));
                return hexWord;

            } else {
                System.out.println("Invalid character in number.");
                tempWord = "";
                return "";
            }
            
        }else{
            // Add tempword to array before entering the method
            System.out.println("INTEGER_LITERAL(" + tempWord + ")");
            return word.substring(i);
        }
    }
    System.out.println("INTEGER_LITERAL(" + tempWord + ")");
    return "";
}



public static String checkID(String word){
    String tempWord = "";
    for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                if (Character.isLetterOrDigit(c) || c == '_') {
                    tempWord = tempWord + c;
                }else {
                    checkForReserveWord(tempWord);
                    System.out.println("ID(" + tempWord + ")");
                    return word.substring(i);
                }
        }
        System.out.println("ID(" + tempWord + ")");
        return "";
}


public static String checkSpecial(String word){
    int i = 0;
    int index = 1;
    

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
                    index++;
                } else {
                    System.out.println("BITWISE_AND");
                }

            } else if (c == '|') {

                if (i + 1 < word.length() && word.charAt(i + 1) == '|') {
                    System.out.println("OR");
                    index++;
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
                    index++;
                } else {
                    System.out.println("ASSIGN");
                }

            } else if (c == '!') {

                if (i + 1 < word.length() && word.charAt(i + 1) == '=') {
                    System.out.println("NOTEQUAL");
                    index++;
                } else {
                    System.out.println("BANG");
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
        

        return word.substring(index);
    }

public static String checkForReserveWord(String word){

    String LocalWord = word;
    switch(LocalWord){

       

    }
    return "Not Here Yet";
}

public static String checkForHex(String word){
    String tempWord = "";
    int i =0;
    for(i = 0; i < word.length();i++){
        char nextChar = word.charAt(i);
        if((nextChar >= '0' && nextChar <= '9') ||
            (nextChar >= 'a' && nextChar <= 'f') ||
            (nextChar >= 'A' && nextChar <= 'F')) {
            
            tempWord = tempWord + nextChar;
        }else if(Character.isLetter(nextChar)){
            
            System.out.println("Invalid character in hex number.");
            tempWord = "";
            return "";
            
        }else{
            System.out.println("HEXADECIMAL_LITERAL(0x" + tempWord + ")");
            return word.substring(i);
        }
    }
    System.out.println("HEXADECIMAL_LITERAL(0x" + tempWord + ")");
    return word.substring(i);
}


}