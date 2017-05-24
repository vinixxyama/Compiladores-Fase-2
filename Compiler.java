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
            if(lexer.token == Symbol.INT || lexer.token == Symbol.FLOAT || lexer.token == Symbol.STRING || lexer.token == Symbol.BOOLEAN){
              while(lexer.token != Symbol.SEMICOLON){
                if(lexer.token == Symbol.INT || lexer.token == Symbol.FLOAT || lexer.token == Symbol.STRING || lexer.token == Symbol.BOOLEAN){
                  decl.add(declaration());
                }
              }
              lexer.nextToken();
            }
            if(lexer.token == Symbol.IDENT || lexer.token == Symbol.IF || lexer.token == Symbol.WHILE || lexer.token == Symbol.BREAK || lexer.token == Symbol.PRINT){
              st.add(stmt());
            }

          }
        }
      }
      if(lexer.token == Symbol.END){
        lexer.nextToken();
      }
    return new Program(decl, st);
    }

    private Declaration declaration(){
      String var = null;
      ArrayList<String> varia = new ArrayList<String>();
      Declaration dec = null;
      if(lexer.token == Symbol.INT || lexer.token == Symbol.FLOAT || lexer.token == Symbol.STRING || lexer.token == Symbol.BOOLEAN){
        tipo = type();
        varia.add(tipo);
      }
      while(lexer.token != Symbol.SEMICOLON){
        if(lexer.token == Symbol.IDENT){
          varia.add(name());
          if(lexer.token == Symbol.LEFTBRACKETS){
            varia.add("[");
            lexer.nextToken();
            if(lexer.token == Symbol.NUMBER){
              varia.add(lexer.getStringValue());
              lexer.nextToken();
            }else{
              //error nao eh numero
            }
            if(lexer.token == Symbol.RIGHTBRACKETS){
              varia.add("]");
              lexer.nextToken();
            }else{
              //error falta chaves
            }
          }
        }
        if(lexer.token == Symbol.COMMA){
          lexer.nextToken();
        }
      }
      dec = new Declaration(varia);
      return dec;
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
      }else if(lexer.token == Symbol.PRINT){
        tk = 'R';
        lexer.nextToken();
        pt.add(printstm());
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
      ArrayList<OrTest> o = new ArrayList<OrTest>();
      while(lexer.token == Symbol.IDENT || lexer.token == Symbol.NUMBER || lexer.token == Symbol.IBAR || lexer.token == Symbol.BOOLEAN || lexer.token == Symbol.NOT){
        o.add(ortest());
      }
      if(lexer.token == Symbol.SEMICOLON){
        lexer.nextToken();
      }else{
        //error
      }
      prt = new PrintStmt(o);
      return prt;
    }

    private OrTest ortest(){
      OrTest ou = null;
      String str = null;
      ArrayList<AndTest> an = new ArrayList<AndTest>();

      if(lexer.token == Symbol.IDENT || lexer.token == Symbol.NUMBER || lexer.token == Symbol.IBAR || lexer.token == Symbol.BOOLEAN || lexer.token == Symbol.NOT){
        an.add(andtest());
        if(lexer.token == Symbol.OR){
          str = lexer.getNameVariable();
          lexer.nextToken();
        }
      }
      ou = new OrTest(an, str);
      return ou;
    }

    private AndTest andtest(){
      String str = null;
      AndTest an = null;
      ArrayList<NotTest> no = new ArrayList<NotTest>();

      if(lexer.token == Symbol.IDENT || lexer.token == Symbol.NUMBER || lexer.token == Symbol.IBAR || lexer.token == Symbol.BOOLEAN || lexer.token == Symbol.NOT){
        no.add(nottest());
        if(lexer.token == Symbol.AND){
          str = lexer.getNameVariable();
          lexer.nextToken();
        }
      }
      an = new AndTest(no, str);
      return an;
    }

    private NotTest nottest(){
      NotTest no = null;
      Comparison co = null;
      String str = null;
      if(lexer.token == Symbol.IDENT || lexer.token == Symbol.NUMBER || lexer.token == Symbol.IBAR || lexer.token == Symbol.BOOLEAN || lexer.token == Symbol.NOT){
        if(lexer.token == Symbol.NOT){
          str = lexer.getNameVariable();
          lexer.nextToken();
        }
        co = comparison();
      }
      no = new NotTest(co, str);
      return no;
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
      ArrayList<String> strg = new ArrayList<String>();
      Variable aux = null;
      Numbers aux2 = null;
      ExprStmt novo = null;
      int i = 0;
      String ctr1 = null;
      String aux3 = null;
      StringBuffer ident = new StringBuffer();
      StringBuffer vet = new StringBuffer();
      int flag = 0;

      ctr1 = name();
      //se for vetor salva o vetor
      if(lexer.token == Symbol.LEFTBRACKETS){
        vet.append(ctr1);
        vet.append("[");
        lexer.nextToken();
        while(lexer.token != Symbol.RIGHTBRACKETS){
            vet.append(lexer.getStringValue());
            lexer.nextToken();

        }
        vet.append("]");
        lexer.nextToken();
        flag = 1;
      }
      //ve se o proximo eh = se sim consome
      if(lexer.token == Symbol.ASSIGN){
        lexer.nextToken();
      }else{
        //ERROR ATRIBUIÇAO SEM VALOR
      }
      if(lexer.token == Symbol.NUMBER || lexer.token == Symbol.IDENT || lexer.token == Symbol.IBAR || lexer.token == Symbol.LEFTBRACKETS){
        //verificar se variavel pode receber o valor
        for(i = 0;i<decl.size();i++){
          strg = decl.get(i).getArray();
          for(int j=0;j<strg.size();j++){
            if(ctr1.equals(strg.get(j))){
              if((lexer.token == Symbol.NUMBER || lexer.token == Symbol.LEFTBRACKETS) && strg.get(0).equals("int")){
                if(lexer.token == Symbol.LEFTBRACKETS){
                  ident.append("[");
                  lexer.nextToken();
                  while(lexer.token != Symbol.RIGHTBRACKETS){
                    if(lexer.token == Symbol.COMMA){
                      ident.append(",");
                      lexer.nextToken();
                    }else{
                      ident.append(lexer.getStringValue());
                      lexer.nextToken();
                    }
                  }
                  ident.append("]");
                  lexer.nextToken();
                }else{
                  aux2 = numbers();
                  ident.append(aux2.getReal());
                }
                aux3 = ident.toString();
                if(flag == 1){
                  ctr1 = vet.toString();
                  aux = new Variable(ctr1, aux3);
                }else{
                  aux = new Variable(ctr1, aux3);
                }
              }else if(lexer.token == Symbol.IBAR && strg.get(0).equals("string")){
                ident.append("'");
                lexer.nextToken();
                while(lexer.token !=  Symbol.IBAR){
                  ident.append(lexer.getNameVariable());
                  lexer.nextToken();
                }
                ident.append("'");
                lexer.nextToken();
                aux3 = ident.toString();
                aux = new Variable(ctr1, aux3);
                lexer.nextToken();
              }else if(lexer.token == Symbol.NUMBER && strg.get(0).equals("float")){
                aux2 = numbers();
                aux = new Variable(ctr1, aux2.getReal());
                
              }else if(lexer.token == Symbol.IDENT && strg.get(0).equals("boolean")){
                aux = new Variable(ctr1, lexer.getNameVariable());
                lexer.nextToken();
              }else{
                //error valor nao bate com a variavel ex int recebendo string;
              }
            }
          }
        }
      }else{
        //error NADA DECLARADO;
      }
      if(lexer.token == Symbol.SEMICOLON){
        lexer.nextToken();
      }
      novo = new ExprStmt(aux);
      return novo;
    }

    //COMPARISON
    private Comparison comparison(){
      Comparison co = null;
      co = new Comparison(expr());
      return co;
    }

    //Expr ::= Term {(’+’ | ’-’) Term}
    private Expr expr(){
      Expr ex = null;
      Term t = null;
      char ch = '\0';

      if(lexer.token == Symbol.IDENT || lexer.token == Symbol.NUMBER || lexer.token == Symbol.IBAR || lexer.token == Symbol.BOOLEAN || lexer.token == Symbol.PLUS || lexer.token == Symbol.MINUS){
        t = term();
        if(lexer.token == Symbol.PLUS || lexer.token == Symbol.MINUS){
          ch = lexer.getCharValue();
          lexer.nextToken();
        }
      }
      ex = new Expr(t, ch);
      return ex;
    }

    //Term ::= Factor {(’*’ | ’/’) Factor}
    private Term term(){
      Term t = null;
      Factor f = null;
      char ch = '\0';

      if(lexer.token == Symbol.IDENT || lexer.token == Symbol.NUMBER || lexer.token == Symbol.IBAR || lexer.token == Symbol.BOOLEAN || lexer.token == Symbol.PLUS || lexer.token == Symbol.MINUS){
        f = factor();
        if(lexer.token == Symbol.MULT || lexer.token == Symbol.DIV){
          ch = lexer.getCharValue();
          lexer.nextToken();
        }
      }
      t = new Term(f, ch);
      return t;
    }

    //Factor ::= [’+’|’-’] Atom {^ Factor}
    private Factor factor(){
      Factor f = null;
      Atom at = null;
      char ch = '\0';

      if(lexer.token == Symbol.PLUS || lexer.token == Symbol.MINUS){
        ch = lexer.getCharValue();
        lexer.nextToken();
      }

      if(lexer.token == Symbol.IDENT || lexer.token == Symbol.NUMBER || lexer.token == Symbol.IBAR || lexer.token == Symbol.BOOLEAN){
        at = atom();
      }

      if(lexer.token == Symbol.EXPONENCIAL){
        lexer.nextToken();
      }

      f = new Factor(at, ch);
      return f;
    }

    //Atom ::= Name[ ‘[’(Number | Name)‘]’ ] | Number | String | ’True’ | ’False’
    private Atom atom(){
      Atom at = null;
      Numbers num = null;
      String str = null;
      char op = '\0'; // saber se eh variavel, uma frase, numero ou boolean---- b = bool, v = variavel, f = frase, n = numero

      if(lexer.token == Symbol.IDENT){
        op = 'v';
        at = new Atom(name(), op);
      }else if(lexer.token == Symbol.NUMBER){
        op = 'n';
        at = new Atom(numbers(), op);
      }else if(lexer.token == Symbol.IBAR){
        op = 'f';
        at = new Atom(string(), op);
      }else if(lexer.token == Symbol.TRUE || lexer.token == Symbol.FALSE){
        op = 'b';
        str = lexer.getNameVariable();
        at = new Atom(str, op);
        lexer.nextToken();
      }

    return at;
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
      StringBuffer ident = new StringBuffer();
      String tok = null;
      if(lexer.token == Symbol.IBAR){
        lexer.nextToken();
        while(lexer.token != Symbol.IBAR && lexer.token != Symbol.SEMICOLON){
          ident.append(lexer.getNameVariable());
          lexer.nextToken();
        }
        tok = ident.toString();
        if(lexer.token == Symbol.IBAR)
          lexer.nextToken();
        //else ERROR() AKI
      }else{
        
      }
      return tok;
    }
    
    //Number ::= [Signal] Digit{Digit} [’.’ Digit{Digit}]
    private Numbers numbers(){
      Numbers ret = null;
      String digito = null;
      StringBuffer ident = new StringBuffer();
      char op;

      if(lexer.token == Symbol.PLUS || lexer.token == Symbol.MINUS){
        ident.append(lexer.getNameVariable());
        lexer.nextToken();
      }
      if(lexer.token == Symbol.NUMBER){
        while(lexer.token == Symbol.NUMBER){
          ident.append(lexer.getStringValue());
          lexer.nextToken();
        }
        if(lexer.token == Symbol.DOT){
          ident.append(lexer.getCharValue());
          lexer.nextToken();
          while(lexer.token == Symbol.NUMBER){
            ident.append(lexer.getStringValue());
            lexer.nextToken();
          }
        }
        digito = ident.toString();
      }
      ret = new Numbers(digito);
      return ret;
    }

    //Digit ::= ’0’ | ... | ’9’
    private int digit(){
      int dig = 0;
        if(lexer.token == Symbol.NUMBER){
          dig = lexer.getNumberValue();
          lexer.nextToken();
        }else{
          
        }
        return dig;
    }

    private ArrayList<Declaration> decl;
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
    
}
