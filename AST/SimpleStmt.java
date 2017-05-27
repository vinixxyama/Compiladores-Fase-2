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
	
	public SimpleStmt (char tk, BreakStmt br){
		this.tk = tk;
		this.br = br;
	}

	public SimpleStmt (ExprStmt exstmt){
		this.exstmt = exstmt;
	}

	public void genC(PW pw){
		int i = 0;
		if(tk == 'R'){
			while(i < printStmt.size()){
				printStmt.get(i).genC(pw);
				i++;
			}
		}else if(tk == 'B'){
			br.genC(pw);
		}else{
			exstmt.genC(pw);
		}
	}
	private ArrayList<PrintStmt> printStmt;
	private ExprStmt exstmt;
	private BreakStmt br;
	private char tk;
}