/* Copyright (C) 2007, Marquette University.  All rights reserved. */
package Absyn;

/**
 * Identifier expression abstract class.
 */

public class IdentifierExpr extends AssignableExpr
{
    public String id;
    public Types.Type type;

    public IdentifierExpr(String id) 
    { 
        this.id = id; 
    }
    /** Visitor pattern dispatch. */
    public void accept(Visitor v) {v.visit(this); }

    public Types.Type accept(Types.TypesTypeVisitor v) { return v.visit(this); }
}
