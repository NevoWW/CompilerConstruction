package Absyn;

/**
 * Assignable expression abstract class.
 */

public class EqualExpr extends BinOpExpr
{
    public Expr Expr1; 
    public Expr Expr2; 

    public EqualExpr(Expr e1, Expr e2)
    {
        this.Expr1 = e1;
        this.Expr2 = e2;
    }
    public void accept(Visitor v) {v.visit(this); }
}
