/* Copyright (C) 1997-2003, Purdue Research Foundation of Purdue University.
 * All rights reserved.  */
package Tree;
import java.util.LinkedList;
import Temp.Temp;

/**
 * Abstract class that represents an expression in the Tree language.
 */

public abstract class Exp implements Hospitable
{

    public java.util.LinkedList<Exp> kids() { throw new Error("kids() not implemented"); }
    public Exp build(java.util.LinkedList<Exp> kids) { throw new Error("build() not implemented"); }

    public abstract void accept(IntVisitor v, int d);

}
