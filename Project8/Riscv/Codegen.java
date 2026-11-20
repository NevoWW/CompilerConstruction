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

    private static boolean isConst12(int v) {
        return v >= -2048 && v <= 2047;
    }

    private static boolean isPow2(int v) {
        if (v <= 0) return false;
        while (v % 2 == 0) v /= 2;
        return v == 1;
    }

    private static int log2(int v) {
        int k = 0;
        while (v > 1) {
            v /= 2;
            k++;
        }
        return k;
    }

    public Temp visit(TEMP n) {
        return n.temp;
    }

    public Temp visit(CONST n){
        if(n.value == 0 ){
            return RiscvFrame.ZERO;
        }

        Temp t = new Temp();
        emit(new Assem.OPER("li Rd, " + n.value, L(t), null, null));
        return t;
    }

    public Temp visit(NAME n){
        Temp t = new Temp();
        emit(new Assem.OPER("la Rd, " + n.label, L(t), null, null));
        return t;
    }

    public Temp visit(BINOP n){
        Exp left = n.left, right = n.right;
        boolean rc = n.left instanceof CONST;
        boolean lc = n.right instanceof CONST;
        String op;
        switch (n.binop) {
            case BINOP.PLUS:
                if (rc && isConst12(((CONST)right).value)) return immOp("addi", left, ((CONST) right).value);
                if (lc && isConst12(((CONST)left).value)) return immOp("addi", right, ((CONST) left).value);
                return regOp("add", left, right);

            case BINOP.MINUS: 
                if (rc && isConst12(-((CONST)right).value)) return immOp("addi", left, -((CONST) right).value);
                return regOp("sub", left, right);
            case BINOP.MUL:   
                if (rc && isPow2(((CONST)right).value)) return immOp("sll", left, log2(((CONST) right).value));
                if (lc && isPow2(((CONST)left).value)) return immOp("sll", right, log2(((CONST) left).value));
                return regOp("mul", left, right);
            case BINOP.DIV:   
                if (rc && isPow2(((CONST)right).value)) return immOp("sra", left, log2(((CONST) right).value));
                return regOp("div", left, right);
            case BINOP.AND:  
                if (rc && isConst12(((CONST)right).value)) return immOp("andi", left, ((CONST) right).value);
                if (lc && isConst12(((CONST)left).value)) return immOp("andi", right, ((CONST) left).value);
                return regOp("and", left, right);
            case BINOP.OR:    
                if (rc && isConst12(((CONST)right).value)) return immOp("ori", left, ((CONST) right).value);
                if (lc && isConst12(((CONST)left).value)) return immOp("ori", right, ((CONST) left).value);
                return regOp("or", left, right);
            case BINOP.LSHIFT:  
                if (rc && isConst12(((CONST)right).value)) return immOp("sll", left, ((CONST) right).value);
                return regOp("sll", left, right);
            case BINOP.RSHIFT:  
                if (rc && isConst12(((CONST)right).value)) return immOp("srl", left, ((CONST) right).value);
                return regOp("srl", left, right);
            case BINOP.ARSHIFT: 
                if (rc && isConst12(((CONST)right).value)) return immOp("sra", left, ((CONST) right).value);
                return regOp("sra", left, right);
            case BINOP.BITXOR:  
                if (rc && isConst12(((CONST)right).value)) return immOp("xori", left, ((CONST) right).value);
                if (lc && isConst12(((CONST)left).value)) return immOp("xori", right, ((CONST) left).value);
                return regOp("xor", left, right);
            default: throw new Error("bad binop");
        }

    }

    private Temp regOp(String op, Exp left, Exp right) {
        Temp l = munchExp(left);
        Temp r = munchExp(right);
        Temp t = new Temp();
        emit(new Assem.OPER(op + " Rd, Rs1, Rs2", L(t), L(l, r), null));
        return t;
    }

    private Temp immOp(String op, Exp e, int imm) {
        Temp s = munchExp(e);
        Temp t = new Temp();
        emit(new Assem.OPER(op + " Rd, Rs, " + imm, L(t), L(s), null));
        return t;
    }

    public Temp visit(MEM n){
        Temp t = new Temp();

        if(n.exp instanceof BINOP && ((BINOP) n.exp).binop == BINOP.PLUS){
            BINOP binop = (BINOP) n.exp;
            if(binop.left instanceof CONST && isConst12(((CONST) binop.left).value)){
                Temp base = munchExp(binop.right);
                int offset = ((CONST) binop.left).value;
                emit(new Assem.OPER("lw Rd, " + offset + "(Rs)", L(t), L(base), null));
                return t;
                
            }

            if(binop.right instanceof CONST && isConst12(((CONST) binop.right).value)){
                Temp base = munchExp(binop.left);
                int offset = ((CONST) binop.right).value;
                emit(new Assem.OPER("lw Rd, " + offset + "(Rs)", L(t), L(base), null));
                return t;
                
            }
        }

        Temp exp = munchExp(n.exp);
        emit(new Assem.OPER("lw Rd, 0(Rs)", L(t), L(exp), null));
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


        if(n.dst instanceof MEM ){
            MEM m = (MEM) n.dst;

            if(m.exp instanceof BINOP && ((BINOP) m.exp).binop == BINOP.PLUS){
                BINOP binop = (BINOP) m.exp;
                if(binop.left instanceof CONST && isConst12(((CONST) binop.left).value)){
                    int offset = ((CONST) binop.left).value;
                    Temp base = munchExp(binop.right);
                    Temp src = munchExp(n.src);
                    emit(new Assem.OPER("sw Rs, " + offset + "(Rd)", null, L(src, base), null));
                    return;
                    
                }

                if(binop.right instanceof CONST && isConst12(((CONST) binop.right).value)){
                    int offset = ((CONST) binop.right).value;
                    Temp base = munchExp(binop.left);
                    Temp src = munchExp(n.src);
                    emit(new Assem.OPER("sw Rs, " + offset + "(Rd)", null, L(src, base), null));
                    return;
                    
                }
            }


            Temp addr = munchExp(m.exp);
            Temp src = munchExp(n.src);
            emit(new Assem.OPER("sw Rs, 0(Rd)", null, L(src, addr), null));
            return;
        }

        Temp src = munchExp(n.src);
        Temp dst = munchExp(n.dst);
        emit(new Assem.OPER("mv Rd, Rs", L(dst), L(src), null));
    }

    public void visit(EXP n){
        munchExp(n.exp);
    }

    public void visit(JUMP n){
        if (n.exp instanceof NAME) {
            NAME name = (NAME) n.exp;
            emit(new Assem.OPER("j " + name.label, null, null, labels(name.label)));
        } else {
            Temp t = munchExp(n.exp);
            emit(new Assem.OPER("jalr Rs", null, L(t), n.targets));
        }
    }
    

    public void visit(CJUMP n){
        Temp l = munchExp(n.left);
        Temp r = munchExp(n.right);
        String op;
        switch (n.relop) {
            case CJUMP.EQ: op = "beq"; break;
            case CJUMP.NE: op = "bne"; break;
            case CJUMP.LT: op = "blt"; break;
            case CJUMP.GT: op = "bgt"; break;
            case CJUMP.LE: op = "ble"; break;
            case CJUMP.GE: op = "bge"; break;
            default: throw new Error("unsupported relop in CJUMP: " + n.relop);
        }
        emit(new Assem.OPER(op + "Rs1, Rs2, " + n.iftrue, null, L(l,r), labels(n.iftrue, n.iffalse)));
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
