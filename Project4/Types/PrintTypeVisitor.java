
/* Copyright (C) 2007, Marquette University.  All rights reserved. */
package Types;
import java.io.PrintWriter;

/**
 * Visitor prints AST in reparseable form.
 */

public class PrintTypeVisitor implements TypeVisitor
{
    PrintWriter out;
    public int indentCount = 0;

    public PrintTypeVisitor(PrintWriter out)
    {
		this.out = out;
    }

    public PrintTypeVisitor()
    {
		this.out = new PrintWriter(System.out);
    }

    private void indent()
    {
		out.print('\n');
		for(int i = 0; i < indentCount; i++)
	    { out.print(' '); }
    }

    /** Visitor pattern dispatch. */
   

    public void visit(BOOLEAN b) { out.print("BOOLEAN"); }
    public void visit(INT i)     { out.print("INT"); }
    public void visit(STRING s)  { out.print("STRING"); }
    public void visit(VOID v)    { out.print("VOID"); }
    public void visit(NIL n)     { out.print("NIL"); }

    public void visit(ARRAY a){
		
		out.print("ARRAY(");
        indentCount++;
        indent();
        a.element.accept(this);
        indentCount--;
        out.print(")");
	}
    public void visit(CLASS c){
		
		
		out.print("CLASS(" + c.name);
        indentCount++;
        indent(); 
		out.print(c.parent);          
        indent(); 
		c.methods.accept(this);
        indent(); 
		c.fields.accept(this);

        
        indent();
        out.print("OBJECT(" + c.instance.myClass.name);
        indentCount++;
        indent(); 
		c.instance.methods.accept(this);
        indent(); 
		c.instance.fields.accept(this);
        indentCount--;
        out.print(")");

        indentCount--;
        out.print(")");
	}
    public void visit(FIELD f){

		out.print("FIELD(" + f.index + " " + f.name);
        indentCount++;
        indent();
        f.type.accept(this);
        indentCount--;
        out.print(")");
		

	}
    public void visit(FUNCTION f){

		out.print("FUNCTION(" + f.name);          
        indentCount++;
        indent();
		f.self.accept(this);
        indent(); 
		f.formals.accept(this);
        indent(); 
		f.result.accept(this);
        indentCount--;
        out.print(")");
	}

    public void visit(OBJECT o){

		out.print("OBJECT(" + o.myClass.name + ")");
	}
    public void visit(RECORD r){

		out.print("RECORD(");
        indentCount++;
        for (FIELD f : r)
        {
            indent();
            f.accept(this);
        }
        indentCount--;
        out.print(")");
	}
	private void printFullObject(OBJECT o)
    {
        out.print("OBJECT(" + o.myClass.name);
        indentCount++;
        indent(); o.methods.accept(this);
        indent(); o.fields.accept(this);
        indentCount--;
        out.print(")");
    }
}
