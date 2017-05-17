/* =====================================================================
 * Universidade Federal de São Carlos - Campus Sorocaba
 * Compiladores - 2016/1
 * Orientação: Prof.ª Dr.ª Tiemi C. Sakata
 * 
 * Analise sintatica para a linguagem da seção 3
 *
 * Trabalho - Fase 2
 *
 * Março de 2016
 * 
 * Gabriel Stankevix Soares		|	511340
 * Luis Augusto França Barbosa	|	511374
===================================================================== */

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

