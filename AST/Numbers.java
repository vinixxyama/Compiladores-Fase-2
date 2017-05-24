/*
Vinicius Yamamoto     RA 490105
Daniel Valim    RA  511315
*/
package AST;

import java.util.*;

public class Numbers{
	public Numbers(String real){
			this.real = real;
	}

	public String getReal(){
		return real;
	}
	
	public void genC(PW pw){
		pw.out.print(real);
	}
	private String real;
}