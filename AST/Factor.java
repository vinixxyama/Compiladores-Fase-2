/*
Vinicius Yamamoto     RA 490105
Daniel Valim    RA  511315
*/
package AST;
import java.util.ArrayList;

public class Factor{
	public Factor(Atom at, char op){
		this.at = at;
		this.op = op;
	}

	public Atom getatom(){
		return at;
	}

	public void genC(PW pw){
		if(op != '\0'){
			pw.out.print(op);
			at.genC(pw);
		}else{
			at.genC(pw);
		}
    }

	private Atom at;
	private char op;
}