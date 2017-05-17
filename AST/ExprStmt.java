/*
Vinicius Yamamoto     RA 490105
Daniel Valim    RA  511315
*/
package AST;

public class ExprStmt {
 	public ExprStmt(char var, char atribuicao, Comparison co, int flag){
        this.var= var;
        this.atribuicao = atribuicao;
		this.co = co;
		this.flag = flag;
	}

	public void genC(PW pw){
		pw.out.print(var + " = ");
		if(flag == 0){
			co.genC(pw);
		}else{
			pw.out.print("\"");
			co.genC(pw);
			pw.out.print("\"");
		}
		pw.out.println(";");
    }

        private char  var;
        private char atribuicao;
        private Comparison co;
        private int flag;
}
