/*
Vinicius Yamamoto     RA 490105
Daniel Valim    RA  511315
*/
package AST;
import java.util.*;

public class Expr {
	public Expr(Term t, char op){
		this.t = t;
        this.op = op;
	}

    public Term getterm (){
        return t;
    }

    public void genC(PW pw){
    	if(op != '\0'){
    		t.genC(pw);
    		pw.out.print(op);
    	}else{
    		t.genC(pw);
    	}
    }
   	private Term t;
	private char op;
}