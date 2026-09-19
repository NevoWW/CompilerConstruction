/* Copyright (C) 2007, Marquette University.  All rights reserved. */
package Absyn;
import java.util.LinkedList;

/**
 * Identifier expression abstract class.
 */

public class NewArrayExpr extends Expr
{
    public Type type;
    public LinkedList<Expr> dim;
    public NewArrayExpr(Type type, LinkedList<Expr> dim)
    {
        this.type = type;
        this.dim = dim;
    }
    /** Visitor pattern dispatch. */
    public void accept(Visitor v) {v.visit(this); }
}
