/* Copyright (C) 1997-2003, Purdue Research Foundation of Purdue University.
 * All rights reserved.  */
package Tree;
import Temp.Temp;
import Temp.Label;
import java.util.LinkedList;

/**
 * Contents of a word of memory starting at address e.
 */

public class MEM extends Exp 
{
    public Exp exp;
    public MEM(Exp e) { exp=e; }

    public java.util.LinkedList<Exp> kids() {
        java.util.LinkedList<Exp> kids = new java.util.LinkedList<Exp>();
        kids.add(exp);
        return kids;
    }

    public Exp build(java.util.LinkedList<Exp> kids) {
        return new MEM(kids.get(0));
    }


    public void accept(IntVisitor v, int d) { v.visit(this, d); }
    public Temp accept(CodeVisitor v) { return v.visit(this); }
}
