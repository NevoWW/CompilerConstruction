/* Copyright (C) 2007, Marquette University.  All rights reserved. */
package Absyn;

/**
 * Expression abstract class.
 */

public class FalseExpr extends Expr
{
    /** Visitor pattern dispatch. */
public void accept(Visitor v) {v.visit(this); }}
