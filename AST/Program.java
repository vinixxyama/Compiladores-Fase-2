/*
Vinicius Yamamoto     RA 490105
Daniel Valim    RA  511315
*/

package AST;

import java.util.*;
import java.util.ArrayList;

public class Program {
    public Program(ArrayList<Declaration> declList, ArrayList<Stmt> stm){
    	if(declList != null){
			this.declList = declList;
		}
		if(stm != null){
			this.stm = stm;
		}
    }
    public void genC(PW pw) {
        int i =0;
        pw.out.println("#include <stdio.h>");
        pw.out.println("#include <string.h>");
        pw.out.println("");
        pw.out.println("int main(){");
        if(declList != null){
            while(i < declList.size()){
                System.out.print("entrou");
                declList.get(i).genC(pw);
                i++;
            }
        }
        pw.out.println("");
        pw.out.println("return 0;");
        pw.out.println("}");
    }                             
    private ArrayList<Declaration> declList;
    private ArrayList<Stmt> stm;
}