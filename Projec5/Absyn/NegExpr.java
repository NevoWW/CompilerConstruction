package Absyn;

/**
 * Neg Expr.
 */
public class NegExpr extends Expr
{
    public Expr Expr1; 

    public NegExpr(Expr e1)
    {
        this.Expr1 = e1;
    }
    public void accept(Visitor v) {v.visit(this); };
}
