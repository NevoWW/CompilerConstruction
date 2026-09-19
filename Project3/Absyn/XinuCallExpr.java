/* Copyright (C) 2013, Marquette University.  All rights reserved. */
package Absyn;
import java.util.LinkedList;

/**
 * Xinu Expressions.
 */

public class XinuCallExpr extends Expr
{
    public String method;
    public LinkedList<Expr> args;
    public XinuCallExpr(String method, LinkedList<Expr> args)
    {
		this.method = method;
		this.args   = args;
    }

    /** Visitor pattern dispatch. */
    public void accept(Visitor v) {v.visit(this); }
}
