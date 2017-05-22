/*
Vinicius Yamamoto     RA 490105
Daniel Valim    RA  511315
*/
package AST;
import java.util.ArrayList;

public class Variable {
	
	public Variable( String name, String value ) {
		this.name = name;
		this.value = value;
	}

	public String getname(){
		return name;
	}

	public String getvalue(){
		return value;
	}

	public void genC(PW pw) {
		pw.out.println(name + " = " + value + ";");
	}

	private String name;
	private String value;
}
