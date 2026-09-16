/* Copyright (C) 2007, Marquette University.  All rights reserved. */
package Absyn;

/**
 * Assignable expression abstract class.
 */

public class FieldExpr extends AssignableExpr
{
    public Expr target;
    public String field;
    public FieldExpr(Expr target, String field)
    {
        this.target = target;
        this.field = field;
    }
    /** Visitor pattern dispatch. */
    public void accept(Visitor v) {v.visit(this); }
}
