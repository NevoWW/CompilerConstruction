/* Copyright (C) 1997-2003, Purdue Research Foundation of Purdue University.
 * All rights reserved.  */
package Tree;
import Temp.Temp;
import java.util.LinkedList;

/**
 * A temporary t.
 */

public class TEMP extends Exp 
{
    public Temp temp;
    public TEMP(Temp t) 
	{ 
		temp=t; 
		//System.out.println("\n Call to TEMP : "+t.toString());
	}

	public java.util.LinkedList<Exp> kids() {
        java.util.LinkedList<Exp> kids = new java.util.LinkedList<Exp>();
        return kids;
    }

    public Exp build(java.util.LinkedList<Exp> kids) {
        return this;
    }
	
    public void accept(IntVisitor v, int d) { v.visit(this, d); }
	public Temp accept(CodeVisitor v) { return v.visit(this); }
}
