/*
Vinicius Yamamoto     RA 490105
Daniel Valim    RA  511315
*/
package AST;

public class BreakStmt{
	public BreakStmt(){
	}

	public void genC(PW pw){
		pw.out.print("Break;");
	}
}