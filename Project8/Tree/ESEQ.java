/* Copyright (C) 1997-2003, Purdue Research Foundation of Purdue University.
 * All rights reserved.  */
package Tree;
import Temp.Temp;
import Temp.Label;
import java.util.LinkedList;

/** 
 * Implements an expression sequence, statment is evaluated
 * for side effects and the expression is evaluated as result.
 */

public class ESEQ extends Exp 
{
    public Stm stm;
    public Exp exp;
    public ESEQ(Stm s, Exp e) { stm=s; exp=e; }

    public java.util.LinkedList<Exp> kids() {
        throw new Error("kids() not applicable to SEQ");
    }

    public Exp build(java.util.LinkedList<Exp> kids) {
        throw new Error("Build() not applicable to SEQ");
    }

    public void accept(IntVisitor v, int d) { v.visit(this, d); }
    public Temp accept(CodeVisitor v) { return v.visit(this); }
}
