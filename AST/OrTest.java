package AST;
import java.util.*;

public class OrTest{
	public OrTest(ArrayList<AndTest> e, String str){
		if(e != null){
			this.e = e;
		}
		this.str = str;
	}

	public void genC(PW pw){
		int i=0;
		while(i < e.size()){
			if(str != null){
				e.get(i).genC(pw);
				pw.out.print(str);
				i++;
			}else{
				e.get(i).genC(pw);
				i++;
			}
		}
	}
	
	private ArrayList<AndTest> e;
	private String str;
}