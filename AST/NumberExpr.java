/*  Vinicius Yamamoto   RA:490105
    Daniel Valim        RA:511315
*/
    
package AST;
import java.util.*;
public class NumberExpr extends Expr {
    public NumberExpr( int n ) {
        this.n = n;
    }
    
    public int getValue() {
        return n;
    }

    public char getChar(){
        return c;
    }

    public void genC() {
        System.out.print(n);
    }
    private int  n;
    private char c;
}
