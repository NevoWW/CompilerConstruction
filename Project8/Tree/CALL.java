/* Copyright (C) 1997-2003, Purdue Research Foundation of Purdue University.
 * All rights reserved.  */
package Tree;
import Temp.Temp;
import Temp.Label;
import java.util.List;
import java.util.LinkedList;

/**
 * Implements a procedure call, application of a function to an
 * argument list.
 */

public class CALL extends Exp 
{
    public Exp func;
    public List<Exp> args;
    public CALL(Exp f, List<Exp> a) { func=f; args=a; }

    public java.util.LinkedList<Exp> kids() {
        java.util.LinkedList<Exp> kids = new java.util.LinkedList<Exp>();
        kids.add(func);
        for (Exp arg : args) {
            kids.add(arg);
        }
        return kids;
    }

    public Exp build(java.util.LinkedList<Exp> kids) {
        return new CALL(kids.get(0), new LinkedList<Exp>(kids.subList(1, kids.size())));
    }
    
    public void accept(IntVisitor v, int d) { v.visit(this, d); }
    public Temp accept(CodeVisitor v) { return v.visit(this); }
}
