package Types;
import Absyn.*;
import Symbol.*;

public class TypesPrint implements TypesTypeVisitor
{
	private final Table typeEnv;

    public TypesPrint(Table typeEnv) {this.typeEnv = typeEnv; }
    public Types.Type visit(ArrayType ast) {
        return  new ARRAY(ast.base.accept(this));
    }

    public Types.Type visit(IdentifierType ast) {
        Symbol key = Symbol.symbol(ast.id);
        CLASS c = (CLASS)typeEnv.get(key);
        if(c == null){
            return new VOID();
        }
        return c.instance;
    }

    public Types.Type visit(IntegerType ast) {
        return new INT();
    }
    public Types.Type visit(BooleanType ast) {
        return new BOOLEAN();
    }


    public Types.Type visit(TrueExpr ast) {
        return new BOOLEAN();
    }
    public Types.Type visit(FalseExpr ast) {
        return new BOOLEAN();
    }
    public Types.Type visit(AndExpr ast) {
        return new BOOLEAN();
    }
    public Types.Type visit(OrExpr ast) {
        return new BOOLEAN();
    }
    
    public Types.Type visit(ThisExpr ast) {
        return new VOID();
    }
    public Types.Type visit(ArrayExpr ast) {
        return new VOID();
    }
    public Types.Type visit(FieldExpr ast) {
        return new VOID();
    }
    public Types.Type visit(CallExpr ast) {
        return new VOID();
    }
    public Types.Type visit(AssignStmt ast) {
        Type leftType = ast.lhs.accept(this);
        Type rightType = ast.rhs.accept(this);
        if (!rightType.coerceTo(leftType)){
            System.out.println("Incompatible type");
            Semant.Main.errorCount++;
            return new VOID();
        }else{
            return new VOID();
        }
    }
    public Types.Type visit(WhileStmt ast) {
        return new VOID();
    }
    public Types.Type visit(IfStmt ast) {
        return new VOID();
    }
    public Types.Type visit(BlockStmt ast) {
        return new VOID();
    }
    public Types.Type visit(Program ast) {
        return new VOID();
    }
    public Types.Type visit(MethodDecl ast) {
        return new VOID();
    }
    public Types.Type visit(Formal ast) {
        return new VOID();
    }
    public Types.Type visit(VarDecl ast) {
        return new VOID();
    }
    public Types.Type visit(NewObjectExpr ast) {
        return new VOID();
    }
    public Types.Type visit(NewArrayExpr ast) {
        return new VOID();
    }
    public Types.Type visit(XinuCallExpr ast) {
        return new VOID();
    }
    public Types.Type visit(XinuCallStmt ast) {
        return new VOID();
    }
    public Types.Type visit(NotEqExpr ast) {
        return new VOID();
    }
    public Types.Type visit(VoidDecl ast) {
        return new VOID();
    }
    public Types.Type visit(ThreadDecl ast) {
        return new VOID();
    }
    public Types.Type visit(NotExpr ast) {
        return new VOID();
    }
    public Types.Type visit(NegExpr ast) {
        return new VOID();
    }
    public Types.Type visit(AddExpr ast) {
        return new VOID();
    }
    public Types.Type visit(SubExpr ast) {
        return new VOID();
    }
    public Types.Type visit(MulExpr ast) {
        return new VOID();
    }
    public Types.Type visit(DivExpr ast) {
        return new VOID();
    }
    public Types.Type visit(EqualExpr ast) {
        return new VOID();
    }
    public Types.Type visit(GreaterExpr ast) {
        return new VOID();
    }
    public Types.Type visit(LesserExpr ast) {
        return new VOID();
    }
    public Types.Type visit(IdentifierExpr ast) {
        return new VOID();
    }
    public Types.Type visit(IntegerLiteral ast) {
        return new INT();
    }
    public Types.Type visit(StringLiteral ast) {
        return new STRING();
    }
    public Types.Type visit(NullExpr ast) {
        return new NIL();
    }
    public Types.Type visit(AssignableExpr ast) {
        return new VOID();
    }
    public Types.Type visit(ClassDecl ast) {
        return new VOID();
    }
    public Types.Type visit(java.util.AbstractList<Visitable> list) {
        return new VOID();
    }
}
