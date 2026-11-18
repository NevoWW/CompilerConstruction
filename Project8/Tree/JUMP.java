/* Copyright (C) 1997-2003, Purdue Research Foundation of Purdue University.
 * All rights reserved.  */
package Tree;
import Temp.Temp;
import Temp.Label;
import java.util.List;
import java.util.LinkedList;

/**
 * Jump (transfer control) to address e.
 */

public class JUMP extends Stm 
{
    public Exp exp;
    public LinkedList<Label> targets;
    public JUMP(Exp e, LinkedList<Label> t) { exp=e; targets=t; }
    public JUMP(Label target) 
	{
		exp = new NAME(target);
		targets = new LinkedList<Label>();
		targets.addFirst(target);
    }

    public java.util.LinkedList<Exp> kids() {
        java.util.LinkedList<Exp> kids = new java.util.LinkedList<Exp>();
        kids.add(exp);
        return kids;
    }

    public Stm build(java.util.LinkedList<Exp> kids) {
        return new JUMP(kids.get(0), targets);
    }

    public void accept(IntVisitor v, int d) { v.visit(this, d); }
    public void accept(CodeVisitor v) { v.visit(this); }
}
