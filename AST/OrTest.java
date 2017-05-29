/*	Vinicius Yamamoto	RA:490105
	Daniel Valim		RA:511315
*/
package AST;
import java.util.*;

public class OrTest{
	public OrTest(AndTest e, String str){
		this.e = e;
		this.str = str;
	}

	public OrTest(String str){
		this.str = str;
	}

	public AndTest getand (){
		return e;
	}

	public String getstring (){
		return str;
	}

	public void genC(PW pw){
		if(str != null){
			if(str.equals(",")){
				pw.out.print(str);
			}else{
				e.genC(pw);
				pw.out.print(str);
			}
		}else{
			e.genC(pw);
		}
	}
	
	private	AndTest e;
	private String str;
}