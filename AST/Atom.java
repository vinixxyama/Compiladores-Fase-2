/*
Vinicius Yamamoto     RA 490105
Daniel Valim    RA  511315
*/
package AST;
import java.util.*;

public class Atom {
	public Atom(String str, char op){
		this.str = str;
		this.op = op;
	}

	public Atom(String str, char op, String tipo){
		this.str = str;
		this.op = op;
		this.tipo = tipo;
	}

	public String getstring(){
		return str;
	}

	public char getchar(){
		return op;
	}	

	public String gettipo(){
		return tipo;
	}	

	public void genC(PW pw){
		if(op == 'b' || op == 'v'){
			pw.out.print(str);
		}else if(op == 'n'){
			pw.out.print(str);
		}else if(op == 'f'){
			pw.out.print(str);
		}
	}

	private String tipo;
    private String str;
    private char op;
}