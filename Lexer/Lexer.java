/*  Vinicius Yamamoto RA:490105
  Daniel Valim    RA:511315
*/
  
package Lexer;
import java.util.*;
import AST.*;

public class Lexer {
    
    public Lexer( char []input, CompilerError error ) {
        this.input = input;
          // add an end-of-file label to make it easy to do the lexer
        input[input.length - 1] = '\0';
          // number of the current line
        lineNumber = 1;
        tokenPos = 0;
        this.error = error;
    }

      // contains the keywords
    static public Hashtable<String, Symbol> keywordsTable;
    
     // this code will be executed only once for each program execution
    static{
      keywordsTable = new Hashtable<String, Symbol>();
      keywordsTable.put( "if", Symbol.IF );
      keywordsTable.put( "then", Symbol.THEN );
      keywordsTable.put( "else", Symbol.ELSE );
      keywordsTable.put( "endif", Symbol.ENDIF );
      keywordsTable.put( "read", Symbol.READ );
      keywordsTable.put( "write", Symbol.WRITE );
      keywordsTable.put( "integer", Symbol.INTEGER );
      keywordsTable.put( "boolean", Symbol.BOOLEAN );
      keywordsTable.put( "char", Symbol.CHAR );
      keywordsTable.put( "True", Symbol.TRUE );
      keywordsTable.put( "False", Symbol.FALSE );
      keywordsTable.put( "and", Symbol.AND );
      keywordsTable.put( "or", Symbol.OR );
      keywordsTable.put( "not", Symbol.NOT );
        
      //adicionadas novas palavras chaves 
      keywordsTable.put( "void", Symbol.VOID );
      keywordsTable.put( "program", Symbol.PROGRAM );
      keywordsTable.put( "main", Symbol.MAIN );
      keywordsTable.put( "int", Symbol.INT);
      keywordsTable.put( "while", Symbol.WHILE );
      keywordsTable.put( "break", Symbol.BREAK );
      keywordsTable.put( "print", Symbol.PRINT );
      keywordsTable.put( "readInteger", Symbol.READINTEGER );
      keywordsTable.put( "readDouble", Symbol.READDOUBLE );
      keywordsTable.put( "readChar", Symbol.READCHAR );
      keywordsTable.put( "end", Symbol.END );
      keywordsTable.put( "string", Symbol.STRING );
      keywordsTable.put( "float", Symbol.FLOAT );
      keywordsTable.put("for", Symbol.FOR);
      keywordsTable.put("inrange",Symbol.INRANGE);
    }
     
     
    
    //Nova implementação nextToken
    public void nextToken() {
        char ch;
        
        while ((ch = input[tokenPos]) == ' ' || ch == '\r' || ch == '\t' || ch == '\n'){
          // count the number of lines
          if ( ch == '\n')
            lineNumber++;
          tokenPos++;
        }
        
        //chega no final do arquivo
        if ( ch == '\0') 
          token = Symbol.EOF;
        else
          //ignora comentario //
          if (input[tokenPos] == '#'){
            // comment found
            tokenPos+=2;
            while (input[tokenPos] != '\0'&& input[tokenPos] != '\n'){
              tokenPos++;
            }
            nextToken();
          //ignora comentario / *
          }else if(input[tokenPos] == '/' && input[tokenPos + 1] == '*'){
            // comment found
            tokenPos+=2;
            while(input[tokenPos] != '*' && input[tokenPos] != '/'){//TEORICAMENTE ERA PRA FUNCIONAR!!!!!!!!!!
              tokenPos++;
              if(input[tokenPos] == '\0')
                error.signal("Error: Comment not terminated!");
            }
            tokenPos+=2;
            nextToken();
          }else{
            
            if (Character.isLetter( ch )){ //caso encontre um caracter

              // get an identifier(name) or keyword (palavra reservada)
              StringBuffer ident = new StringBuffer();
              //percorre o token 
              while ( Character.isLetter( input[tokenPos]) || Character.isDigit( input[tokenPos]) || input[tokenPos]== '_'){
                  ident.append(input[tokenPos]);
                  tokenPos++;
              }
              nameVariable = ident.toString();//converte para string e verifica se é palavra reservada
              System.out.print(nameVariable+" ");
              // if identStr is in the list of keywords, it is a keyword !
              Symbol value = keywordsTable.get(nameVariable);
              if ( value == null ){ //caso não esteja na Lista de palavras reservadas
                token = Symbol.IDENT; //REVER o que token recebe
              }else{ 
                token = value;
                if (Character.isDigit(input[tokenPos]))
                  error.signal("Error: Word followed by a number!");
              }
                  
            }else if ( Character.isDigit( ch )){ //caso encontre um digito
                // get a number
                StringBuffer number = new StringBuffer();
                StringBuffer str = new StringBuffer();
                while ( Character.isDigit( input[tokenPos] ) ) {
                    number.append(input[tokenPos]);
                    str.append(input[tokenPos]);
                    tokenPos++;
                }
                token = Symbol.NUMBER;
                try{
                   numberValue = Integer.valueOf(number.toString()).intValue();
                   stringValue = str.toString();
                }catch( NumberFormatException e ){
                   error.signal("Error: Number out of limits!");
                }
                if ( numberValue >= MaxValueInteger )
                   error.signal("Error: Number out of limits!");
                System.out.print(numberValue+" ");
            }else{
                tokenPos++;
                StringBuffer ident = new StringBuffer();
                ident.append(ch);
                nameVariable = ident.toString();
                System.out.print(ch+" ");
                switch ( ch ) {
                    case '+' :
                      token = Symbol.PLUS;
                      charValue = ch;
                      break;
                    case '-' :
                      token = Symbol.MINUS;
                      charValue = ch;
                      break;
                    case '*' :
                      token = Symbol.MULT;
                      charValue = ch;
                      break;
                    case '/' :
                      token = Symbol.DIV;
                      charValue = ch;
                      break;
                    case '%' :
                      token = Symbol.PCT;
                      charValue = ch;
                      break;
                    case '^' :
                      token = Symbol.EXPONENCIAL;
                      charValue = ch;
                      break;
                    case '<' :
                      if ( input[tokenPos] == '=' ) {
                        ident.append(input[tokenPos]);
                        tokenPos++;
                        System.out.print("= ");
                        nameVariable = ident.toString();
                        token = Symbol.LE;
                      }else if(input[tokenPos] == '>' ){
                        ident.append(input[tokenPos]);
                        tokenPos++;
                        System.out.print("> ");
                        nameVariable = ident.toString();
                        token = Symbol.LTGT;
                      }else{
                        if(input[tokenPos+1] == '=')
                          error.signal("Error: Bad Formation!");
                        nameVariable = ident.toString();
                        token = Symbol.LT;
                      }
                      break;
                    case '>' :
                      if ( input[tokenPos] == '=' ) {
                        ident.append(input[tokenPos]);
                        tokenPos++;
                        System.out.print("= ");
                        nameVariable = ident.toString();
                        token = Symbol.GE;
                      }
                      else{
                        if(input[tokenPos+1] == '=')
                          error.signal("Error: Bad Formation!");
                        nameVariable = ident.toString();
                        token = Symbol.GT;
                      }
                      break;
                    case '=' :
                      if ( input[tokenPos] == '=' ) {
                        ident.append(input[tokenPos]);
                        tokenPos++;
                        System.out.print("= ");
                        nameVariable = ident.toString();
                        token = Symbol.EQ;
                      }else{
                        if(input[tokenPos+1] == '=')
                          error.signal("Error: Bad Formation!");
                        nameVariable = ident.toString();
                        token = Symbol.ASSIGN;
                      }
                      break;
                    case '{':
                      token = Symbol.LEFTBRACES;
                      break;
                    case '}':
                      token = Symbol.RIGHTBRACES;
                      break;
                    case '(' :
                      token = Symbol.LEFTPAR;
                      break;
                    case ')' :
                      token = Symbol.RIGHTPAR;
                      break;
                    case ',' :
                      token = Symbol.COMMA;
                      charValue = ch;
                      break;
                    case '.' :
                      token = Symbol.DOT;
                      charValue = ch;
                      break;
                    case ';' :
                      token = Symbol.SEMICOLON;
                      break;
                    case '[' :
                      token = Symbol.LEFTBRACKETS;
                      break;
                    case ']' :
                      token = Symbol.RIGHTBRACKETS;
                      break;
                    case '!' :
                      token = Symbol.EM;
                      charValue = ch;
                      break;
                    case '|' :
                      if(input[tokenPos] == '|'){
                        tokenPos++;
                        System.out.print("| ");
                        token = Symbol.MAGNITUDE;
                      }else{
                        error.signal("Error: Illegal initialization of '||' !");
                      }
                      break;
                    case ':' :
                        token = Symbol.COLON;
                      break;
                    case '\'' :
                        token = Symbol.IBAR;

                      break;
                }
            }
          }
        lastTokenPos = tokenPos - 1;      
    }

    // return the line number of the last token got with getToken()
    public int getLineNumber() {
        return lineNumber;
    }
        
    public String getCurrentLine() {
        int i = lastTokenPos;
        if ( i == 0 ) 
          i = 1; 
        else 
          if ( i >= input.length )
            i = input.length;
            
        StringBuffer line = new StringBuffer();
          // go to the beginning of the line
        while ( i >= 1 && input[i] != '\n' )
          i--;
        if ( input[i] == '\n' )
          i++;
          // go to the end of the line putting it in variable line
        while ( input[i] != '\0' && input[i] != '\n' && input[i] != '\r' ) {
            line.append( input[i] );
            i++;
        }
        return line.toString();
    }

    public String getStringValue() {
       return stringValue;
    }
    
    public int getNumberValue() {
       return numberValue;
    }
    
    public char getCharValue() {
       return charValue;
    }
    
    public String getNameVariable(){
      return nameVariable;
    }

    

    // current token
    public Symbol token;
    private String nameVariable;
    private String stringValue;
    private int numberValue;
    private char charValue;
    
    private int  tokenPos;
    //  input[lastTokenPos] is the last character of the last token
    private int lastTokenPos;
      // program given as input - source code
    private char []input;
    
    // number of current line. Starts with 1
    private int lineNumber;
    
    private CompilerError error;
    private static final int MaxValueInteger = 32768;
}
