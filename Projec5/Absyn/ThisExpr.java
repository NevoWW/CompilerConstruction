/* Copyright (C) 2007, Marquette University.  All rights reserved. */
package Absyn;

/**
 * Identifier expression abstract class.
 */

public class ThisExpr extends Expr
{
    /** Visitor pattern dispatch. */
    public void accept(Visitor v) {v.visit(this); }

    public Types.Type accept(Types.TypesTypeVisitor v) { return v.visit(this); }
}
