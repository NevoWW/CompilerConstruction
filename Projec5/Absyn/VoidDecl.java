/* Copyright (C) 2007, Marquette University.  All rights reserved. */
package Absyn;
import java.util.LinkedList;

/**
 * Method Declaration structure.
 */

public class VoidDecl extends MethodDecl
{
    public String name;
    public LinkedList<VarDecl> locals;
    public LinkedList<Stmt> stmts;
 
    public VoidDecl(Type returnType,
		      boolean synced,
		      String name,
		      LinkedList<Formal> params,
		      LinkedList<VarDecl> locals,
		      LinkedList<Stmt> stmts,
		      Expr returnVal)
    {
		super(null, false, name, null, locals, stmts, null);
		this.name = name;
		this.locals = locals;
		this.stmts = stmts;
    }

    /** Visitor pattern dispatch. */
    public void accept(Visitor v) {v.visit(this); }
}
