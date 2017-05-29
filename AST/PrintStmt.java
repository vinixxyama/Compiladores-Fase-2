
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
		int i=0, j=0, flag = 0;
		StringBuffer aux = new StringBuffer();
		ArrayList<String> strlist = new ArrayList<String>();
		String frase = null;
		String aux2 = null;
		String sent = null;
		String tipo = null;
		char op = '\0';

		while(i < o.size()){
			e = o.get(i).getand();
			nt = e.getnot();
			co = nt.getcom();
			ex = co.getexpr();
			t = ex.getterm();
			f = t.getfactor();
			at = f.getatom();
			op = at.getchar();
			tipo = at.gettipo();

			if(op == 'v'){
				if(tipo.equals("string")){
					aux.append("%s ");
				}else if(tipo.equals("int")){
					aux.append("%d ");
				}else if(tipo.equals("float")){
					aux.append("%f ");
				}
			}
			if(op == 'f'){
				if(flag == 1){
					flag = 0;
					sent = aux.toString();
					strlist.add(sent);
					aux = new StringBuffer();
				}
				if(flag == 0){
					flag = 1;
					frase = at.getstring();
					aux.append(frase);
				}
			}
			i++;
			if(flag == 1 && i >= o.size()){
				flag = 0;
				sent = aux.toString();
				strlist.add(sent);
			}else if(flag == 0 && i >= o.size()){
				sent = aux.toString();
				strlist.add(sent);
			}
		}
		i = 0;
		j = 0;
		pw.out.print("printf(");
		while(i<o.size()){
			e = o.get(i).getand();
			nt = e.getnot();
			co = nt.getcom();
			ex = co.getexpr();
			t = ex.getterm();
			f = t.getfactor();
			at = f.getatom();
			op = at.getchar();
			if(op == 'f'){
				if(!strlist.isEmpty()){	
					if(j < strlist.size()){
						pw.out.print("\" "+strlist.get(j)+ "\"");
						j++;
					}
				}else{
					pw.out.print("\""+aux.toString()+"\"");
				}
				aux2 = o.get(i).getstring();
				if(aux2!= null){
					pw.out.print(",");
				}
				i++;
			}else{
				if(strlist.get(0).equals("%d ") || strlist.get(0).equals("%s ") || strlist.get(0).equals("%f ")){
					pw.out.print("\" "+strlist.get(j)+ "\",");
				}
				pw.out.print(at.getstring());
				if(o.get(i).getstring() != null){
					pw.out.print(",");
				}
				i++;
			}
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