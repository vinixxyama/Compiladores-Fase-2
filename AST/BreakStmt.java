/*
Vinicius Yamamoto     RA 490105
Daniel Valim    RA  511315
*/
package AST;

public class BreakStmt{
	public BreakStmt(char tk){
		this.tk = tk;
	}

	public void genC(PW pw){
		pw.out.print("\rbreak;");
	}

	char tk;
}