package Types;
import Absyn.*;

public class TypesPrint implements TypesTypeVisitor
{
    public TypesPrint() { }
    public Types.Type visit(ArrayType ast) {
        Types.Type arr = new Types.ARRAY(ast.base.accept(this));
        return arr;
    }
    public Types.Type visit(IdentifierType ast) {
        Types.Type id = new Types.STRING();
        return id;
    }
    public Types.Type visit(IntegerType ast) {
        Types.Type intType = new Types.INT();
        return intType;
    }
    public Types.Type visit(BooleanType ast) {
        Types.Type boolType = new Types.BOOLEAN();
        return boolType;
    }
    
}
