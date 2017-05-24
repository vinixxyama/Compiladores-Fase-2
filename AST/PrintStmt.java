/*
Vinicius Yamamoto     RA 490105
Daniel Valim    RA  511315
*/
package AST;
import java.util.*;

public class PrintStmt{
	public PrintStmt(ArrayList<OrTest> o){
		if(o != null){
			this.o = o;
		}
	}

	public void genC(PW pw){
		int i=0;
		pw.out.print("printf(");
		while(i < o.size()){
			o.get(i).genC(pw);
			i++;
		}
		pw.out.print(");");
	}
	
	private ArrayList<OrTest> o;
}