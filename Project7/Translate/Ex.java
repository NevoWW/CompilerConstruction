/* Copyright (C) 1997-2003, Purdue Research Foundation of Purdue University.
 * All rights reserved.  */
package Translate;
import Temp.Label;
import Tree.*;

/**
 * Stands for an "expression", represented as a Tree.Exp.
 */

public class Ex extends Exp {
    Tree.Exp exp;
    Ex(Tree.Exp e)  { exp = e; }
    Tree.Exp unEx() { return exp; }
    Tree.Stm unNx() {
	// TODO: If exp is CONST, return null, else EXP
	if (exp instanceof CONST) {
        return null;
    }else{
        return new EXP(exp);
    }
	
    }
    Tree.Stm unCx(Label t, Label f) {
	// TODO: if the exp is a constant, emit JUMP statement,
	// else CJUMP( exp != false )
        if (exp instanceof  CONST) {
            CONST val = (CONST)exp;
            if(val.value != 0)
            {
                return new JUMP(t);
            }
            else
            {
                return new JUMP(f);
            }  
        }
        return new CJUMP(CJUMP.NE,exp,new CONST(0),t,f);

    }

}
