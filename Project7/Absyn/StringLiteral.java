/* Copyright (C) 2009, Marquette University.  All rights reserved. */
package Absyn;

/**
 * String Literals.
 */

public class StringLiteral extends Expr
{
    public String value;
    public Types.Type type;

    public StringLiteral(String value)
    {
		this.value = value;
    }

    /** Visitor pattern dispatch. */
    public void accept(Visitor v) {v.visit(this); }

    public Types.Type accept(Types.TypesTypeVisitor v) { return v.visit(this); }
}
