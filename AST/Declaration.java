/*
Vinicius Yamamoto     RA 490105
Daniel Valim    RA  511315
*/
package AST;
import java.util.ArrayList;

public class Declaration {
	public Declaration(String tipo, String name, int tamanho) {
		this.tipo = tipo;
		this.name = name;
		this.tamanho = tamanho;
	}

	public String getName(){
		return name;
	}

	public String getType(){
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

	private String name;
	private String tipo;
	private int tamanho;
}
