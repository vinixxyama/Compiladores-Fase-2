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
        variableTable = new Hashtable<String, String>();
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
        if(lexer.token == Symbol.IDENT){
          name();
        }else{
          error.signal("Falta o nome do programa");
        }
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
            if(lexer.token == Symbol.IDENT || lexer.token == Symbol.IF || lexer.token == Symbol.WHILE || lexer.token == Symbol.BREAK || lexer.token == Symbol.PRINT || lexer.token == Symbol.FOR){
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
        }else{
          error.signal("Falta ; na declaracao");
          break;
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
      }else if(lexer.token == Symbol.IF || lexer.token == Symbol.WHILE || lexer.token == Symbol.ELSE|| lexer.token == Symbol.FOR){
        tk = 'C';
        st = new Stmt(tk, compoundstmt()); 
      }else if(lexer.token == Symbol.ELSE){
        error.signal("else sem if");
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
        	//error
        }
      }else if(lexer.token == Symbol.PRINT){
        tk = 'R';
        lexer.nextToken();
        pt.add(printstm());
        si = new SimpleStmt(tk,pt);
      }else if(lexer.token == Symbol.BREAK){
        tk = 'B';
        si = new SimpleStmt(tk, breakStmt());
      }
      return si;
    }

    private CompoundStmt compoundstmt(){
	    CompoundStmt cmp = null;
	    String tk = null;
	    if(lexer.token == Symbol.IF || lexer.token == Symbol.ELSE){
	        tk = lexer.getNameVariable();
	        cmp = new CompoundStmt(tk, ifstmt());
	    }else if(lexer.token == Symbol.WHILE){
	        tk = lexer.getNameVariable();
	        cmp = new CompoundStmt(tk, whilestmt());
	    }else if(lexer.token == Symbol.FOR){
		    tk = lexer.getNameVariable();
		    cmp = new CompoundStmt(tk,forstmt());
	    }
    	return cmp;
    }
    
    private PrintStmt printstm(){
      PrintStmt prt = null;
      ArrayList<OrTest> o = new ArrayList<OrTest>();
      OrTest vir = null;
      String str = null;
      while(lexer.token == Symbol.IDENT || lexer.token == Symbol.NUMBER || lexer.token == Symbol.COMMA || lexer.token == Symbol.IBAR || lexer.token == Symbol.BOOLEAN || lexer.token == Symbol.NOT){
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

    private IfStmt ifstmt(){
      IfStmt se = null;
      IfStmt el = null;
      ArrayList<Stmt> st = new ArrayList<Stmt>();
      ArrayList<OrTest> or = new ArrayList<OrTest>();
      char tk = '\0';
      //se tiver chave aberta do if continua senao da erro de sintaxe
      if(lexer.token == Symbol.IF){
        lexer.nextToken();
        while(lexer.token == Symbol.IDENT || lexer.token == Symbol.NUMBER || lexer.token == Symbol.IBAR || lexer.token == Symbol.BOOLEAN || lexer.token == Symbol.NOT){
        	or.add(ortest());
    	}
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
          se = new IfStmt(tk, or, st, el);
        }else{
          error.signal("Falta abre chaves no if");
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

    private ForStmt forstmt(){
      ForStmt fo = null;
      String str = null;
      int nb1 ;
      int nb2 ;
      ArrayList<Stmt> st = new ArrayList<Stmt>(); 
      char tk = ' ';
      tk = 'F';
      lexer.nextToken();
      if(lexer.token == Symbol.IDENT){
          str = lexer.getNameVariable();
          lexer.nextToken();
      }else{

      } //error
          
      if(lexer.token == Symbol.INRANGE){
        lexer.nextToken();
        if(lexer.token == Symbol.LEFTPAR){
            lexer.nextToken();
            if(lexer.token == Symbol.NUMBER){
                nb1 = lexer.getNumberValue();
                lexer.nextToken();
                if(lexer.token == Symbol.COMMA){
                    lexer.nextToken();
                    if(lexer.token == Symbol.NUMBER){
                        nb2 = lexer.getNumberValue();
                        lexer.nextToken();
                        if(lexer.token == Symbol.RIGHTPAR){
                            lexer.nextToken();
                            if(lexer.token == Symbol.LEFTBRACES){
                                lexer.nextToken();
                                if(lexer.token == Symbol.IDENT || lexer.token == Symbol.PRINT || lexer.token == Symbol.BREAK || lexer.token == Symbol.IF || lexer.token == Symbol.WHILE || lexer.token == Symbol.FOR){
                                    while(lexer.token != Symbol.RIGHTBRACES){
                                        st.add(stmt());
                                    }
                                }
                            }else{}//erro
                                 
                            if(lexer.token != Symbol.RIGHTBRACES){ //error
                            }else{
                                lexer.nextToken();
                                fo = new ForStmt(tk, st, str,nb1,nb2);
                            }
                        }
                    }else{
                      error.signal("Falta valor no inrange");
                    }
                }
                
            }
        }
     }else{
      //error();
      }
      return fo;
    }
    
    private WhileStmt whilestmt(){
	    WhileStmt wh = null;
	    ArrayList<OrTest> or = new ArrayList<OrTest>();
	    ArrayList<Stmt> st = new ArrayList<Stmt>(); 

	    lexer.nextToken();
	    while(lexer.token == Symbol.IDENT || lexer.token == Symbol.NUMBER || lexer.token == Symbol.IBAR || lexer.token == Symbol.BOOLEAN || lexer.token == Symbol.NOT){
	        or.add(ortest());
	    }
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
	      wh = new WhileStmt(st, or);
	      return wh;
	}

    private BreakStmt breakStmt(){
    	char tk = 'b';
    	BreakStmt br = null;
      lexer.nextToken();
      if(lexer.token != Symbol.SEMICOLON){
        //error
      }else{
        lexer.nextToken();
      }
      br = new BreakStmt(tk);
      return br;
    }

    private ExprStmt exprStmt(){
    	ExprStmt exp = null;
    	ExprStmt aux = null;
    	Numbers aux2 = null;
    	Variable v = null;
    	ArrayList<OrTest> or = new ArrayList<OrTest>();
    	ArrayList<String> strg = new ArrayList<String>();
    	StringBuffer varia = new StringBuffer();
    	StringBuffer valor = new StringBuffer();
    	String var = null;
    	String var2 = null;
    	String val = null;
      String tipo = null;
    	int i = 0, j = 0, w = 0, k = 0;

    	if(lexer.token == Symbol.IDENT){
    		var = name();
    		varia.append(var);
    		if(lexer.token == Symbol.LEFTBRACKETS){
    			varia.append(exprList());
	      }
    	}
    	if(lexer.token == Symbol.ASSIGN){
    		lexer.nextToken();
    	}
    	if(lexer.token == Symbol.LEFTBRACKETS){
        valor.append(exprList());
        tipo = "vetor";
      }else if(lexer.token == Symbol.IBAR || lexer.token == Symbol.TRUE || lexer.token == Symbol.FALSE ||lexer.token == Symbol.IDENT || lexer.token == Symbol.NUMBER || lexer.token == Symbol.MINUS || lexer.token == Symbol.PLUS){
      	if(lexer.token == Symbol.MINUS || lexer.token == Symbol.PLUS){
        	valor.append(lexer.getCharValue());
        	lexer.nextToken();
        }
      	while(lexer.token == Symbol.IDENT || lexer.token == Symbol.NUMBER || lexer.token == Symbol.IBAR || lexer.token == Symbol.TRUE || lexer.token == Symbol.FALSE){
          for(i = 0;i<decl.size();i++){
	          strg = decl.get(i).getArray();
	          for(j=0;j<strg.size();j++){
	            if(var.equals(strg.get(j))){
	              if(lexer.token == Symbol.NUMBER && strg.get(0).equals("int")){
	                aux2 = numbers();
	                valor.append(aux2.getReal());
                  tipo = strg.get(0);
                  if(lexer.token == Symbol.EXPONENCIAL){
                    valor.append("^");
                    lexer.nextToken();
                  }
	              }else if(lexer.token == Symbol.IBAR && strg.get(0).equals("string")){
	                valor.append(string());
                  tipo = strg.get(0);
	              }else if(lexer.token == Symbol.NUMBER && strg.get(0).equals("float")){
	              	aux2 = numbers();
	                valor.append(aux2.getReal());
                  if(lexer.token == Symbol.EXPONENCIAL){
                    valor.append("^");
                    lexer.nextToken();
                  }
                  tipo = strg.get(0);
	              }else if((lexer.token == Symbol.TRUE || lexer.token == Symbol.FALSE)&& strg.get(0).equals("boolean")){
	              	if(lexer.token == Symbol.TRUE){
                    valor.append("true");
                  }else if(lexer.token == Symbol.FALSE){
                    valor.append("false");
                  }
                  lexer.nextToken();
                  tipo = strg.get(0);
	              }else if(lexer.token == Symbol.IDENT){
                  while(lexer.token == Symbol.IDENT || lexer.token == Symbol.NUMBER || lexer.token == Symbol.IBAR || lexer.token == Symbol.BOOLEAN || lexer.token == Symbol.NOT){
                      or.add(ortest());
                  }
	              }else{
	              	//error valor imcompativel
	              }
	        		}
	        	}
        	}
      	}
      }else{
        error.signal("Falta valor depois de =");
      }

      var = varia.toString();
      val = valor.toString();
      v = new Variable(var, val, tipo);
      exp = new ExprStmt(v, or);
    	return exp;
    }

    private String exprList(){
    	StringBuffer vet = new StringBuffer();
    	String aux = null;

  		vet.append("[");
      lexer.nextToken();
      while(lexer.token != Symbol.RIGHTBRACKETS){
        if(lexer.token == Symbol.IDENT){
      		vet.append(lexer.getNameVariable());
          lexer.nextToken();
      	}else if(lexer.token == Symbol.NUMBER){
					vet.append(lexer.getStringValue());
          lexer.nextToken();
          if(lexer.token != Symbol.COMMA && lexer.token != Symbol.RIGHTBRACKETS){
            error.signal("Falta , no vetor");
          }
      	}else if(lexer.token == Symbol.COMMA){
          vet.append(",");
          lexer.nextToken();
        }
      }
      vet.append("]");
      lexer.nextToken();
      aux = vet.toString();
      return aux;
    }

    private OrTest ortest(){
    	OrTest ou = null;
    	String str = null;
    	AndTest an = null;

    	if(lexer.token == Symbol.IDENT || lexer.token == Symbol.NUMBER || lexer.token == Symbol.IBAR || lexer.token == Symbol.BOOLEAN || lexer.token == Symbol.NOT){
	      an = andtest();
	      if(lexer.token == Symbol.OR){
	        str = "||";
	        lexer.nextToken();
	      }
        if(lexer.token == Symbol.COMMA){
          str = ",";
          lexer.nextToken();
        }
	    }
	    ou = new OrTest(an, str);
	    return ou;
    }

    private AndTest andtest(){
    	String str = null;
    	AndTest an = null;
    	NotTest no = null;

    	if(lexer.token == Symbol.IDENT || lexer.token == Symbol.NUMBER || lexer.token == Symbol.IBAR || lexer.token == Symbol.BOOLEAN || lexer.token == Symbol.NOT){
      	no = nottest();
      	if(lexer.token == Symbol.AND){
        	str = "&&";
        	lexer.nextToken();
      	}
    	}
    	an = new AndTest(no, str);
    	return an;
    }

    private NotTest nottest(){
      NotTest no = null;
      Comparison com = null;
      String str = null;
      if(lexer.token == Symbol.IDENT || lexer.token == Symbol.NUMBER || lexer.token == Symbol.IBAR || lexer.token == Symbol.BOOLEAN || lexer.token == Symbol.NOT){
        if(lexer.token == Symbol.NOT){
          str = "!";
          lexer.nextToken();
        }
        com = comparison();
      }
      no = new NotTest(com, str);
      return no;
    }

    //COMPARISON
    private Comparison comparison(){
      Comparison co = null;
      Expr ex = null;
      String str = null;
      if(lexer.token == Symbol.IDENT || lexer.token == Symbol.NUMBER || lexer.token == Symbol.IBAR || lexer.token == Symbol.BOOLEAN || lexer.token == Symbol.NOT){
        ex = expr();
        if(lexer.token == Symbol.LT || lexer.token == Symbol.LE || lexer.token == Symbol.GT || lexer.token == Symbol.GE || lexer.token == Symbol.EQ || lexer.token == Symbol.LTGT){
          	str = lexer.getNameVariable();
          	lexer.nextToken();
            if(lexer.token != Symbol.IDENT && lexer.token != Symbol.NUMBER){
              error.signal("Falta valor depois de <=");
            }
        }
      }
      co = new Comparison(ex, str);
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

      f = new Factor(at, ch);
      return f;
    }

    //Atom ::= Name[ ‘[’(Number | Name)‘]’ ] | Number | String | ’True’ | ’False’
    private Atom atom(){
      int i=0,j=0;
      Atom at = null;
      ArrayList<String> strg = new ArrayList<String>();
      StringBuffer str2 = new StringBuffer();
      Numbers num = null;
      String str = null;
      char op = '\0'; // saber se eh variavel, uma frase, numero ou boolean---- b = bool, v = variavel, f = frase, n = numero

      if(lexer.token == Symbol.IDENT){
        op = 'v';        
        str = name();
        str2.append(str);
        for(i = 0;i<decl.size();i++){
          strg = decl.get(i).getArray();
          for(j=0;j<strg.size();j++){
            if(str.equals(strg.get(j))){
              tipo = strg.get(0);
            }
          }
        }
        if(lexer.token == Symbol.LEFTBRACKETS){
          str2.append(exprList());
          str = str2.toString();
        }
        at = new Atom(str, op, tipo);
      }else if(lexer.token == Symbol.NUMBER){
        op = 'n';
        num = numbers();
        at = new Atom(num.getReal(), op, "numero");
      }else if(lexer.token == Symbol.IBAR){
        op = 'f';
        at = new Atom(string(), op, "frase");
      }else if(lexer.token == Symbol.TRUE || lexer.token == Symbol.FALSE){
        op = 'b';
        if(lexer.token == Symbol.TRUE){
          str = "true";
        }else if(lexer.token == Symbol.FALSE){
          str = "false";
        }
        at = new Atom(str, op, "boolean");
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
          if(lexer.token == Symbol.IDENT){
            ident.append(lexer.getNameVariable());
            ident.append(" ");
            lexer.nextToken();
          }else if(lexer.token == Symbol.NUMBER){
            ident.append(lexer.getStringValue());
            ident.append(" ");
            lexer.nextToken();
          }else{
            ident.append(lexer.getNameVariable());
            ident.append(" ");
            lexer.nextToken();
          }
        }
        tok = ident.toString();
        if(lexer.token == Symbol.IBAR)
          lexer.nextToken();
        else{
          error.signal("Falta fechar aspas no print");

        }
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
    private Hashtable<String, String> variableTable; // variaveis criadas no sistema
    private CompilerError error;
    
}
