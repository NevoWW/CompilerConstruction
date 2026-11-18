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

    public abstract java.util.LinkedList<Exp> kids();
    public abstract Exp build(java.util.LinkedList<Exp> kids);

    public abstract void accept(IntVisitor v, int d);

    public abstract Temp accept(CodeVisitor v);

}
