package Absyn;

public abstract class BinOpExpr extends Expr
{

    public Expr e1;
    public Expr e2;
    public Types.Type type;

   

    public abstract void accept(Visitor v);

    public abstract Types.Type accept(Types.TypesTypeVisitor v);
}