package Tree;

import Temp.Temp;

public interface CodeVisitor {
     public void visit(SEQ n);
    public void visit(LABEL n);
    public void visit(JUMP n);
    public void visit(CJUMP n);
    public void visit(MOVE n);
    public void visit(EXP n);
    public void visit(BINOP n);
    public void visit(MEM n);
    public void visit(TEMP n);
    public void visit(ESEQ n);
    public void visit(NAME n);
    public void visit(CONST n);
    public void visit(CALL n);
}
