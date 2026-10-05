package Absyn;

public abstract class BinOpExpr extends Expr
{

    public Expr e1;
    public Expr e2;

   

    public abstract void accept(Visitor v);



}