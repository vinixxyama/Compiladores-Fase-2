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
      if (lexer.token == Symbol.PROGRAM){
        lexer.nextToken();
        name();
        //’:’ Body ’E’
        if(lexer.token == Symbol.COLON){
          lexer.nextToken();
          while(lexer.token != Symbol.END){
            if(lexer.token == Symbol.INT || lexer.token == Symbol.FLOAT || lexer.token == Symbol.STRING){
              while(lexer.token != Symbol.SEMICOLON){
                if(lexer.token == Symbol.INT || lexer.token == Symbol.FLOAT || lexer.token == Symbol.STRING){
                  decl.add(declaration());
                }else if(lexer.token == Symbol.IDENT || lexer.token == Symbol.COMMA){
                  decl.add(declaration());
                }
              }
              decl.add(declaration());
              lexer.nextToken();
            }
            if(lexer.token == Symbol.IDENT || lexer.token == Symbol.IF || lexer.token == Symbol.WHILE || lexer.token == Symbol.BREAK || lexer.token == Symbol.PRINT){
              st.add(stmt());
            }
          }
        }else{
          
        }
      }else{
        
      }

      if(lexer.token == Symbol.END){
        lexer.nextToken();
      }
    return new Program(decl, st);
    }

    private Declaration declaration(){
      String var = null; 
      if(lexer.token == Symbol.INT || lexer.token == Symbol.FLOAT || lexer.token == Symbol.STRING){
        tipo = type();
      }
      if(lexer.token == Symbol.IDENT){
        var = name();
      }else if(lexer.token == Symbol.SEMICOLON){
        var = lexer.getNameVariable();
      }else if(lexer.token == Symbol.COMMA){
        var = lexer.getNameVariable();
        lexer.nextToken();
      }else{
        
      }
      return new Declaration(tipo, var, 0);
    }

    private Stmt stmt(){
      Stmt st = null;
      char tk = ' ';
      if(lexer.token == Symbol.BREAK || lexer.token == Symbol.PRINT || lexer.token == Symbol.IDENT){
        tk = 'S';
        st = new Stmt(tk, simplestmt());
      }else if(lexer.token == Symbol.IF || lexer.token == Symbol.WHILE || lexer.token == Symbol.ELSE){
        tk = 'C';
        st = new Stmt(tk, compoundstmt()); 
      }
      return st;
    }

    private SimpleStmt simplestmt(){
      char tk;
      SimpleStmt si = null;
      ArrayList<PrintStmt> pt = new ArrayList<PrintStmt>();
      if(lexer.token == Symbol.IDENT){
        si = new SimpleStmt(exprStmt());
        if(lexer.token == Symbol.SEMICOLON){
          lexer.nextToken();
        }else{
          
        }
      }else if(lexer.token == Symbol.PRINT){
        tk = 'R';
        lexer.nextToken();
        while(lexer.token != Symbol.SEMICOLON){
          if(lexer.token == Symbol.COMMA){
            lexer.nextToken();
          }
          pt.add(printstm());
        }
        lexer.nextToken();
        si = new SimpleStmt(tk,pt);
      }else if(lexer.token == Symbol.BREAK){
        tk = 'B';
        breakStmt();
        si = new SimpleStmt(tk);
      }
      return si;
    }

    private CompoundStmt compoundstmt(){
      CompoundStmt cmp = null;
      String tk;
      if(lexer.token == Symbol.IF || lexer.token == Symbol.ELSE){
        tk = lexer.getNameVariable();
        cmp = new CompoundStmt(tk, ifstmt());
      }else if(lexer.token == Symbol.WHILE){
        tk = lexer.getNameVariable();
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
      //VAI PRECISAR CONSERTAR
      // for(int i=0;i<aux2.size();i++){
      //   aux3 = aux2.get(i).getFactor();
      //   for(int j=0;j<decl.size();j++){
      //     if(decl.get(j).getName() == aux3.getName()){
      //       t = decl.get(j).getType();
      //       c = decl.get(j).getName();
      //     }
      //   }
      // }
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
      if(lexer.token == Symbol.IF){
        lexer.nextToken();
        com = comparison();
        if(lexer.token == Symbol.LEFTBRACES){
          tk = 'I';
          lexer.nextToken();
          while(lexer.token != Symbol.RIGHTBRACES){
           st.add(stmt());
          }
          lexer.nextToken();
          if(lexer.token == Symbol.ELSE){
            char tk2 = 'L';
            el = new IfStmt(tk2, stmt());
          }
          se = new IfStmt(tk, com, st, el);
        }else{
          
        }
      }else if(lexer.token == Symbol.ELSE){
        lexer.nextToken();
        if(lexer.token == Symbol.LEFTBRACES){
          tk = 'L';
          lexer.nextToken();
          while(lexer.token != Symbol.RIGHTBRACES){
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
      if(lexer.token == Symbol.LEFTBRACES){
        lexer.nextToken();
        if(lexer.token == Symbol.IDENT || lexer.token == Symbol.PRINT || lexer.token == Symbol.BREAK || lexer.token == Symbol.IF || lexer.token == Symbol.WHILE){
          while(lexer.token != Symbol.RIGHTBRACES){
            st.add(stmt());
          }
        }
      }else{
        
      }
      if(lexer.token != Symbol.RIGHTBRACES){
        
      }
      lexer.nextToken();
      wh = new WhileStmt(tk, st, com);
      return wh;
    }

    private void breakStmt(){
      lexer.nextToken();
      if(lexer.token != Symbol.SEMICOLON){
        
      }else{
        lexer.nextToken();
      }
    }

    //VAI TER QUE MEXER
    private ExprStmt exprStmt(){
      Comparison aux = null;
      ArrayList<Expr> aux2 = null;
      Factor aux3 = null;
      ArrayList<Character> aux4 = null;
      ExprStmt novo;
      //pega o name
      int len=0, flag = 0;
      String ctr1 = null;
      String equal = null;
      ctr1 = name();
      //ve se o proximo eh = se sim consome
      if(lexer.token == Symbol.ASSIGN){
        equal = lexer.getNameVariable();
        lexer.nextToken();
      }else{
        
      }
      aux = comparison();
      aux2 = aux.getExpr();

      //VAI TER QUE MEXER
      // for(int i=0;i<aux2.size();i++){
      //   aux3 = aux2.get(i).getFactor();
      //   if(aux3.getStr() != null)
      //     len = aux3.getStr().size();
      //   if(len >= 1){
      //     for(int j=0;j<decl.size();j++){
      //       if(decl.get(j).getType() == 'S'){
      //         if(decl.get(j).getName() == ctr1){
      //           decl.get(j).setTamanho(len);
      //           flag = 1;
      //         }
      //       }
      //     }
      //   }
      // }
      
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
      String op;
      int flag = 0;
      ArrayList<Expr> listexpr = new ArrayList<Expr>();
      while(lexer.token != Symbol.SEMICOLON){
        if(lexer.token == Symbol.COMMA){
          break;
        }else{
          if(lexer.token == Symbol.MULT || lexer.token == Symbol.DIV){
            ex = new Expr(factor());
            listexpr.add(ex);
            ex = new Expr(factor());
            listexpr.add(ex);
            flag = 0;
          }else if(lexer.token == Symbol.PLUS || lexer.token == Symbol.MINUS){
            ex = new Expr(factor());
            listexpr.add(ex);
            ex = new Expr(factor());
            listexpr.add(ex);
            flag = 0;
          }else if(lexer.token == Symbol.LT || lexer.token == Symbol.GT){
            op = lexer.getNameVariable();
            ex = new Expr(factor());
            listexpr.add(ex);
            ex = new Expr(factor());
            listexpr.add(ex);
            flag = 0;
          }else if((lexer.token == Symbol.IDENT || lexer.token == Symbol.NUMBER || lexer.token == Symbol.IBAR) && flag == 0){
            ex = new Expr(factor());
            listexpr.add(ex);
            flag = 1;
          }else if(lexer.token == Symbol.SEMICOLON && flag == 1){
            break;
          }else if(lexer.token != Symbol.SEMICOLON && flag == 1){
            
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
      if(lexer.token == Symbol.LT || lexer.token == Symbol.GT ||lexer.token == Symbol.MULT || lexer.token == Symbol.DIV|| lexer.token == Symbol.PLUS || lexer.token == Symbol.MINUS || lexer.token == Symbol.COMMA){
        f = new Factor(lexer.getNameVariable());
        lexer.nextToken();
      }else{
        if(lexer.token ==Symbol.IDENT){
          f = new Factor(name());
        }else if(lexer.token == Symbol.NUMBER || lexer.token == Symbol.PLUS || lexer.token == Symbol.MINUS){
          f = new Factor(numbers());
        }else if(lexer.token == Symbol.IBAR){
          f = new Factor(string());
        }else{
          
        }
      }
      return f;
    }

    //Type ::= ’N’ | ’F’ | ’S’
    private String type(){
      String ti = lexer.getNameVariable();
      lexer.nextToken();
      return ti;
    }

    private String name(){
      String ctr = null;
      if(lexer.token == Symbol.IDENT){
        ctr = lexer.getNameVariable();
        lexer.nextToken();
      }
      return ctr;
    }

    private String string(){
      String tok = null;
      if(lexer.token == Symbol.IBAR){
        lexer.nextToken();
        while(lexer.token != Symbol.IBAR && lexer.token != Symbol.SEMICOLON){
          tok = lexer.getNameVariable();
          lexer.nextToken();
        }
        if(lexer.token == Symbol.IBAR)
          lexer.nextToken();
        //else ERROR() AKI
      }else{
        
      }
      return tok;
    }
    
    //Number ::= [’+’ | ’-’] Digit [’.’ Digit]
    private Numbers numbers(){
      Numbers ret = null;
      String digito = null;
      StringBuffer ident = new StringBuffer();
      char op;

      if(lexer.token == Symbol.PLUS || lexer.token == Symbol.MINUS){
        ident.append(lexer.getNameVariable());
        lexer.nextToken();
        ret = new Numbers(digito);
      }else if(lexer.token == Symbol.NUMBER){
        ident.append(lexer.getStringValue());
        if(lexer.token == Symbol.DOT){
          ident.append(lexer.getNameVariable());
          lexer.nextToken();
          ident.append(lexer.getNameVariable());
        }
        digito = ident.toString();
        ret = new Numbers(digito);
      }
      return ret;
    }

    private int digit(){
      int dig = 0;
        if(lexer.token == Symbol.NUMBER){
          dig = lexer.getNumberValue();
          lexer.nextToken();
        }else{
          
        }
        return dig;
    }

    private ArrayList<Declaration> decl;;
    private int lineNumber;
    private int  tokenPos;
    private char []input;
    private int i;
    private String tipo;
    private char tokenAnt;
    private String stringValue;
    private int numberValue;
    private Lexer lexer;
    private Hashtable<String, Variable> variableTable; // variaveis criadas no sistema
    private CompilerError error;
    private ArrayList<Variable> variableDeclList; 
}
