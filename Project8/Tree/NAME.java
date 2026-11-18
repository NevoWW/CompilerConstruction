/* Copyright (C) 1997-2003, Purdue Research Foundation of Purdue University.
 * All rights reserved.  */
package Tree;
import Temp.Temp;
import Temp.Label;
import java.util.LinkedList;

/**
 * The symbolic constant of the label.
 */

public class NAME extends Exp 
{
    public Label label;
    public NAME(Label label) 
	{ 
		//System.out.println("\n NAME is called : l :"+l);
		this.label = label; 
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
