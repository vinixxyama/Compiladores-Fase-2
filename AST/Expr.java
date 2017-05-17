/*
Vinicius Yamamoto     RA 490105
Daniel Valim    RA  511315
*/
package AST;
import java.util.*;

public class Expr {
	public Expr(Factor f){
		this.f = f;
	}

	public Factor getFactor(){
		return f;
	}

    public void genC(PW pw){
    	f.genC(pw);
    }
   	private Factor f;
	private char op;
}