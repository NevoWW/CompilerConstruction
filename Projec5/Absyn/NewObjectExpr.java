/* Copyright (C) 2007, Marquette University.  All rights reserved. */
package Absyn;

/**
 * Identifier expression abstract class.
 */

public class NewObjectExpr extends Expr
{
    public Type type;
    public Types.Type type2;

    public NewObjectExpr(Type type) 
    { 
        this.type = type; 
    }
    /** Visitor pattern dispatch. */
    public void accept(Visitor v) {v.visit(this); }

    public Types.Type accept(Types.TypesTypeVisitor v) { return v.visit(this); }
}
