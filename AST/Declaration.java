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
	public ArrayList<String> getArray(){
		return deck;
	}

	public void genC(PW pw) {
		int i=0;
		if(deck.get(i).equals("int")){
				pw.out.print(deck.get(i)+" ");
				i++;
				pw.out.print(deck.get(i));
				i++;
			}else if(deck.get(i).equals("string")){
				pw.out.print("char ");
				i++;
				pw.out.print(deck.get(i)+"[40]");
				i++;
			}else if(deck.get(i).equals("float")){
				pw.out.print(deck.get(i)+" ");
				i++;
				pw.out.print(deck.get(i));
				i++;
			}else if(deck.get(i).equals("boolean")){
				pw.out.print(deck.get(i)+" ");
				i++;
				pw.out.print(deck.get(i));
				i++;
			}
		while(i < deck.size()){
			if(deck.get((i+1)%deck.size()) != null){
				if(deck.get(i).equals("[")){
					pw.out.print(deck.get(i));
					i++;
					pw.out.print(deck.get(i));
					i++;
					pw.out.print(deck.get(i));
					i++;
				}else{
					pw.out.print(", ");
					if(deck.get(0).equals("string")){
						pw.out.print(deck.get(i)+"[40]");
					}else{
						pw.out.print(deck.get(i));
					}
					i++;
				}
			}else{
			}
		}
		pw.out.println(";");
	}

	ArrayList<String> deck = new ArrayList<String>();
}
