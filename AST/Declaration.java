/*
Vinicius Yamamoto     RA 490105
Daniel Valim    RA  511315
*/
package AST;
import java.util.ArrayList;

public class Declaration {
	public Declaration(ArrayList<String> deck) {
		if(deck != null){
			this.deck = deck;
		}
	}

	public void genC(PW pw) {
		int i=0;
		while(i < deck.size()){
			if(deck.get(i).equals("int")){
				pw.out.print(deck.get(i)+" ");
				i++;
				pw.out.print(deck.get(i));
				i++;
			}else if(deck.get(i).equals("string")){
				pw.out.print(deck.get(i)+" ");
				i++;
				pw.out.print(deck.get(i));
				i++;
			}else if(deck.get(i).equals("float")){
				pw.out.print(deck.get(i)+" ");
				i++;
				pw.out.print(deck.get(i));
				i++;
			}

			if(deck.get((i+1)%deck.size()) != null){
				pw.out.print(", ");
				pw.out.print(deck.get(i));
				i++;
			}else{
			}
		}
		pw.out.println(";");
	}

	ArrayList<String> deck = new ArrayList<String>();
}