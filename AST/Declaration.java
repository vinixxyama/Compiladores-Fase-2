/*
Vinicius Yamamoto     RA 490105
Daniel Valim    RA  511315
*/
package AST;
import java.util.ArrayList;

public class Declaration {
	public Declaration(char tipo, char name, int tamanho) {
		this.tipo = tipo;
		this.name = name;
		this.tamanho = tamanho;
	}

	public char getName(){
		return name;
	}

	public char getType(){
		return tipo;
	}

	public void setTamanho(int tamanho){
		this.tamanho = tamanho;
	}

	public void genC(PW pw) {
		pw.out.print(name);
		if(tamanho > 1){
			pw.out.print("[");
			pw.out.print(tamanho);
			pw.out.print("]");
		}
	}

	private char name;
	private char tipo;
	private int tamanho;
}
