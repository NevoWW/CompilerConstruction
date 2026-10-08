package Absyn;

/**
 * Neg Expr.
 */
public class NegExpr extends Expr
{
    public Expr Expr1; 
    public Types.Type type;

    public NegExpr(Expr e1)
    {
        this.Expr1 = e1;
    }
    public void accept(Visitor v) {v.visit(this); }
    public Types.Type accept(Types.TypesTypeVisitor v) { return v.visit(this); }
}
