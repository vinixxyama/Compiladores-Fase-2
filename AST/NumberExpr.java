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
