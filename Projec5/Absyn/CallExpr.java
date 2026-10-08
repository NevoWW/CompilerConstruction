/* Copyright (C) 2007, Marquette University.  All rights reserved. */
package Absyn;
import java.util.LinkedList;

/**
 * Assignable expression abstract class.
 */

public class CallExpr extends Expr
{
    public Expr target;
    public String method;
    public LinkedList<Expr> args;
    public Types.Type type;

    public CallExpr(Expr target, String method, LinkedList<Expr> args)
    {
        this.target = target;
        this.method = method;
        this.args = args;
    }
    /** Visitor pattern dispatch. */
    public void accept(Visitor v) {v.visit(this); }

    public Types.Type accept(Types.TypesTypeVisitor v) { return v.visit(this); }
}
