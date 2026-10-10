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

    public Types.Type visit(IntegerType ast) {return new INT();}
    public Types.Type visit(BooleanType ast) {return new BOOLEAN();}
    public Types.Type visit(TrueExpr ast) {return new BOOLEAN();}
    public Types.Type visit(FalseExpr ast) {return new BOOLEAN();}
    public Types.Type visit(IntegerLiteral ast) {return new INT();}
    public Types.Type visit(StringLiteral ast) {return new STRING();}
    public Types.Type visit(IdentifierExpr ast) {return new STRING();}
    public Types.Type visit(NullExpr ast) {return new NIL();}

    public Types.Type visit(AndExpr ast) {
        Type leftExpr = ast.Expr1.accept(this);
        Type rightExpr = ast.Expr2.accept(this);
        if(!leftExpr.coerceTo(rightExpr) || !rightExpr.coerceTo(leftExpr)){
            System.out.println("Incompatible types: " + leftExpr +" required, but "+ rightExpr + " found");
            Semant.Main.errorCount++;
        }
        return new BOOLEAN();
    }
    public Types.Type visit(OrExpr ast) {
        Type leftExpr = ast.Expr1.accept(this);
        Type rightExpr = ast.Expr2.accept(this);
        if(!leftExpr.coerceTo(rightExpr) || !rightExpr.coerceTo(leftExpr)){
            System.out.println("Incompatible types: " + leftExpr +" required, but "+ rightExpr + " found");
            Semant.Main.errorCount++;
        }
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
            System.out.println("Incompatible types: " + leftType +" required, but "+ rightType + " found");
            Semant.Main.errorCount++;
            
        }
        return new VOID();
    }
    public Types.Type visit(WhileStmt ast) {
        Type test = ast.test.accept(this);
        Type check = new BOOLEAN();
        if(!test.coerceTo(check)){
            System.out.println("Incompatible types: " + check +" required, but "+ test + " found");
            Semant.Main.errorCount++;
        }
        typeEnv.beginScope();
        
        ast.body.accept(this);
        typeEnv.endScope();
        return new VOID();
    }
    public Types.Type visit(IfStmt ast) {
        Type ifstmt = ast.test.accept(this);
        BOOLEAN check = new BOOLEAN();
        if(!ifstmt.coerceTo(check)){
            System.out.println("Incompatible types: " + check +" required, but "+ ifstmt + " found");
            Semant.Main.errorCount++;
        }
        typeEnv.beginScope();
        Type thenstmt = ast.thenStm.accept(this);
        typeEnv.endScope();

        typeEnv.beginScope();
        Type elsestmt = ast.elseStm.accept(this);
        typeEnv.endScope();

        return new VOID();
    }
    public Types.Type visit(BlockStmt ast) {
        for (Stmt s : ast.stmts) {
        s.accept(this);
        }
        return new VOID();
    }
    public Types.Type visit(Program ast) {
        return new VOID();
    }
    public Types.Type visit(MethodDecl ast) {
        typeEnv.beginScope();

        for (Formal f : ast.params) {
            f.accept(this);
        }
        for (VarDecl v : ast.locals) {
            v.accept(this);
        }
        for (Stmt s : ast.stmts) {
            s.accept(this);
        }
        Type returnType = ast.returnType.accept(this);
        Type returnValue = ast.returnVal.accept(this);
        if(!returnValue.coerceTo(returnType)){
            System.out.println("Incompatible types: " + returnType +" required, but "+ returnValue + " found");
            Semant.Main.errorCount++;
        }
        typeEnv.endScope();
        return new VOID();
    }
    public Types.Type visit(Formal ast) {
        Type type = ast.type.accept(this);
        
        typeEnv.put(Symbol.symbol(ast.name), type);
        return new VOID();
    }
    public Types.Type visit(VarDecl ast) {
        Type typeVar = ast.type.accept(this);
        
        if(ast.init != null){
            Type varExpr = ast.init.accept(this);
            if(!typeVar.coerceTo(varExpr) || !varExpr.coerceTo(typeVar)){
                System.out.println("Incompatible types: " + typeVar +" required, but "+ varExpr + " found");
                Semant.Main.errorCount++;
            
            }
        }
        
        typeEnv.put(Symbol.symbol(ast.name), typeVar);
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
        Type leftExpr = ast.Expr1.accept(this);
        Type rightExpr = ast.Expr2.accept(this);
        if(!leftExpr.coerceTo(rightExpr) || !rightExpr.coerceTo(leftExpr)){
            System.out.println("Incompatible types: " + leftExpr +" required, but "+ rightExpr + " found");
            Semant.Main.errorCount++;
        }
        return new BOOLEAN();
    }
    public Types.Type visit(VoidDecl ast) {
        return new VOID();
    }
    public Types.Type visit(ThreadDecl ast) {
        return new VOID();
    }
    public Types.Type visit(NotExpr ast) {
        Type expr = ast.Expr1.accept(this);
        return new BOOLEAN();
    }
    public Types.Type visit(NegExpr ast) {
        Type expr = ast.Expr1.accept(this);
        return new INT();
    }
    public Types.Type visit(AddExpr ast) {
        Type leftExpr = ast.Expr1.accept(this);
        Type rightExpr = ast.Expr2.accept(this);
        if(!leftExpr.coerceTo(rightExpr) || !rightExpr.coerceTo(leftExpr)){
            System.out.println("Incompatible types: " + leftExpr +" required, but "+ rightExpr + " found");
            Semant.Main.errorCount++;
        }
        return new INT();
    }
    public Types.Type visit(SubExpr ast) {
        Type leftExpr = ast.Expr1.accept(this);
        Type rightExpr = ast.Expr2.accept(this);
        if(!leftExpr.coerceTo(rightExpr) || !rightExpr.coerceTo(leftExpr)){
            System.out.println("Incompatible types: " + leftExpr +" required, but "+ rightExpr + " found");
            Semant.Main.errorCount++;
        }
        return new INT();
    }
    public Types.Type visit(MulExpr ast) {
        Type leftExpr = ast.Expr1.accept(this);
        Type rightExpr = ast.Expr2.accept(this);
        if(!leftExpr.coerceTo(rightExpr) || !rightExpr.coerceTo(leftExpr)){
            System.out.println("Incompatible types: " + leftExpr +" required, but "+ rightExpr + " found");
            Semant.Main.errorCount++;
        }
        return new INT();
        
    }
    public Types.Type visit(DivExpr ast) {
        Type leftExpr = ast.Expr1.accept(this);
        Type rightExpr = ast.Expr2.accept(this);
        if(!leftExpr.coerceTo(rightExpr) || !rightExpr.coerceTo(leftExpr)){
            System.out.println("Incompatible types: " + leftExpr +" required, but "+ rightExpr + " found");
            Semant.Main.errorCount++;
        }
        return new INT();
        
    }
    public Types.Type visit(EqualExpr ast) {
        Type leftExpr = ast.Expr1.accept(this);
        Type rightExpr = ast.Expr2.accept(this);
        if(!leftExpr.coerceTo(rightExpr) || !rightExpr.coerceTo(leftExpr)){
            System.out.println("Incompatible types: " + leftExpr +" required, but "+ rightExpr + " found");
            Semant.Main.errorCount++;
        }
        return new BOOLEAN();
    }
    public Types.Type visit(GreaterExpr ast) {
        Type leftExpr = ast.Expr1.accept(this);
        Type rightExpr = ast.Expr2.accept(this);
        if(!leftExpr.coerceTo(rightExpr) || !rightExpr.coerceTo(leftExpr)){
            System.out.println("Incompatible types: " + leftExpr +" required, but "+ rightExpr + " found");
            Semant.Main.errorCount++;
        }
        return new BOOLEAN();
    }
    public Types.Type visit(LesserExpr ast) {
        Type leftExpr = ast.Expr1.accept(this);
        Type rightExpr = ast.Expr2.accept(this);
        if(!leftExpr.coerceTo(rightExpr) || !rightExpr.coerceTo(leftExpr)){
            System.out.println("Incompatible types: " + leftExpr +" required, but "+ rightExpr + " found");
            Semant.Main.errorCount++;
        }
        return new BOOLEAN();
    }
    
    

    public Types.Type visit(AssignableExpr ast) {
        return new BOOLEAN();
    }
    public Types.Type visit(ClassDecl ast) {
        return new VOID();
    }
    public Types.Type visit(java.util.AbstractList<Visitable> list) {
        return new VOID();
    }
}
