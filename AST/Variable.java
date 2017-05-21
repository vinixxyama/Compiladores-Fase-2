/*
Vinicius Yamamoto     RA 490105
Daniel Valim    RA  511315
*/
package AST;
import java.util.ArrayList;

public class Variable {
	
	public Variable( String name, int value ) {
		this.name = name;
		this.value = value;
	}

	public Variable( String name, String value2 ) {
		this.name = name;
		this.value2 = value2;
	}

	public void genC() {
		System.out.println(name + " = " + value + ";" );
	}

	private String name;
	private int value;
	private String value2;
}
