/*
Vinicius Yamamoto     RA 490105
Daniel Valim    RA  511315
*/
package AST;
import java.util.*;

public class PrintStmt{
	public PrintStmt(Comparison co, char t, char ch){
		this.t = t;
		this.co = co;
		this.ch = ch;
	}

	public char getTipo(){
		return t;
	}

	public char getch(){
		return ch;
	}

	public void genC(PW pw){
		co.genC(pw);
	}
	
	private char t;
	private Comparison co;
	private char ch;
}