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
		ArrayList<Character> listch = new ArrayList<Character>();
		int i = 0;
		if(tk == 'R'){
			pw.out.print("printf(\"");
			for(i = 0;i<printStmt.size();i++){
				if(printStmt.get(i).getTipo() == 'N'){
					listch.add(printStmt.get(i).getch());
					pw.out.print("%d ");
				}else if(printStmt.get(i).getTipo() == 'S'){
					listch.add(printStmt.get(i).getch());
					pw.out.print("%s ");
				}else if(printStmt.get(i).getTipo() == 'F'){
					listch.add(printStmt.get(i).getch());
					pw.out.print("%f ");
				}else{
					printStmt.get(i).genC(pw);
				}
			}
			pw.out.print("\"");
			for(i = 0;i<listch.size();i++){
				pw.out.print(",");
				pw.out.print(listch.get(i));
			}
			pw.out.println(");");
		}else if(tk == 'B'){
			pw.out.print("break;");
		}else{
			exstmt.genC(pw);
		}
	}
	private ArrayList<PrintStmt> printStmt;
	private ExprStmt exstmt;
	private char tk;
}