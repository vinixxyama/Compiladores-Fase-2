

package AST;
import Lexer.*;
import java.io.*;

public class CompilerError {
    
    private Lexer lexer;
    String nameFile;
    
    public CompilerError(String nameFile){
        this.nameFile = nameFile;
    }
    
    public CompilerError(){
        
    }
    
    public void setLexer( Lexer lexer ) {
        this.lexer = lexer;
    }
    
    public void signal( String strMessage ) {
        System.out.println("\nError in file: " + nameFile);
        System.out.println(" \nError at line " + lexer.getLineNumber() + ": ");
        System.out.println(lexer.getCurrentLine());
        System.out.println(strMessage );
        throw new RuntimeException(strMessage);
    }
    
    
}

