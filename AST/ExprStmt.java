/*
Vinicius Yamamoto     RA 490105
Daniel Valim    RA  511315
*/
package AST;
import java.util.ArrayList;

public class ExprStmt {
 	public ExprStmt(Variable var){
        	this.var = var;
	}

	public void genC(PW pw){
		
		var.genC(pw);
    }
	private Variable var;
}
