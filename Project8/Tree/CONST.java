/* Copyright (C) 1997-2003, Purdue Research Foundation of Purdue University.
 * All rights reserved.  */
package Tree;
import Temp.Temp;
import Temp.Label;
import java.util.LinkedList;

/**
 * An integer constant.
 */

public class CONST extends Exp 
{
    public int value;
    public CONST(int v) { value=v; }

    public java.util.LinkedList<Exp> kids() {
        java.util.LinkedList<Exp> kids = new java.util.LinkedList<Exp>();
        kids.add(new CONST(value));
        return kids;
    }

    public Exp build(java.util.LinkedList<Exp> kids) {
        if (kids.isEmpty()) return this;
        return new CONST(((CONST)kids.removeFirst()).value);
    }

    public void accept(IntVisitor v, int d) { v.visit(this, d); }
    public Temp accept(CodeVisitor v) { return v.visit(this); }
}
