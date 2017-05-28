/*
Vinicius Yamamoto     RA 490105
Daniel Valim    RA  511315
*/
package AST;
import java.util.ArrayList;

public class Variable {
	
	public Variable( String name, String value ,String tipo) {
		this.name = name;
		this.value = value;
        this.tipo = tipo;
	}

	public String getname(){
		return name;
	}

    public String getTipo(){
            return tipo;
    }

	public String getvalue(){
		return value;
	}

	public void genC(PW pw) {
            if(tipo.equals("string"))
                pw.out.println("strcpy("+name + " ,\" " + value + "\");");
            else
				pw.out.println(name + " = " + value + ";");
	}

    	private String name;
	private String value;
        private String tipo;
}
