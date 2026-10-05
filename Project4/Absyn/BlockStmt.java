/* Copyright (C) 2013, Marquette University.  All rights reserved. */
package Absyn;
import java.util.LinkedList;

/**
 * Xinu Statements.
 */

public class BlockStmt extends Stmt
{
    public LinkedList<Stmt> stmts;
    public BlockStmt(LinkedList<Stmt> stmts)
    {
		this.stmts = stmts;
    }

    /** Visitor pattern dispatch. */
    public void accept(Visitor v) {v.visit(this); }
}
