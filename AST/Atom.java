/*
Vinicius Yamamoto     RA 490105
Daniel Valim    RA  511315
*/
package AST;
import java.util.*;

public class Atom {
	public Atom(Numbers num, char op){
		this.num = num;
		this.op = op;
	}

	public Atom(String str, char op){
		this.str = str;
		this.op = op;
	}

	public void genC(PW pw){
		if(op == 'b' || op == 'v'){
			pw.out.print(str);
		}else if(op == 'n'){
			num.genC(pw);
		}else if(op == 'f'){
			pw.out.print("\""+str+"\"");
		}
	}

    Numbers num;
    String str;
    char op;
}