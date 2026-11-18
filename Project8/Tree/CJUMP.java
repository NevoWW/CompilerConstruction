/* Copyright (C) 1997-2003, Purdue Research Foundation of Purdue University.
 * All rights reserved.  */
package Tree;
import Temp.Temp;
import Temp.Label;
import java.util.LinkedList;

/**
 * Implements a conditional jump.
 */

public class CJUMP extends Stm 
{
    public int relop;
    public Exp left, right;
    public Label iftrue, iffalse;
    public CJUMP(int rel, Exp l, Exp r, Label t, Label f) 
	{
		relop=rel; left=l; right=r; iftrue=t; iffalse=f;
    }

    public final static int EQ=0, NE=1, LT=2, GT=3, LE=4, GE=5,
		ULT=6, ULE=7, UGT=8, UGE=9;

     public java.util.LinkedList<Exp> kids() {
        java.util.LinkedList<Exp> kids = new java.util.LinkedList<Exp>();
        kids.add(left);
        kids.add(right);
        return kids;
    }

    public Stm build(java.util.LinkedList<Exp> kids) {
        Exp l = kids.get(0);
        Exp r = kids.get(1);
        return new CJUMP(relop, l, r, iftrue, iffalse);
    }

    public void accept(IntVisitor v, int d) { v.visit(this, d); }
    public void accept(CodeVisitor v) { v.visit(this); }
}
