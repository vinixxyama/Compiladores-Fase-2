/*
Vinicius Yamamoto     RA 490105
Daniel Valim    RA  511315
*/
package AST;
import java.util.ArrayList;

public class Stmt{
	public Stmt(char k, SimpleStmt simplestmt){
		this.simplestmt = simplestmt;
		this.k = k;
	}

	public Stmt(char k, CompoundStmt compound){
		this.compound = compound;
		this.k = k;
	}

	public void genC(PW pw){
		if(k == 'S'){
			simplestmt.genC(pw);
		}else if(k == 'C'){
			compound.genC(pw);
		}
	}

	private SimpleStmt simplestmt;
	private CompoundStmt compound;
	private char k;
}
