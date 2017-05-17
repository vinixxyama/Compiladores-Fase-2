/*
Vinicius Yamamoto     RA 490105
Daniel Valim    RA  511315
*/
package AST;
import java.util.ArrayList;

public class Variable {
	// public Variable( String tipo, char name) {
	// 	this.tipo = tipo;
	// 	this.name = name;
	// }
	
	public Variable( char name, int value ) {
		this.name = name;
		this.value = value;
	}

	public void genC() {
		System.out.println(name + " = " + value + ";" );
	}

	private char name;
	private int value;
	private String tipo;
}
