/*
Vinicius Yamamoto     RA 490105
Daniel Valim    RA  511315
*/
package AST;
import java.util.ArrayList;

public class ExprStmt {
 	public ExprStmt(Variable var, ArrayList<OrTest> or){
		if(or != null){
			this.or = or;
		}
    	this.var = var;
	}

	public void genC(PW pw){
		int i=0;
		if(or.isEmpty()){
			var.genC(pw);
		}else{
			pw.out.print(var.getname()+" = ");
			while(i < or.size()){
				or.get(i).genC(pw);
				i++;
			}
			pw.out.println(";");
		}
    }
	private Variable var;
	private ArrayList<OrTest> or;
}
