/*
Vinicius Yamamoto     RA 490105
Daniel Valim    RA  511315
*/
package AST;
import java.util.*;

public class CompoundStmt{
	public CompoundStmt(char tk, IfStmt se){
		this.tk = tk;
		this.se = se;
	}
	public CompoundStmt(char tk, WhileStmt wh){
		this.tk = tk;
		this.wh = wh;
	}

	public void genC(PW pw){
		if(tk == 'I' || tk == 'L'){
			se.genC(pw);
		}
		if(tk == 'W'){
			wh.genC(pw);
		}
	}
	private IfStmt se;
	private char tk;
	private WhileStmt wh;
}