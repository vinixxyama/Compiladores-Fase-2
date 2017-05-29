/*  Vinicius Yamamoto   RA:490105
    Daniel Valim        RA:511315
*/
package AST;
import java.util.*;

public class CompositeExpr extends Expr {
    public CompositeExpr( Expr pleft, char poper, Expr pright ) {
        left = pleft;
        oper = poper;
        right = pright;
    }
    public void genC() {
        System.out.print("(");
        left.genC();
        System.out.print(" " + oper + " ");
        right.genC();
        System.out.print(")");
    }
    private Expr left, right;
    private char oper;
}

