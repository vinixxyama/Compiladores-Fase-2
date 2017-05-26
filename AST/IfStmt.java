/*
Vinicius Yamamoto     RA 490105
Daniel Valim    RA  511315
*/
package AST;
import java.util.*;

public class IfStmt{
	public IfStmt(char k, Comparison cmp, ArrayList<Stmt> st, IfStmt el){
		this.k = k;
		this.cmp = cmp;
		this.el = el;
		if(st != null){
			this.st = st;
		}
	}
	public IfStmt(char k, ArrayList<OrTest> or, ArrayList<Stmt> st, IfStmt el){
		this.k = k;
		this.or = or;
		this.el = el;
		if(st != null){
			this.st = st;
		}
	}
	public IfStmt(char k, ArrayList<Stmt> st){
		this.k = k;
		if(st != null){
			this.st = st;
		}
	}

	public IfStmt(char k, Stmt st2){
		this.k = k;
		this.st2 = st2;
	}

	public void genC(PW pw){
		int j=0;
		if(k == 'I'){
			pw.out.print("if(");
			while(j < or.size()){
				or.get(j).genC(pw);
				j++;
			}
			j=0;
			pw.out.print("){\n");
			for(int i=0;i<st.size();i++){
				st.get(i).genC(pw);
			}
			pw.out.print("\n}");
		}
		if(el != null){
			pw.out.print("else{\n");
			el.st2.genC(pw);
			pw.out.print("\n}");
		}
		if(k == 'L'){
			for(int i=0;i<st.size();i++){
				st.get(i).genC(pw);
			}
		}
	}
	private Comparison cmp;
	private ArrayList<Stmt> st;
	private Stmt st2;
	private char k;
	private IfStmt el;
    private ArrayList<OrTest> or = new ArrayList<OrTest>();
        
}