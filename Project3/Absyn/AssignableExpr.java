/* Copyright (C) 2007, Marquette University.  All rights reserved. */
package Absyn;

/**
 * Assignable expression abstract class.
 */

public abstract class AssignableExpr extends Expr
{
    /** Visitor pattern dispatch. */
    public abstract void accept(Visitor v);
}
