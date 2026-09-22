/* Copyright (C) 2007, Marquette University.  All rights reserved. */
package Absyn;
import java.util.LinkedList;

/**
 * Method Declaration structure.
 */

public class ThreadDecl extends ClassDecl
{
    public String name;
    public String parent;
    public LinkedList<VarDecl> fields;
    public LinkedList<MethodDecl> methods;

    public ThreadDecl(String name, String parent, LinkedList<VarDecl> fields, LinkedList<MethodDecl> methods)
    {
		super(name, parent, fields, methods);
		this.name = name;
		this.fields = fields;
		this.methods = methods;
    }

    /** Visitor pattern dispatch. */
    public void accept(Visitor v) {v.visit(this); }
}
