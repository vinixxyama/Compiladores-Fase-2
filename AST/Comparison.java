/*
Vinicius Yamamoto     RA 490105
Daniel Valim    RA  511315
*/

package AST;
import java.util.*;

public class Comparison{
	public Comparison(ArrayList<Expr> ex){
		if(ex != null){
			this.ex = ex;
		}
	}

	public ArrayList<Expr> getExpr(){
		return ex;
	}

	public void genC(PW pw){
		if(ex != null){
          	for(int i=0; i<ex.size();i++){
            	ex.get(i).genC(pw);
        	}
        }
	}
	private ArrayList<Expr> ex;
}