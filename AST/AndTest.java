package AST;
import java.util.*;

public class AndTest{
	public AndTest(ArrayList<NotTest> nt, String str){
		if(nt != null){
			this.nt = nt;
		}
		this.str = str;
	}
	public void genC(PW pw){
		int i=0;
		while(i < nt.size()){
			if(str != null){
				nt.get(i).genC(pw);
				pw.out.print(str);
				i++;
			}else{
				nt.get(i).genC(pw);
				i++;
			}
		}
	}
	private ArrayList<NotTest> nt;
	private String str;
}