package Riscv;

import Frame.Frame;

public class Codegen implements Tree.CodeVisitor{
    Frame frame;
    public Codegen(Frame f) {frame=f;}
    private Assem.Instr ilist=null, last=null;
    private void emit(Assem.Instr inst) {
        if (last!=null)
            last = last.tail = new Assem.Instr(inst,null);
        else last = ilist = new Assem.Instr(inst,null);
    }
    
    
    void munchStm(Tree.Stm s) {  }
    Temp.Temp munchExp(Tree.Exp s) {  }

    Assem.Instr codegen(Tree.Stm s) {
        Assem.InstrList l;
        munchStm(s);
        l=ilist;
        ilist=last=null;
        return l;
    }
    
}
