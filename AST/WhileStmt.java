/*
Vinicius Yamamoto     RA 490105
Daniel Valim    RA  511315
*/
package AST;
import java.util.*;

public class WhileStmt{
        public WhileStmt(ArrayList<Stmt> st, ArrayList<OrTest> or){
		this.st = st;
		this.or = or;
	}

	public void genC(PW pw){
		pw.out.print("while(");
		for(int i=0; i<or.size();i++){
			or.get(i).genC(pw);
		}
		pw.out.println("){");
		for(int i=0; i<st.size();i++){
			st.get(i).genC(pw);
		}
		pw.out.print("\n}");
	}

	private ArrayList<Stmt> st;
   	private ArrayList<OrTest> or;
}