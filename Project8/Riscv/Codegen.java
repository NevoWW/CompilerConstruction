package Riscv;

import Frame.Frame;
import Assem.Instr;
import java.util.LinkedList;
import Temp.*;
import Tree.*;

public class Codegen implements Tree.CodeVisitor{
    Frame frame;
    public Codegen(Frame f) {frame=f;}

    private LinkedList<Assem.Instr> ilist = new LinkedList<Assem.Instr>();
    private void emit(Assem.Instr inst) {ilist.add(inst);}

    void munchStm(Tree.Stm s) { s.accept(this); }
    Temp munchExp(Tree.Exp s) { return s.accept(this); }

     public LinkedList<Instr> codegen(Tree.Stm s) {
        munchStm(s);
        LinkedList<Instr> l = ilist;
	    ilist = new LinkedList<>();
        return l;
    }

    private static Temp[] L() {
        return new Temp[0];
    }

   private static Temp[] L(Temp a) {
        Temp[] arr = new Temp[1];
        arr[0] = a;
        return arr;
    }

    private static Temp[] L(Temp a, Temp b) {
        Temp[] arr = new Temp[2];
        arr[0] = a;
        arr[1] = b;
        return arr;
    }

    private static LinkedList<Label> labels(Label a) {
        LinkedList<Label> l = new LinkedList<>();
        l.add(a);
        return l;
    }

    private static LinkedList<Label> labels(Label a, Label b) {
        LinkedList<Label> l = new LinkedList<>();
        l.add(a);
        l.add(b);
        return l;
    }

    public Temp visit(TEMP n) {
        return n.temp;
    }

    public Temp visit(CONST n){
        if(n.value == 0 ){
            return RiscvFrame.ZERO;
        }

        Temp t = new Temp();
        
        if(n.value >= -32768 && n.value <= 32767){
            emit(new Assem.OPER("addi 'd0, x0, " + n.value, L(t), null, null));
            return t;
        }

        emit(new Assem.OPER("li 'd0, " + n.value, L(t), null, null));
        return t;
    }

    public Temp visit(NAME n){
        Temp t = new Temp();
        emit(new Assem.OPER("la 'd0, " + n.label, L(t), null, null));
        return t;
    }

    public Temp visit(BINOP n){
        Temp t = new Temp();
        Temp l = munchExp(n.left);
        Temp r = munchExp(n.right);
        String op;
        switch (n.binop) {
            case BINOP.PLUS:  op = "add"; break;
            case BINOP.MINUS: op = "sub"; break;
            case BINOP.MUL:   op = "mul"; break;
            case BINOP.DIV:   op = "div"; break;
            case BINOP.AND:   op = "and"; break;
            case BINOP.OR:    op = "or";  break;
            case BINOP.LSHIFT:  op = "sll"; break;
            case BINOP.RSHIFT:  op = "srl"; break;
            case BINOP.ARSHIFT: op = "sra"; break;
            case BINOP.BITAND:  op = "and"; break;
            case BINOP.BITOR:   op = "or";  break;
            case BINOP.BITXOR:  op = "xor"; break;
            default: throw new Error("bad binop");
        }
        emit(new Assem.OPER(op + " 'd0, 's0, 's1", L(t), L(l, r), null));
        return t;

    }

    public Temp visit(MEM n){
        Temp t = new Temp();
        Temp exp = munchExp(n.exp);
        emit(new Assem.OPER("lw 'd0, 0('s0)", L(t), L(exp), null));
        return t;

    }

    public Temp visit(CALL n){
        return null;
    }

    public Temp visit(ESEQ n){
        munchStm(n.stm);
        return munchExp(n.exp);
    }

    public void visit(MOVE n){
        Temp src = munchExp(n.src);
        Temp dst = munchExp(n.dst);
        emit(new Assem.OPER("move 'd0, 's0", L(dst), L(src), null));
    }

    public void visit(EXP n){
        munchExp(n.exp);
    }

    public void visit(JUMP n){
        if (n.exp instanceof NAME) {
            NAME name = (NAME) n.exp;
            emit(new Assem.OPER("b " + name.label, null, null, labels(name.label)));
        } else {
            Temp t = munchExp(n.exp);
            emit(new Assem.OPER("jr 'j0", null, L(t), n.targets));
        }
    }
    

    public void visit(CJUMP n){
        return;
    }

    public void visit(SEQ n){
        munchStm(n.left);
        munchStm(n.right);
    }

    public void visit(LABEL n){
        emit(new Assem.LABEL(n.label + ":", n.label));
    }

}
