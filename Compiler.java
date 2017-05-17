/*
Vinicius Yamamoto     RA 490105
Daniel Valim    RA  511315
*/

import AST.*;
import Lexer.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.*;
import java.io.*;


public class Compiler {

    public Program compile( char []p_input, String nameFile) {
        input = p_input;
        //instancia a nova HashTable para variaveis 
        variableTable = new Hashtable<String, Variable>();
        error = new CompilerError(nameFile);
        lexer = new Lexer(input, error);
        error.setLexer(lexer);
        
        lexer.nextToken(); 
        return program();
        }

    //Program ::= ’P’ Name ’:’ Body ’E’
    private Program program() {
      ArrayList<Stmt> st = new ArrayList<Stmt>();
      decl = new ArrayList<Declaration>();
      if(lexer.token == Symbol.PROGRAM){
        System.out.println("OI");
      }
      if(lexer.token == Symbol.END){
        System.out.println("over");
      }
    return new Program(decl, st);
    }

    private Declaration declaration(){
      char var = ' ';
      if(token == 'N' || token == 'S' || token == 'F'){
        tipo = type();
      }
      if(token >= 'a' && token <= 'z'){
        var = name();
      }else if(token == ';'){
        var = ';';
      }else if(token == ','){
        var = ',';
        lexer.nextToken();
      }else{
        
      }
      return new Declaration(tipo, var, 0);
    }

    private Stmt stmt(){
      Stmt st = null;
      char tk = ' ';
      if(token == 'B' || token == 'R' || (token >= 'a' && token <= 'z')){
        tk = 'S';
        st = new Stmt(tk, simplestmt());
      }else if(token == 'I' || token == 'W' || token == 'L'){
        tk = 'C';
        st = new Stmt(tk, compoundstmt()); 
      }
      return st;
    }

    private SimpleStmt simplestmt(){
      char tk;
      SimpleStmt si = null;
      ArrayList<PrintStmt> pt = new ArrayList<PrintStmt>();
      if(token >= 'a' && token <= 'z'){
        si = new SimpleStmt(exprStmt());
        if(token == ';'){
          lexer.nextToken();
        }else{
          
        }
      }else if(token == 'R'){
        tk = 'R';
        lexer.nextToken();
        while(token != ';'){
          if(token == ','){
            lexer.nextToken();
          }
          pt.add(printstm());
        }
        lexer.nextToken();
        si = new SimpleStmt(tk,pt);
      }else if(token == 'B'){
        tk = 'B';
        breakStmt();
        si = new SimpleStmt(tk);
      }
      return si;
    }

    private CompoundStmt compoundstmt(){
      CompoundStmt cmp = null;
      char tk = ' ';
      if(token == 'I' || token == 'L'){
        tk = token;
        cmp = new CompoundStmt(tk, ifstmt());
      }else if(token == 'W'){
        tk = token;
        cmp = new CompoundStmt(tk, whilestmt());
      }
      return cmp;
    }

    private PrintStmt printstm(){
      PrintStmt prt = null;
      Comparison aux = null;
      ArrayList<Expr> aux2 = null;
      Factor aux3 = null;
      ArrayList<Character> aux4 = null;
      char t = ' ';
      char c = ' ';

      aux = comparison();
      aux2 = aux.getExpr();
      for(int i=0;i<aux2.size();i++){
        aux3 = aux2.get(i).getFactor();
        for(int j=0;j<decl.size();j++){
          if(decl.get(j).getName() == aux3.getName()){
            t = decl.get(j).getType();
            c = decl.get(j).getName();
          }
        }
      }
      prt = new PrintStmt(aux, t, c);
      return prt;
    }

    private IfStmt ifstmt(){
      IfStmt se = null;
      IfStmt el = null;
      ArrayList<Stmt> st = new ArrayList<Stmt>();
      Comparison com = null;
      char tk = ' ';
      //se tiver chave aberta do if continua senao da erro de sintaxe
      if(token == 'I'){
        lexer.nextToken();
        com = comparison();
        if(token == '{'){
          tk = 'I';
          lexer.nextToken();
          while(token != '}'){
           st.add(stmt());
          }
          lexer.nextToken();
          if(token == 'L'){
            char tk2 = 'L';
            el = new IfStmt(tk2, stmt());
          }
          se = new IfStmt(tk, com, st, el);
        }else{
          
        }
      }else if(token == 'L'){
        lexer.nextToken();
        if(token == '{'){
          tk = 'L';
          lexer.nextToken();
          while(token != '}'){
           st.add(stmt());
          }
          lexer.nextToken();
          se = new IfStmt(tk, st);
        }
      }
      return se;
    }

    private WhileStmt whilestmt(){
      WhileStmt wh = null;
      Comparison com = null;
      ArrayList<Stmt> st = new ArrayList<Stmt>(); 
      char tk = ' ';
      tk = 'W';
      lexer.nextToken();
      com = comparison();
      if(token == '{'){
        lexer.nextToken();
        if((token >= 'a' && token <= 'z') || token == 'R' || token == 'B' || token == 'I' || token == 'W'){
          while(token != '}'){
            st.add(stmt());
          }
        }
      }else{
        
      }
      if(token != '}'){
        
      }
      lexer.nextToken();
      wh = new WhileStmt(tk, st, com);
      return wh;
    }

    private void breakStmt(){
      lexer.nextToken();
      if(token != ';'){
        
      }else{
        lexer.nextToken();
      }
    }

    private ExprStmt exprStmt(){
      Comparison aux = null;
      ArrayList<Expr> aux2 = null;
      Factor aux3 = null;
      ArrayList<Character> aux4 = null;
      ExprStmt novo;
      //pega o name
      int len=0, flag = 0;
      char ctr1 = name();
      char equal =' ';
      //ve se o proximo eh = se sim consome
      if(token == '='){
        equal = token;
        lexer.nextToken();
      }else{
        
      }
      aux = comparison();
      aux2 = aux.getExpr();
      for(int i=0;i<aux2.size();i++){
        aux3 = aux2.get(i).getFactor();
        if(aux3.getStr() != null)
          len = aux3.getStr().size();
        if(len >= 1){
          for(int j=0;j<decl.size();j++){
            if(decl.get(j).getType() == 'S'){
              if(decl.get(j).getName() == ctr1){
                decl.get(j).setTamanho(len);
                flag = 1;
              }
            }
          }
        }
      }
      
      //chama comparison
      novo = new ExprStmt(ctr1, equal, aux, flag);
      return novo;
    }

    //COMPARISON
    private Comparison comparison(){
      Comparison co = null;
      co = new Comparison(expr());
      return co;
    }

    /*Expr ::= Term {(’+’ | ’-’) Term}
    Term ::= Factor {(’*’ | ’/’) Factor}*/
    private ArrayList<Expr> expr(){
      Expr ex = null;
      char op;
      int flag = 0;
      ArrayList<Expr> listexpr = new ArrayList<Expr>();
      while(token != ';'){
        if(token == ','){
          break;
        }else{
          if(token == '*' || token == '/'){
            ex = new Expr(factor());
            listexpr.add(ex);
            ex = new Expr(factor());
            listexpr.add(ex);
            flag = 0;
          }else if(token == '+' || token == '-'){
            ex = new Expr(factor());
            listexpr.add(ex);
            ex = new Expr(factor());
            listexpr.add(ex);
            flag = 0;
          }else if(token == '<' || token == '>'){
            op = token;
            ex = new Expr(factor());
            listexpr.add(ex);
            ex = new Expr(factor());
            listexpr.add(ex);
            flag = 0;
          }else if(((token >= 'a' && token <= 'z') || (token >= '0' && token <= '9') || token == '\'') && flag == 0){
            ex = new Expr(factor());
            listexpr.add(ex);
            flag = 1;
          }else if(token == ';' && flag == 1){
            break;
          }else if(token != ';' && flag == 1){
            
          }else if(flag == 0){
            break;
          }
        }
      }
      return listexpr;
    }

    //Factor ::= Name | Number | String
    private Factor factor(){
      Factor f = null;
      if(token == '<' || token == '>' || token == '*' || token == '/' || token == '+' || token == '-' || token == ','){
        f = new Factor(token);
        lexer.nextToken();
      }else{
        if(token >= 'a' && token <= 'z'){
          f = new Factor(name());
        }else if((token >= '0' && token <= '9') || token == '+' || token == '-'){
          f = new Factor(numbers());
        }else if(token == '\''){
          f = new Factor(string());
        }else{
          
        }
      }
      return f;
    }

    //Type ::= ’N’ | ’F’ | ’S’
    private char type(){
      char ti = token;
      lexer.nextToken();
      return ti;
    }

    private char name(){
      if(token >= 'a' && token <= 'z'){
        ctr = token;
        lexer.nextToken();
      }
      return ctr;
    }

    private ArrayList<Character> string(){
      ArrayList<Character> ch = new ArrayList<Character>();
      char tok = ' ';
      if(token == '\''){
        lexer.nextToken();
        while(token != '\'' && token != ';'){
          tok = token;
          ch.add(tok);
          if(input[tokenPos] == ' '){
            tok = input[tokenPos];
            ch.add(tok);
          }
          lexer.nextToken();
        }
        if(token == '\'')
          lexer.nextToken();
      }else{
        
      }
      return ch;
    }
    
    //Number ::= [’+’ | ’-’] Digit [’.’ Digit]
    private Numbers numbers(){
      Numbers ret = null;
      ArrayList<Character> digito = new ArrayList<Character>();
      char op;

      if(token == '+' || token == '-'){
        digito.add(token);
        lexer.nextToken();
        ret = new Numbers(digito);
      }else if(token >= '0' && token <= '9'){
        digito.add(digit());
        if(token == '.'){
          digito.add(token);
          lexer.nextToken();
          digito.add(digit());
        }
        ret = new Numbers(digito);
      }
      return ret;
    }

    private char digit(){
      char dig = ' ';
        if(token >= '0' && token <= '9'){
          dig = token;
          lexer.nextToken();
        }else{
          
        }
        return dig;
    }    

    private ArrayList<Declaration> decl;;
    private int lineNumber;
    private char token;
    private int  tokenPos;
    private char []input;
    private int i;
    private char ctr;
    private char tipo;
    private char tokenAnt;
    private String stringValue;
    private int numberValue;
    private Lexer lexer;
    private Hashtable<String, Variable> variableTable; // variaveis criadas no sistema
    private CompilerError error;
    private ArrayList<Variable> variableDeclList; 
}
