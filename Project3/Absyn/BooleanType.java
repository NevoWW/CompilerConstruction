/* Copyright (C) 2007, Marquette University.  All rights reserved. */
package Absyn;

/**
 * Class types.
 */

public class BooleanType extends Type
{
    

    /** Visitor pattern dispatch. */
    public void accept(Visitor v) {v.visit(this); }
}
