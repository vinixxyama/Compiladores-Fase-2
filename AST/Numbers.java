/*
Vinicius Yamamoto     RA 490105
Daniel Valim    RA  511315
*/
package AST;

import java.util.*;

public class Numbers{
	public Numbers(ArrayList<Character> real){
		if(real != null){
			this.real = real;
		}
	}

	public void genC(PW pw){
		for(int i=0; i<real.size();i++){
            pw.out.print(real.get(i));
        }
	}
	private ArrayList<Character> real;
}