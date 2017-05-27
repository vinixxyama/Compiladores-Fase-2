/*
Vinicius Yamamoto     RA 490105
Daniel Valim    RA  511315
*/
package AST;
import java.util.*;

public class Term{
	public Term(Factor f, char op){
		this.f = f;
		this.op = op;
	}

    public Factor getfactor (){
        return f;
    }

	public void genC(PW pw){
    	if(op != '\0'){
    		f.genC(pw);
    		pw.out.print(op);
    	}else{
    		f.genC(pw);
    	}
    }

	private Factor f;
    private char op;
}	