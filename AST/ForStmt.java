/*  Vinicius Yamamoto   RA:490105
    Daniel Valim        RA:511315
*/
package AST;
import Lexer.*;
import java.util.*;

public class ForStmt {
    public ForStmt(char tk, ArrayList<Stmt> st, Comparison com){
		this.tk = tk;
		this.st = st;
		this.com = com;
	}
    public ForStmt(char tk, ArrayList<Stmt> st,String var, int nb1,int nb2){
		this.tk = tk;
		this.st = st;
                this.var = var;
                this.nb1 = nb1;
                this.nb2 = nb2;
	}
    public void genC(PW pw){
		pw.out.print("for(");
                
                pw.out.print(var);
         	pw.out.print("=");
                pw.out.print(nb1);
                pw.out.print(";");
                
                pw.out.print(var);
                int n1= nb1;
                int n2= nb2;
                
                if( n1 <= n2) {
                    pw.out.print("<");
                }else { 
                    pw.out.print(">");
                }
                pw.out.print(nb2);
                pw.out.print(";");
               
                pw.out.print(var);
                if( n1 <= n2) {
                    pw.out.print("++");
                } else { 
                    pw.out.print("--");
                }
                
		pw.out.print("){\n");
		for(int i=0; i<st.size();i++){
			st.get(i).genC(pw);
		}
		pw.out.print("\n}");
	}


    char tk;
    ArrayList<Stmt> st;
    Comparison com;
    String var;
    int nb1;
    int nb2;
}
