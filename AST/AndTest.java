/*	Vinicius Yamamoto	RA:490105
	Daniel Valim		RA:511315
*/
package AST;
import java.util.*;

public class AndTest{
	public AndTest(NotTest nt, String str){
		this.nt = nt;
		this.str = str;
	}

	public NotTest getnot (){
		return nt;
	}

	public void genC(PW pw){
		int i=0;
		if(str != null){
			nt.genC(pw);
			pw.out.print(str);
			i++;
		}else{
			nt.genC(pw);
			i++;
		}
	}
	private NotTest nt;
	private String str;
}