/*
Vinicius Yamamoto     RA 490105
Daniel Valim    RA  511315
*/
package AST;
import java.util.*;

public class CompoundStmt{
	public CompoundStmt(String tk, IfStmt se){
		this.tk = tk;
		this.se = se;
	}
	public CompoundStmt(String tk, WhileStmt wh){
		this.tk = tk;
		this.wh = wh;
	}

	public void genC(PW pw){
		if(tk == "if" || tk == "else"){
			se.genC(pw);
		}
		if(tk == "while"){
			wh.genC(pw);
		}
	}
	private IfStmt se;
	private String tk;
	private WhileStmt wh;
}