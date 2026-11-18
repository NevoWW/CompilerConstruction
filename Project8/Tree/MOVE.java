/* Copyright (C) 1997-2003, Purdue Research Foundation of Purdue University.
 * All rights reserved.  */
package Tree;
import Temp.Temp;
import Temp.Label;
import java.util.LinkedList;

/**
 * Implements a move instruction.
 */

public class MOVE extends Stm 
{
    public Exp dst, src;
    public MOVE(Exp d, Exp s) { dst=d; src=s; }

     public java.util.LinkedList<Exp> kids() {
        java.util.LinkedList<Exp> kids = new java.util.LinkedList<Exp>();
        if (dst instanceof MEM) kids.add(((MEM)dst).exp);
        else kids.add(dst);
        kids.add(src);
        return kids;
    }

    public Stm build(java.util.LinkedList<Exp> kids) {
        if (dst instanceof MEM) return new MOVE(new MEM(kids.get(0)), kids.get(1));
        return new MOVE(kids.get(0), kids.get(1));
    
    }
    public void accept(IntVisitor v, int d) { v.visit(this, d); }
    public void accept(CodeVisitor v) { v.visit(this); }
}
