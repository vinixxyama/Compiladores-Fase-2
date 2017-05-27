/*
Vinicius Yamamoto     RA 490105
Daniel Valim    RA  511315
*/
package AST;
import java.util.*;

public class PrintStmt{
	public PrintStmt(ArrayList<OrTest> o){
		if(o != null){
			this.o = o;
		}
	}

	public void genC(PW pw){
		int i=0;
		StringBuffer aux = new StringBuffer();
		String frase = null;
		String sent = null;
		String tipo = null;
		char op = '\0';
		// while(i < o.size()){
		// 	e = o.get(i).getand();
		// 	nt = e.getnot();
		// 	co = nt.getcom();
		// 	ex = co.getexpr();
		// 	t = ex.getterm();
		// 	f = t.getfactor();
		// 	at = f.getatom();
		// 	op = at.getchar();
		// 	tipo = at.gettipo();

		// 	if(op == 'v'){
		// 		if(tipo.equals("string")){
		// 			aux.append("%s ");
		// 		}else if(tipo.equals("int")){
		// 			aux.append("%d ");
		// 		}else if(tipo.equals("float")){
		// 			aux.append("%f ");
		// 		}
		// 	}
		// 	i++;
		// }
		// sent = aux.toString();
		// i=0;
		pw.out.print("printf(");
		while(i<o.size()){
			// e = o.get(i).getand();
			// nt = e.getnot();
			// co = nt.getcom();
			// ex = co.getexpr();
			// t = ex.getterm();
			// f = t.getfactor();
			// at = f.getatom();
			// op = at.getchar();
			// if(op == 'f'){
			// 	frase = at.getstring();
			// 	pw.out.print("\" "+frase+" "+ sent+ "\"");
			// 	i++;
			// }else{
				o.get(i).genC(pw);
			//}
			i++;
		}
		pw.out.println(");");
	}
	
	private ArrayList<OrTest> o;
	private AndTest e;
	private NotTest nt;
	private Comparison co;
	private Expr ex;
	private Term t;
	private Factor f;
	private Atom at;

}