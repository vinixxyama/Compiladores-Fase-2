/*	Vinicius Yamamoto	RA:490105
	Daniel Valim		RA:511315
*/
package AST;
import java.util.*;

public class NotTest{
	public NotTest(Comparison co, String str){
		this.co = co;
		this.str = str;
	}

	public Comparison getcom (){
		return co;
	}

	public void genC(PW pw){
		int i=0;
		if(str != null){
			pw.out.print(str);
			co.genC(pw);
		}else{
			co.genC(pw);
		}
	}
	private Comparison co;
	private String str;
}