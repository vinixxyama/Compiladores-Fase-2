/*
Vinicius Yamamoto     RA 490105
Daniel Valim    RA  511315
*/

package AST;
import java.util.*;

public class Comparison{
	public Comparison(Expr ex, String str){
		this.ex = ex;
		this.str = str;
	}

	public Expr getexpr (){
		return ex;
	}

	public void genC(PW pw){
		if(str == null){
			ex.genC(pw);
		}else{
			ex.genC(pw);
			pw.out.print(str+" ");
		}
	}

	private Expr ex;
	private String str;
}