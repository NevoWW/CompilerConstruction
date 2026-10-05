/* Copyright (C) 2007, Marquette University.  All rights reserved. */
package Absyn;

/**
 * Expression abstract class.
 */

public class NullExpr extends Expr
{
    /** Visitor pattern dispatch. */
public void accept(Visitor v) {v.visit(this); }}
