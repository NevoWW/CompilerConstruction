/* Copyright (C) 1997-2003, Purdue Research Foundation of Purdue University.
 * All rights reserved.  */
package Tree;
import Temp.Temp;
import Temp.Label;
import java.util.LinkedList;

/**
 * Evaluates the expression and discards the result.
 */

public class EXP extends Stm 
{
    public Exp exp; 
    public EXP(Exp e) { exp=e; }

    public java.util.LinkedList<Exp> kids() {
        java.util.LinkedList<Exp> kids = new java.util.LinkedList<Exp>();
        kids.add(exp);
        return kids;
    }

    public Stm build(java.util.LinkedList<Exp> kids) {
        return new EXP(kids.get(0));
    }

    public void accept(IntVisitor v, int d) { v.visit(this, d); }
    public void accept(CodeVisitor v) { v.visit(this); }
}
