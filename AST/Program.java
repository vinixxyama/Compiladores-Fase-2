/*
Vinicius Yamamoto     RA 490105
Daniel Valim    RA  511315
*/

package AST;

import java.util.*;

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
        int i =0, flagint = 0, flagfloat=0, flagchar=0;
        pw.out.println("#include <stdio.h>");
        pw.out.println("#include <string.h>");
        pw.out.println("");
        pw.out.println("int main(){");
        if(declList != null){
            while(i < declList.size()){
                if(declList.get(i).getType() == 'N'){
                    if(flagint == 0){
                        pw.out.print("int ");
                        flagint=1;
                    }
                    declList.get(i).genC(pw);
                    if(declList.get(i).getName() == ';')
                        pw.out.println("");
                    i++;
                }else if(declList.get(i).getType() == 'S'){
                    if(flagchar == 0){
                        pw.out.print("char ");
                        flagchar=1;
                    }
                    declList.get(i).genC(pw);
                    if(declList.get(i).getName() == ';')
                        pw.out.println("");
                    i++;
                }else if(declList.get(i).getType() == 'F'){
                    if(flagfloat == 0){
                        pw.out.print("float ");
                        flagfloat=1;
                    }
                    declList.get(i).genC(pw);
                    if(declList.get(i).getName() == ';')
                        pw.out.println("");
                    i++;
                }
            }
        }
        pw.out.println("");
        if(stm != null){
          	for(i=0; i<stm.size();i++){
            	stm.get(i).genC(pw);
        	}
        }
        pw.out.println("");
        pw.out.println("return 0;");
        pw.out.println("}");
    }                             
    private ArrayList<Declaration> declList;
    private ArrayList<Stmt> stm;
}