/*  Vinicius Yamamoto   RA:490105
    Daniel Valim        RA:511315
*/

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
        System.out.print("\nError in file: " + nameFile + ", ");
        System.out.print(lexer.getLineNumber() +", ");
        System.out.println(strMessage );
        throw new RuntimeException(strMessage);
    }
    
    
}

