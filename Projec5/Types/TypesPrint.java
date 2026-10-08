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

}
