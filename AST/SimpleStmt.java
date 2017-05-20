/*
Vinicius Yamamoto     RA 490105
Daniel Valim    RA  511315
*/
package AST;
import java.util.*;

public class SimpleStmt{
	public SimpleStmt (char tk, ArrayList<PrintStmt> printStmt){
		this.printStmt = printStmt;
		this.tk = tk;
	}

	public SimpleStmt (char tk){
		this.tk = tk;
	}

	public SimpleStmt (ExprStmt exstmt){
		this.exstmt = exstmt;
	}

	public void genC(PW pw){
		
	}
	private ArrayList<PrintStmt> printStmt;
	private ExprStmt exstmt;
	private char tk;
}