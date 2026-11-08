package Absyn;

/**
 * Assignable expression abstract class.
 */

public class AddExpr extends BinOpExpr
{
    public Expr Expr1; 
    public Expr Expr2; 
    public Types.Type type; // Type of the expression, to be filled in by the type checker

    public AddExpr(Expr e1, Expr e2)
    {
        this.Expr1 = e1;
        this.Expr2 = e2;
    }
    public void accept(Visitor v) {v.visit(this); }

    public Types.Type accept(Types.TypesTypeVisitor v) { return v.visit(this); }
    
}
