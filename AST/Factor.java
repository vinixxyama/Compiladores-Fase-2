/*
Vinicius Yamamoto     RA 490105
Daniel Valim    RA  511315
*/
package AST;
import java.util.ArrayList;

public class Factor{
	public Factor(Numbers num){
		this.num = num;
	}
	public Factor(String str){
		this.str = str;
	}

	public String getStr(){
		return str;
	}

	public void genC(PW pw){
		
    }
	private Numbers num;
	private String str;
}