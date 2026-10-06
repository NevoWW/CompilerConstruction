package Semant;

import Absyn.ClassDecl;
import Types.CLASS;
import Types.OBJECT;
import Symbol.Symbol;
import Symbol.Table;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.text.ParseException;
import java.util.AbstractList;


public class Main {

	public static void checkerPhaseOne(Absyn.Program program){
		Absyn.Program tempProgram = program;
		Table symTable = new Table();
		AbstractList<ClassDecl> programClassList = program.classes;
		AbstractList<CLASS> classList;
		AbstractList<OBJECT> ObjectList;
		Symbol SymbolTranslate;
		Symbol key;
		OBJECT tempObject;

		boolean hasDupes = hasDuplicates(programClassList);
		if(hasDupes ==true){
			System.out.print("Error");
			return;
		}
		for(int i =0; i < programClassList.size();i++){
			Absyn.ClassDecl tempClass = programClassList.get(i);
			if(tempClass.parent != null){
				//add inhertided fields
			}
			String className = tempClass.name;
			key = SymbolTranslate.symbol(className);
			symTable.put(key,tempClass);
			class = new CLASS(className);
			classList.put(class);
			tempObject = class.instance;
			tempObject.myClass = class;
			ObjectList.put(tempObject);
		}
	}

	public static boolean hasDuplicates(AbstractList list){
		return new HashSet<>(list).size() != list.size();
	}
    public static void main(String [] args) 
    {
	InputStreamReader isr =	new InputStreamReader(System.in);
    Reader reader = new BufferedReader(isr);
		

	try
	{
	Absyn.Program parse = new MiniJavaParser(reader).Goal();
	// PrintWriter writer = new PrintWriter(System.out);
	// Absyn.PrintVisitor pv =	new Absyn.PrintVisitor(writer);
	// pv.visit(parse);
	// writer.flush();
	ClassTemplateConstruction(parse);
	
	}
    catch (ParseException p)
	{
	    System.out.println(p.toString());
	    System.exit(-1);
	}
	


    }

	
}