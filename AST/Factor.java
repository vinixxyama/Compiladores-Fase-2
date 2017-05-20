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

	public Factor(char ch){
		this.ch = ch;
	}

	public Factor(String str){
		this.str = str;
	}

	public String getStr(){
		return str;
	}

	public char getName(){
		return ch;
	}

	public void print(){
	}

	public void genC(PW pw){
		if(str != null){
			//PRECISA MUDAR
			// for(int i=0; i<str.size();i++){
   //          	pw.out.print(str.get(i));
   //      	}
		}
		if(ch != '\0'){
			pw.out.print(ch);
		}
		if(num != null){
			num.genC(pw);
		}
    }
	private Numbers num;
	private String str;
	private char ch;
}