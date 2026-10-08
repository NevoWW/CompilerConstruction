/* Copyright (C) 2013, Marquette University.  All rights reserved. */
package Absyn;

/**
 * Xinu Statements.
 */

public class WhileStmt extends Stmt
{
    public Expr test;
    public Stmt body;
    public WhileStmt(Expr test, Stmt body)
    {
		this.test = test;
		this.body   = body;
    }

    /** Visitor pattern dispatch. */
    public void accept(Visitor v) {v.visit(this); }
}
