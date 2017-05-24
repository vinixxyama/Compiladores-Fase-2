/*
Vinicius Yamamoto     RA 490105
Daniel Valim    RA  511315
*/

package AST;
import java.util.*;

public class Comparison{
	public Comparison(Expr ex){
		this.ex = ex;
	}

	public void genC(PW pw){
		ex.genC(pw);
	}
	private Expr ex;
}