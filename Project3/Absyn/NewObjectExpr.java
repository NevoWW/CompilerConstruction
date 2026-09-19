/* Copyright (C) 2007, Marquette University.  All rights reserved. */
package Absyn;

/**
 * Identifier expression abstract class.
 */

public class NewObjectExpr extends Expr
{
    public Type type;
    public NewObjectExpr(Type type) 
    { 
        this.type = type; 
    }
    /** Visitor pattern dispatch. */
    public void accept(Visitor v) {v.visit(this); }
}
