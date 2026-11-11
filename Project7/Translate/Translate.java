/* Copyright (C) 1997-2003, Purdue Research Foundation of Purdue University.
 * All rights reserved.  */
package Translate;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import Temp.Label;
import Temp.Temp;
import Symbol.*;
import Types.CLASS;

/**
 * Implements translation of abstract syntax expressions
 * into intermediate trees.
 */

public class Translate implements Absyn.TreeVisitor 
{
    public Frame.Frame frame;
	
    public Translate(Frame.Frame frame) { this.frame = frame; }
	
	
    private static Tree.Exp CONST(int value)  { return new Tree.CONST(value); }
    private static Tree.Exp NAME(Label label) {	return new Tree.NAME(label); }
    private static Tree.Exp TEMP(Temp temp)   {	return new Tree.TEMP(temp); }
    private static Tree.Exp BINOP(int binop, Tree.Exp left, Tree.Exp right) 
    {   return new Tree.BINOP(binop, left, right);   }
    private static Tree.Exp MEM(Tree.Exp exp) {	return new Tree.MEM(exp); }
    private static Tree.Exp CALL(Tree.Exp func, List args) 
    {	return new Tree.CALL(func, args);   }
    private static Tree.Exp ESEQ(Tree.Stm stm, Tree.Exp exp)
    {
	if (stm == null) return exp;
	return new Tree.ESEQ(stm, exp);
    }
	
    private static Tree.Stm MOVE(Tree.Exp dst, Tree.Exp src) 
    {
	return new Tree.MOVE(dst, src);
    }
    private static Tree.Stm EXP(Tree.Exp exp) {	return new Tree.EXP(exp); }
    private static Tree.Stm JUMP(Label target) 
    {	return new Tree.JUMP(target);   }
    private static Tree.Stm CJUMP(int relop, Tree.Exp l, Tree.Exp r,
				  Label t, Label f)
    {	return new Tree.CJUMP(relop, l, r, t, f);   }
    private static Tree.Stm SEQ(Tree.Stm left, Tree.Stm right) 
    {
	if (left == null)
	    return right;
	if (right == null)
	    return left;
	return new Tree.SEQ(left, right);
    }
    private static Tree.Stm LABEL(Label label) 
    {
	return new Tree.LABEL(label);
    }
	
    private LinkedList<Frag> frags = new LinkedList<Frag>();
    public List<Frag> results() { return frags; }

    private HashMap<String, CLASS>  classEnv  = new HashMap<String, CLASS>();
    private HashMap<Symbol, Label>  methodEnv = new HashMap<Symbol, Label>();
    private HashMap<String, Label>  staticEnv = new HashMap<String, Label>();
    private HashMap<String, Label>  vtableEnv = new HashMap<String, Label>();
    private Table/*<Frame.Access> */     varEnv    = new Table/*<Frame.Access>*/();
	
    public Exp visit(Absyn.Program n)
    {
	// TODO
        for (Absyn.ClassDecl c : n.classes) {
        classEnv.put(c.name, c.checkType);

        Label vt = new Label(c.name + "__vtable");
        vtableEnv.put(c.name, vt);

        LinkedList<Label> entries = new LinkedList<Label>();
        for (Absyn.MethodDecl m : c.methods) {
            Label methodLabel = new Label(c.name + "__" + m.name);
            entries.add(methodLabel);
            methodEnv.put(Symbol.symbol(c.name + "__" + m.name), methodLabel);
        }
        frags.add(new DataFrag(frame.record(vt, entries)));
    }


        for (Absyn.ClassDecl c : n.classes) {
            c.accept(this);
        }
	return new Nx(null);
    }
    
    private String currentClass;
    public Exp visit(Absyn.ClassDecl c) 
    {
	// TODO
        currentClass = c.name;

        for (Absyn.MethodDecl m : c.methods)
            m.accept(this);
	return new Nx(null);
    }

    public Exp visit(Absyn.ThreadDecl c) 
    {
	// TODO
        currentClass = c.name;
        for (Absyn.MethodDecl m : c.methods)
            m.accept(this);
	return new Nx(null);
    }

    public Exp visit(Absyn.MethodDecl m) 
    {
	// TODO
        String full = currentClass + "__" + m.name;
        Label name = methodEnv.get(Symbol.symbol(full));
        // methodEnv.put(Symbol.symbol(m.name), name);

        Frame.Frame saved = frame;
        String frameName = m.name.equals("main") ? "main" : full;
        frame = saved.newFrame(Symbol.symbol(frameName));
        varEnv.put(Symbol.symbol("this"), frame.allocFormal());
        for (Absyn.Formal f : m.params) {
        f.accept(this);
        }

        Tree.Stm body = null;


        for (Absyn.VarDecl v : m.locals) {
            body = SEQ(body, v.accept(this).unNx());
        }


        for (Absyn.Stmt s : m.stmts) {
            body = SEQ(body, s.accept(this).unNx());
        }

        if (m.returnVal != null) {
            Tree.Exp ret = m.returnVal.accept(this).unEx();
            body = SEQ(body, MOVE(TEMP(frame.RV()), ret));
        }
        frags.add(new ProcFrag(body, frame));
        frame=saved;
        return new Nx(null);
    }

    public Exp visit(Absyn.VoidDecl m) 
    {
	// TODO
	return new Nx(null);
    }

    public Exp visit(Absyn.Formal f) 
    {
	// TODO
        Frame.Access a = frame.allocFormal();
        varEnv.put(Symbol.symbol(f.name), a);
	return new Nx(null);
    }

    public Exp visit(Absyn.VarDecl v) 
    {
	// TODO
        Frame.Access a = frame.allocLocal();
        varEnv.put(Symbol.symbol(v.name), a);
	// return new Nx(null);
        if (v.init != null) {
        return new Nx(
            MOVE(a.exp(frame), v.init.accept(this).unEx())
        );
    } else {
        return new Nx(
            MOVE(a.exp(frame), CONST(0))
        );
    }
    }
    

    public Exp visit(Absyn.BooleanType  t)   { return new Nx(null); }
    public Exp visit(Absyn.IntegerType  t)   { return new Nx(null); }
    public Exp visit(Absyn.ArrayType t)      { return new Nx(null); }
    public Exp visit(Absyn.IdentifierType t) { return new Nx(null); }

    public Exp visit(Absyn.AssignStmt s) 
    {
	// TODO
        Tree.Exp dst = s.lhs.accept(this).unEx();
        Tree.Exp src = s.rhs.accept(this).unEx();
        return new Nx(MOVE(dst, src));

    }
	
    public Exp visit(Absyn.BlockStmt b)
    {
	// TODO
    Tree.Stm result = null;
    for (Absyn.Stmt s : b.stmts) {
       result = SEQ(result, s.accept(this).unNx());
    }
	return new Nx(result);
    }

    public Exp visit(Absyn.IfStmt s)
    {
	// TODO
        Exp cond = s.test.accept(this);
        Exp t = s.thenStm.accept(this);
        Exp f = (s.elseStm == null) ? new Nx(null) : s.elseStm.accept(this);
        return new IfThenElseExp(cond, t, f);
    }

    public Exp visit(Absyn.WhileStmt s)
    {
	// TODO
        Exp cond = s.test.accept(this);
        Tree.Stm t = s.body.accept(this).unNx();
        Label test = new Label();
        Label done = new Label();
        Label body = new Label();
        return new Nx(
                SEQ(LABEL(test),
                SEQ(cond.unCx(body, done),
                SEQ(LABEL(body),
                SEQ(t,
                SEQ(JUMP(test), LABEL(done)))))));
    }

    private Exp Ex(int op, Absyn.BinOpExpr e) 
    {
        Exp e1 = e.e1.accept(this);
		Exp e2 = e.e2.accept(this);
		return new Ex(BINOP(op, e1.unEx(), e2.unEx()));
    }

    public Exp visit(Absyn.AddExpr e) {
    //  return Ex(Tree.BINOP.PLUS, e); 
        Exp left = e.Expr1.accept(this);
        Exp right = e.Expr2.accept(this);

        return new Ex(
            BINOP(Tree.BINOP.PLUS, left.unEx(), right.unEx())
        );
    }
    public Exp visit(Absyn.DivExpr e) {
        //  return Ex(Tree.BINOP.DIV, e);
        Exp left = e.Expr1.accept(this);
        Exp right = e.Expr2.accept(this);

        return new Ex(
            BINOP(Tree.BINOP.DIV, left.unEx(), right.unEx())
        );        
    }
    public Exp visit(Absyn.MulExpr e) {
        //  return Ex(Tree.BINOP.MUL, e);
        Exp left = e.Expr1.accept(this);
        Exp right = e.Expr2.accept(this);

        return new Ex(
            BINOP(Tree.BINOP.MUL, left.unEx(), right.unEx())
        );        
    }
    public Exp visit(Absyn.SubExpr e) { 
        //  return Ex(Tree.BINOP.MINUS, e);
        Exp left = e.Expr1.accept(this);
        Exp right = e.Expr2.accept(this);

        return new Ex(
            BINOP(Tree.BINOP.MINUS, left.unEx(), right.unEx())
        );        
    }

    public Exp visit(Absyn.NegExpr e)
    {
	return new Ex(BINOP(Tree.BINOP.MINUS, 
			    CONST(0), e.Expr1.accept(this).unEx()));
    }

    public Exp visit(Absyn.AndExpr e) 
    {
        Exp e1 = e.e1.accept(this);
        Exp e2 = e.e2.accept(this);
        // Short-circuit Boolean AND, with false == 0.
        return new IfThenElseExp(e1, e2, new Ex(CONST(0)));
    }

    public Exp visit(Absyn.OrExpr e)
    {
	// TODO
	    Exp e1 = e.e1.accept(this);
        Exp e2 = e.e2.accept(this);
        return new IfThenElseExp(e1, new Ex(CONST(1)), e2);
    }

    private Exp RelCx(int op, Absyn.BinOpExpr e)
    {
        Exp e1 = e.e1.accept(this);
	Exp e2 = e.e2.accept(this);
	return new RelCx(op, e1.unEx(), e2.unEx());
    }

    public Exp visit(Absyn.GreaterExpr e)  { 
        // return RelCx(Tree.CJUMP.GT, e); 
        Exp e1 = e.Expr1.accept(this);
        Exp e2 = e.Expr2.accept(this);
        return new RelCx(Tree.CJUMP.GT, e1.unEx(), e2.unEx());
    }
    public Exp visit(Absyn.LesserExpr e)   { 
        // return RelCx(Tree.CJUMP.LT, e); 
        Exp e1 = e.Expr1.accept(this);
        Exp e2 = e.Expr2.accept(this);
        return new RelCx(Tree.CJUMP.LT, e1.unEx(), e2.unEx());
    }
    public Exp visit(Absyn.EqualExpr e)    { 
        // return RelCx(Tree.CJUMP.EQ, e); 
        Exp e1 = e.Expr1.accept(this);
        Exp e2 = e.Expr2.accept(this);
        return new RelCx(Tree.CJUMP.EQ, e1.unEx(), e2.unEx());
    }
    public Exp visit(Absyn.NotEqExpr e)    { 
        // return RelCx(Tree.CJUMP.NE, e); 
        Exp e1 = e.Expr1.accept(this);
        Exp e2 = e.Expr2.accept(this);
        return new RelCx(Tree.CJUMP.NE, e1.unEx(), e2.unEx());
    }

    public Exp visit(Absyn.NotExpr e)
    {
        Exp exp = e.Expr1.accept(this);
        // return new IfThenElseExp(exp, new Ex(CONST(0)), new Ex(CONST(1)));
        return new Ex(BINOP(Tree.BINOP.BITXOR, exp.unEx(), CONST(1)));
    }

    public Exp visit(Absyn.NewArrayExpr e) 
    {
	// TODO
	return new Nx(null);
    }

    public Exp visit(Absyn.XinuCallStmt s)
    {
	// TODO
        LinkedList<Tree.Exp> args = new LinkedList<Tree.Exp>();
        for (Absyn.Expr a : s.args) {
            args.add(a.accept(this).unEx());
        }
        return new Nx(EXP(CALL(NAME(new Label("_" + s.method)), args)));
    }

    public Exp visit(Absyn.XinuCallExpr e)
    {
	// TODO
	LinkedList<Tree.Exp> args = new LinkedList<Tree.Exp>();
        for (Absyn.Expr a : e.args) {
            args.add(a.accept(this).unEx());
        }
        return new Ex(CALL(NAME(new Label("_" + e.method)), args));
    }

    public Exp visit(Absyn.CallExpr e) 
    {
	// TODO
        Tree.Exp obj = e.target.accept(this).unEx();
        Label ok = new Label();
        Temp r = new Temp();
    	LinkedList<Tree.Exp> args = new LinkedList<Tree.Exp>();
        args.add(TEMP(r));
	    for (Absyn.Expr a : e.args) {
            args.add(a.accept(this).unEx());
        }

        int index = e.typeIndex;
        int w = frame.wordSize();
        Tree.Exp vtable = MEM(BINOP(Tree.BINOP.PLUS, TEMP(r), CONST(-w)));
        Tree.Exp fn = MEM(BINOP(Tree.BINOP.PLUS, vtable, CONST(index * w)));
        // return new Ex(CALL(fn, args));
        return new Ex(ESEQ(
        SEQ(MOVE(TEMP(r), obj),
        SEQ(CJUMP(Tree.CJUMP.EQ, TEMP(r), CONST(0), frame.badPtr(), ok),
            LABEL(ok))),
        CALL(fn, args)));
    }

    public Exp visit(Absyn.FalseExpr e)
    {   return new Ex(CONST(0));  }

    public Exp visit(Absyn.IntegerLiteral e)    
    {   return new Ex(CONST(e.value));   }

    public Exp visit(Absyn.NullExpr e)
    {   return new Ex(CONST(0));   }

    public Exp visit (Absyn.NewObjectExpr e) 
    {
    	// TODO
    String cname = ((Types.OBJECT) e.type2).myClass.name;
    CLASS cl = classEnv.get(cname);
    int numFields = 0;
    for (Types.FIELD f : cl.instance.fields) numFields++;

    LinkedList<Tree.Exp> args = new LinkedList<Tree.Exp>();
    args.add(CONST(numFields));
    args.add(NAME(vtableEnv.get(cname)));
    return new Ex(CALL(NAME(new Label("_new")), args));
    }
    
    private HashMap<String, Label> strings = new HashMap<String, Label>();

    public Exp visit(Absyn.StringLiteral e) 
    {
	String result = e.value.intern();
	Label lab = strings.get(result);
	if (lab != null) return new Ex(NAME(lab));
	lab = new Label();
	strings.put(result, lab);
	
	DataFrag frag = new DataFrag(frame.string(lab, result.intern()));
	frags.add(frag);
	return new Ex(NAME(lab));
    }

    public Exp visit(Absyn.ThisExpr e) 
    {
    	// TODO
	     Frame.Access a = (Frame.Access) varEnv.get(Symbol.symbol("this"));  // check e's field name
        return new Ex(a.exp(frame));
    }
    
    public Exp visit(Absyn.TrueExpr e)
    {   return new Ex(CONST(1));   }

    public Exp visit(Absyn.FieldExpr e) 
    {
    	int index		= e.typeIndex;
        Tree.Exp exp	= e.target.accept(this).unEx();
        Label	bad		= frame.badPtr();
        Label	ok  	= new Label();
        Temp result	= new Temp();
        return new Ex(ESEQ(SEQ(MOVE(TEMP(result), exp),
            CJUMP(Tree.CJUMP.EQ, TEMP(result), CONST(0),bad,ok)),
            ESEQ(LABEL(ok), MEM(BINOP(Tree.BINOP.PLUS, TEMP(result), CONST(index * frame.wordSize()))))));

    }

    public Exp visit(Absyn.IdentifierExpr e) 
    {
    	// TODO
	     Frame.Access a = (Frame.Access) varEnv.get(Symbol.symbol(e.id));  // check e's field name
        return new Ex(a.exp(frame));
    }

    public Exp visit(Absyn.ArrayExpr e)
    {
    	// TODO
	return new Nx(null);
    }
}
