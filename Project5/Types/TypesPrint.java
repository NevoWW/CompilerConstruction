package Types;
import Absyn.*;
import Symbol.*;
import java.io.PrintWriter;
public class TypesPrint implements TypesTypeVisitor
{
	private final Table typeEnv;
    private CLASS currentClass = null;

    public TypesPrint(Table typeEnv) {this.typeEnv = typeEnv; }

    
   private void printNode(Absyn node) {  
        PrintWriter writer = new PrintWriter(System.out);
	    PrintVisitor pv =	new PrintVisitor(writer);
        int temp = pv.indentCount;
        pv.indentCount = 9;
        node.accept(pv);
        writer.flush();  
        pv.indentCount = temp;
        System.out.println();
    }

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
    public Types.Type visit(NullExpr ast) {return new NIL();}

    public Types.Type visit(AndExpr ast) {
        Type leftExpr = ast.Expr1.accept(this);
        Type rightExpr = ast.Expr2.accept(this);
        Type check = new BOOLEAN();
        if(!leftExpr.coerceTo(check) || !rightExpr.coerceTo(check)){
            System.out.println("ERROR incompatible types: " + leftExpr +" required, but "+ rightExpr + " found");
            printNode(ast);
            Semant.Main.errorCount++;
        }

        ast.type = new BOOLEAN();
        return ast.type;
    }
    public Types.Type visit(OrExpr ast) {
        Type leftExpr = ast.Expr1.accept(this);
        Type rightExpr = ast.Expr2.accept(this);
        Type check = new BOOLEAN();
        if(!leftExpr.coerceTo(check) || !rightExpr.coerceTo(check)){
            System.out.println("ERROR incompatible types: " + leftExpr +" required, but "+ rightExpr + " found");
            printNode(ast);
            Semant.Main.errorCount++;
        }
        ast.type = new BOOLEAN();
        return ast.type;
    }
    
    public Types.Type visit(ArrayExpr ast) {
        Type targetType = ast.target.accept(this);
        Type indexType = ast.index.accept(this);
        Type check = new INT();

        if (!indexType.coerceTo(check)) {
            System.out.println("ERROR incompatible types: " + check + " required, but " + indexType + " found:");
            printNode(ast);
            Semant.Main.errorCount++;
        }

        if (!(targetType instanceof ARRAY)) {
            System.out.println("ERROR array required, but " + targetType + " found");
            Semant.Main.errorCount++;
            ast.type = new VOID();
            return ast.type;
        }

        ast.type = ((ARRAY) targetType).element;
        return ast.type;
    }

    public Types.Type visit(ThisExpr ast){
        if (currentClass == null) {
            System.out.println("ERROR: this used outside a class");
            Semant.Main.errorCount++;
            ast.type = new VOID();
            return ast.type;
        }

        ast.type = currentClass.instance;
        return ast.type;
    }

    public Types.Type visit(FieldExpr ast) {
        Type targetType = ast.target.accept(this);

        if (targetType instanceof ARRAY && ast.field.equals("length")) {
            ast.typeIndex = -1;
            ast.type = new INT();
            return ast.type;
        }

        if(!(targetType instanceof OBJECT)) {
            System.out.println("ERROR target not object, type " + targetType);
            printNode(ast);
            ast.type = new VOID();
            return ast.type;
        }
        FIELD methodField = ((OBJECT) targetType).fields.get(ast.field);

        if (methodField == null) {
            System.out.println("ERROR: method not found: " + ast.field);
            Semant.Main.errorCount++;
            ast.type = new VOID();
            return ast.type;
        }

        ast.typeIndex = methodField.index;

        // if (!targetType.coerceTo(function.self)) {
        //     System.out.println("ERROR incompatible object type");
        //     Semant.Main.errorCount++;
        //     ast.type = new VOID();
        //     return ast.type;
        // }

        ast.type = methodField.type;
        return ast.type;
    }

    public Types.Type visit(CallExpr ast) {

        Type targetType = ast.target.accept(this);

        if(!(targetType instanceof OBJECT)){
            System.out.println("ERROR target not object, type " + targetType);
            printNode(ast);
            ast.type = new VOID();
            return ast.type;
        } 
        
        FIELD methodField = ((OBJECT) targetType).methods.get(ast.method);

        if (methodField == null || !(methodField.type instanceof FUNCTION)) {
            System.out.println("ERROR: method not found: " + ast.method);
            Semant.Main.errorCount++;
           ast.type = new VOID();
            return ast.type;
        }

        FUNCTION function = (FUNCTION) methodField.type;
        ast.typeIndex = methodField.index;

        if (!targetType.coerceTo(function.self)) {
        System.out.println("ERROR incompatible object type");
        Semant.Main.errorCount++;
        ast.type = new VOID();
        return ast.type;
    }

    // Skip the first formal ("this"), then check actual arguments.
    java.util.Iterator<FIELD> formals = function.formals.iterator();

    // if (formals.hasNext()) {
    //     formals.next();
    // }

    java.util.Iterator<Expr> args = ast.args.iterator();

    while (formals.hasNext() && args.hasNext()) {
        Type formalType = formals.next().type;
        Type argType = args.next().accept(this);

         if (!argType.coerceTo(formalType)) {
            System.out.println("ERROR incompatible type:");
            Semant.Main.errorCount++;
        }
    }
        if (formals.hasNext() || args.hasNext()) {
            System.out.print("ERROR mismatch in number of arguments:");
            printNode(ast);
            Semant.Main.errorCount++;
        }
        ast.type = function.result;
        return ast.type;

    }

    public Types.Type visit(AssignStmt ast) {
        Type leftType = ast.lhs.accept(this);
        Type rightType = ast.rhs.accept(this);
        if(leftType == null || rightType == null){
            ast.type = new NIL();
            return ast.type;
        }
        if (!rightType.coerceTo(leftType)){
            System.out.println("ERROR incompatible types: " + leftType +" required, but "+ rightType + " found:");
            printNode(ast);
            Semant.Main.errorCount++;
            
        }
        ast.type = new VOID();
        return ast.type;
    }

    public Types.Type visit(WhileStmt ast) {
        Type test = ast.test.accept(this);
        Type check = new BOOLEAN();
        if(!test.coerceTo(check)){
            System.out.println("ERROR incompatible types: " + check +" required, but "+ test + " found");
            printNode(ast);
            Semant.Main.errorCount++;
        }
        typeEnv.beginScope();
        
        ast.body.accept(this);
        typeEnv.endScope();

        ast.type = new VOID();
        return ast.type;
    }

    public Types.Type visit(IfStmt ast) {
        Type ifstmt = ast.test.accept(this);
        BOOLEAN check = new BOOLEAN();
        if(!ifstmt.coerceTo(check)){
            System.out.println("ERROR incompatible types: " + check +" required, but "+ ifstmt + " found:");
            printNode(ast);
            Semant.Main.errorCount++;
            ast.type = new VOID();
            return ast.type;
        }
        typeEnv.beginScope();
        Type thenstmt = ast.thenStm.accept(this);
        typeEnv.endScope();

        if(ast.elseStm == null){
            ast.type = new VOID();
            return ast.type;
        }

        typeEnv.beginScope();
        Type elsestmt = ast.elseStm.accept(this);
        typeEnv.endScope();

        ast.type = new VOID();
        return ast.type;
    }

    public Types.Type visit(BlockStmt ast) {
        for (Stmt s : ast.stmts) {
        s.accept(this);
        }
        ast.type = new VOID();
        return ast.type;
    }

    public Types.Type visit(Program ast) {
        ast.type = new VOID();
        return ast.type;
    }
    public Types.Type visit(MethodDecl ast) {
//         System.out.println("Checking method: " + ast.name);
//         System.out.println("AST return type: " + ast.returnType);
//         System.out.println("AST return expression: " + ast.returnVal);
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
        Type returnType;
        if (ast.returnType == null) {

            // System.out.println("Return type is null");
            Type returnValType = ast.returnVal.accept(this);
            returnType = returnValType;
        } else {
            returnType = ast.returnType.accept(this);
        }

        if (ast.returnVal != null){
            Type returnValue = ast.returnVal.accept(this);
            if(!returnValue.coerceTo(returnType)){
                System.out.println("ERROR incompatible types: " + returnType +" required, but "+ returnValue + " found");
                printNode(ast);
                Semant.Main.errorCount++;
            }
        }
        typeEnv.endScope();
        ast.type = returnType;
        return ast.type;
    }

    public Types.Type visit(Formal ast) {
        Type type = ast.type.accept(this);
        
        typeEnv.put(Symbol.symbol(ast.name), type);
        ast.typeOfFormal = type;
        return ast.typeOfFormal;
    }

    public Types.Type visit(VarDecl ast) {
        Type typeVar = ast.type.accept(this);
        
        if(ast.init != null){
            Type varExpr = ast.init.accept(this);
            if(!varExpr.coerceTo(typeVar)){
                System.out.println("ERROR incompatible types: " + typeVar +" required, but "+ varExpr + " found");
                printNode(ast);
                Semant.Main.errorCount++;
            
            }
        }
        
        typeEnv.put(Symbol.symbol(ast.name), typeVar);
        ast.typeInfo = typeVar;
        return ast.typeInfo;
    }

    public Types.Type visit(NewObjectExpr ast) {
        Type t = ast.type.accept(this);
        ast.type2 = t;
        return ast.type2;
    }

    public Types.Type visit(NewArrayExpr ast) {
        Type t = ast.type.accept(this);
        for (int i = 0; i < ast.dim.size(); i++) {
            t = new ARRAY(t);
        }

        // ast.type2 = (t instanceof ARRAY) ? t : new ARRAY(t);
        ast.type2 = t;
        return ast.type2;
    }
    public Types.Type visit(XinuCallExpr ast) {    

        CLASS xinuClass = (CLASS)typeEnv.get(Symbol.symbol("Xinu"));

        if (xinuClass == null) {
            System.out.println("ERROR: method not found: " + ast.method);
            Semant.Main.errorCount++;
           ast.type = new VOID();
            return ast.type;
        }

        FIELD methodField = xinuClass.methods.get(ast.method);
        
        if (methodField == null || !(methodField.type instanceof FUNCTION)) {
            System.out.println("ERROR: method not found: " + ast.method);
            Semant.Main.errorCount++;
           ast.type = new VOID();
            return ast.type;
        }

        FUNCTION function = (FUNCTION) methodField.type;

        java.util.Iterator<FIELD> formals = function.formals.iterator();
        java.util.Iterator<Expr> args = ast.args.iterator();

        while (formals.hasNext() && args.hasNext()) {
            Type formalType = formals.next().type;
            Type argType = args.next().accept(this);

            if (!argType.coerceTo(formalType)) {
                System.out.println("ERROR incompatible types: "+ argType +" required, but "+ formalType +" found:");
                printNode(ast);
                Semant.Main.errorCount++;
            }
        }

        if (formals.hasNext() || args.hasNext()) {
            System.out.print("ERROR mismatch in number of arguments:");
            // printNode(ast);
            Semant.Main.errorCount++;
        }
        ast.type = function.result;
        return ast.type;
    }
    public Types.Type visit(XinuCallStmt ast) {
        CLASS xinuClass = (CLASS)typeEnv.get(Symbol.symbol("Xinu"));

        if (xinuClass == null) {
            System.out.println("ERROR: method not found: " + ast.method);
            Semant.Main.errorCount++;
           ast.type = new VOID();
            return ast.type;
        }

        FIELD methodField = xinuClass.methods.get(ast.method);
        
        if (methodField == null || !(methodField.type instanceof FUNCTION)) {
            System.out.println("ERROR: method not found: " + ast.method);
            Semant.Main.errorCount++;
        }

        FUNCTION function = (FUNCTION) methodField.type;

        java.util.Iterator<FIELD> formals = function.formals.iterator();
        java.util.Iterator<Expr> args = ast.args.iterator();

        // java.util.Iterator<FIELD> debugFormals = function.formals.iterator();

        // while (debugFormals.hasNext()) {
        //     FIELD f = debugFormals.next();
        //     System.out.println("Formal name: " + f.name + ", type: " + f.type);
        // }

        while (formals.hasNext() && args.hasNext()) {
            Type formalType = formals.next().type;
            Type argType = args.next().accept(this);
        
            if (!argType.coerceTo(formalType)) {
                System.out.println("ERROR incompatible types: "+ argType +" required, but "+ formalType +" found:");
                printNode(ast);
                Semant.Main.errorCount++;
            }
        }

        if (formals.hasNext() || args.hasNext()) {
            System.out.print("ERROR mismatch in number of arguments:");
            printNode(ast);
            Semant.Main.errorCount++;
        }
        ast.type = new VOID();
        return ast.type;
    }
    public Types.Type visit(NotEqExpr ast) {
        Type leftExpr = ast.Expr1.accept(this);
        Type rightExpr = ast.Expr2.accept(this);
        if(!leftExpr.coerceTo(rightExpr) || !rightExpr.coerceTo(leftExpr)){
            System.out.println("ERROR incompatible types: " + leftExpr +" required, but "+ rightExpr + " found");
            printNode(ast);
            Semant.Main.errorCount++;
        }
        ast.type = new BOOLEAN();
        return ast.type;
    }
    public Types.Type visit(VoidDecl ast) {
        ast.type = new VOID();
        return ast.type;
    }
    public Types.Type visit(ThreadDecl ast) {
        ast.type = new VOID();
        return ast.type;
    }
    public Types.Type visit(NotExpr ast) {
        Type expr = ast.Expr1.accept(this);
        Type check = new BOOLEAN();
        if (!expr.coerceTo(check)) {
            System.out.println("ERROR operator ! cannot be applied to " + expr);
            Semant.Main.errorCount++;
        }
        ast.type = new BOOLEAN();
        return ast.type;
    }
    public Types.Type visit(NegExpr ast) {
        Type expr = ast.Expr1.accept(this);
        Type check = new INT();
        if (!expr.coerceTo(check)) {
            System.out.println("ERROR operator - cannot be applied to " + expr);
            Semant.Main.errorCount++;
        }
        ast.type = new INT();
        return ast.type;
    }
    public Types.Type visit(AddExpr ast) {
        Type leftExpr = ast.Expr1.accept(this);
        Type rightExpr = ast.Expr2.accept(this);
        Type check = new INT();
        if (!leftExpr.coerceTo(check) || !rightExpr.coerceTo(check)) {
            System.out.println("ERROR operator + cannot be applied to " + leftExpr + ", " + rightExpr + ":");
            printNode(ast);
            Semant.Main.errorCount++;
        }
        ast.type = new INT();
        return ast.type;
    }
    public Types.Type visit(SubExpr ast) {
        Type leftExpr = ast.Expr1.accept(this);
        Type rightExpr = ast.Expr2.accept(this);
        Type check = new INT();
        if(!leftExpr.coerceTo(check) || !rightExpr.coerceTo(check)){
            System.out.println("ERROR operator - cannot be applied to " + leftExpr + ", " + rightExpr + ":");
            printNode(ast);
            Semant.Main.errorCount++;
        }
        ast.type = new INT();
        return ast.type;
    }
    public Types.Type visit(MulExpr ast) {
        Type leftExpr = ast.Expr1.accept(this);
        Type rightExpr = ast.Expr2.accept(this);
        Type check = new INT();
        if(!leftExpr.coerceTo(check) || !rightExpr.coerceTo(check)){
            System.out.println("ERROR operator * cannot be applied to " + leftExpr + ", " + rightExpr + ":");
            printNode(ast);
            Semant.Main.errorCount++;
        }
        ast.type = new INT();
        return ast.type;
        
    }
    public Types.Type visit(DivExpr ast) {
        Type leftExpr = ast.Expr1.accept(this);
        Type rightExpr = ast.Expr2.accept(this);
        Type check = new INT();
        if(!leftExpr.coerceTo(check) || !rightExpr.coerceTo(check)){
            System.out.println("ERROR operator / cannot be applied to " + leftExpr + ", " + rightExpr + ":");
            printNode(ast);
            Semant.Main.errorCount++;
        }
        ast.type = new INT();
        return ast.type;
        
    }
    public Types.Type visit(EqualExpr ast) {
        Type leftExpr = ast.Expr1.accept(this);
        Type rightExpr = ast.Expr2.accept(this);
        if(!leftExpr.coerceTo(rightExpr) || !rightExpr.coerceTo(leftExpr)){
            System.out.println("ERROR operator == cannot be applied to " + leftExpr + ", " + rightExpr);
            Semant.Main.errorCount++;
        }
        ast.type = new BOOLEAN();
        return ast.type;
    }
    public Types.Type visit(GreaterExpr ast) {
        Type leftExpr = ast.Expr1.accept(this);
        Type rightExpr = ast.Expr2.accept(this);
        Type check = new INT();
        if(!leftExpr.coerceTo(check) || !rightExpr.coerceTo(check)){
            System.out.println("ERROR operator > cannot be applied to " + leftExpr + ", " + rightExpr);
            Semant.Main.errorCount++;
        }
        ast.type = new BOOLEAN();
        return ast.type;
    }
    public Types.Type visit(LesserExpr ast) {
        Type leftExpr = ast.Expr1.accept(this);
        Type rightExpr = ast.Expr2.accept(this);
        Type check = new INT();
        if(!leftExpr.coerceTo(check) || !rightExpr.coerceTo(check)){
            System.out.println("ERROR operator < cannot be applied to " + leftExpr + ", " + rightExpr);
            Semant.Main.errorCount++;
        }
        ast.type = new BOOLEAN();
        return ast.type;
    }
    
    

    public Types.Type visit(AssignableExpr ast) {
        return new VOID();
    }

    public Types.Type visit(IdentifierExpr ast) {
        Symbol key = Symbol.symbol(ast.id);
        Types.Type type = (Types.Type) typeEnv.get(key);

        if (type == null) {
            System.out.println("ERROR cannot resolve symbol " + ast.id);
            printNode(ast);
            Semant.Main.errorCount++;
            ast.type = new VOID();
            return ast.type;
        }

        ast.type = type;
        return ast.type;
    }

    public Types.Type visit(ClassDecl ast) {
        CLASS saved = currentClass;
        currentClass = (CLASS) typeEnv.get(Symbol.symbol(ast.name)); 
        typeEnv.beginScope();
        typeEnv.put(Symbol.symbol("this"), currentClass.instance);
        for (FIELD f : currentClass.instance.fields) { typeEnv.put(Symbol.symbol(f.name), f.type);}
        for (VarDecl field : ast.fields) field.accept(this);
        for (MethodDecl method : ast.methods) method.accept(this);
        typeEnv.endScope();
        currentClass = saved;
        return new VOID();
    }
    public Types.Type visit(java.util.AbstractList<Visitable> list) {
        return new VOID();
    }
}
