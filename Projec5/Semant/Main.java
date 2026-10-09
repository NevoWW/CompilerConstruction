package Semant;

import Absyn.*;
import Parse.*;
import Symbol.Symbol;
import Symbol.Table;
import Types.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;

/**
 * COSC 4400 - Project #3
 * Scanner accepts input from user
 * @authors [Aleksandro Zhaka, Christian Guzman ]
 * Instructor [Dennis Brylow]
 * TA-BOT:MAILTO [aleksandro.zhaka@marquette.edu, christian.guzmanrivas@marquette.edu]
 */

public class Main {
	public static int errorCount = 0;

	public static void checkerPhaseOne(Program program){
		Program tempProgram = program;
		Table symClassTable = new Table();

		CLASS stringClass = new CLASS("String");
		Symbol stringKey = Symbol.symbol("String");
		symClassTable.put(stringKey,stringClass);
		
		for(ClassDecl tempClass : program.classes){

			CLASS cl = new CLASS(tempClass.name);
			duplicateClassCheck(cl, symClassTable);
			OBJECT ob;
			Symbol key1 = Symbol.symbol(tempClass.name);
			symClassTable.put(key1,cl);
			ob = cl.instance;
			ob.myClass = cl;

		}
		if(errorCount > 0) return;

		for(ClassDecl tempClass : program.classes){
			Symbol key1 = Symbol.symbol(tempClass.name);
			CLASS c1 = (CLASS)symClassTable.get(key1);

			if (tempClass.parent != null){
				CLASS parent = (CLASS) symClassTable.get(Symbol.symbol(tempClass.parent));

			 
				if (parent == null) {
					System.out.println("ERROR cannot resolve parent class: " + tempClass.parent + ": line not available");
					errorCount++;
					continue;
				}
				c1.parent = parent;
			}
			for(MethodDecl method : tempClass.methods){
				if (c1.methods.get(method.name) != null) {
					System.out.println("ERROR " + method.name + " is already defined in " + tempClass.name);
					errorCount++;
					continue;
				}
				Types.Type ret;
				if(method.returnType == null){
					ret = new Types.VOID();
				}
				else{
					ret = method.returnType.accept(new TypesPrint(symClassTable));
				}

				RECORD formals = new RECORD();
				for(Formal params : method.params){
					Types.Type fType = params.type.accept(new TypesPrint(symClassTable));
					formals.put(fType,params.name);
				}

				FUNCTION fn = new FUNCTION(method.name,c1.instance, formals, ret);
				c1.methods.put(fn,method.name);
			}
			for(VarDecl field : tempClass.fields){
				Types.Type fType = field.type.accept(new TypesPrint(symClassTable));
				c1.fields.put(fType,field.name);
			}
		}

		if(errorCount > 0) return;

		for (ClassDecl tempClass : program.classes){
			CLASS c1 = (CLASS)symClassTable.get(Symbol.symbol(tempClass.name));
			CLASS parent = c1.parent;
			int steps = 0;
			while (parent != null && steps <= program.classes.size()) {
				if (parent == c1) {
					System.out.println("ERROR cyclic inheritance involving " + c1.name + ": line not available");
					errorCount++;
					break;
				}
				parent = parent.parent;
				steps++;
			}
		}

		if(errorCount > 0) return;

		for (ClassDecl tempClass : program.classes){
			Symbol key1 = Symbol.symbol(tempClass.name);
			CLASS c1 = (CLASS)symClassTable.get(key1);
			RECORD Fsource = new RECORD();
			RECORD Msource = new RECORD();
			copy(Fsource,Msource,c1,symClassTable);
			c1.instance.fields = Fsource;
			c1.instance.methods = Msource;

		}

		if(errorCount > 0) return;
	
		for (ClassDecl tempClass : program.classes){
			Symbol key1 = Symbol.symbol(tempClass.name);
			CLASS c1 = (CLASS)symClassTable.get(key1);
			
			for (MethodDecl method : tempClass.methods) {
				TypesPrint checker = new TypesPrint(symClassTable);

				for (Stmt s : method.stmts) {   // use your real field name for the statement list
					s.accept(checker);
				}
    		}
		}

		PrintWriter writer = new PrintWriter(System.out);
		Types.PrintTypeVisitor ptv = new Types.PrintTypeVisitor(writer);   // match its real constructor
		for (ClassDecl tempClass : program.classes) {
			CLASS c = (CLASS) symClassTable.get(Symbol.symbol(tempClass.name));
			c.accept(ptv);
		}
		writer.flush();
	}


	static void copy(RECORD Fsource, RECORD Msource, CLASS cl, Table table) {
		if (cl == null) return;
		if(cl.parent != null){
			Symbol parent = Symbol.symbol(cl.parent.name);  
			if(table.get(parent) == null){
				System.out.print("ERROR cannot resolve parent class: " + parent + ": line not available");
				errorCount++;
				return;
			} 
		}
		
		copy(Fsource, Msource, cl.parent,table);               

		
		for (FIELD f : cl.fields) {
			Fsource.put(f.type, f.name);             
		}
		for (FIELD f : cl.methods) {
			// FIELD parentMethod = Msource.get(f.name);

			// if (parentMethod != null) {
			// 	if (!parentMethod.type.coerceTo(f.type) || !f.type.coerceTo(parentMethod.type)) {
			// 		System.out.println("ERROR incompatible method override: " + cl.parent.name + " in class " + cl.name +": line not available");
			// 		errorCount++;
			// 	}
			// }
			if(cl.parent != null){
				Msource.override(f.type, f.name, cl.parent.name, cl.name);
			}else{
				Msource.override(f.type, f.name, cl.name, cl.name);
			}
			
		}
	}

	static void duplicateClassCheck(CLASS cl, Table symClassTable){
		
		Symbol key = Symbol.symbol(cl.name);
		if(symClassTable.get(key) != null){
			System.out.println("ERROR duplicate class: " + cl.name + ": line not available");
			errorCount++;
		}
	}

    public static void main(String [] args) 
    {
	InputStreamReader isr =	new InputStreamReader(System.in);
    Reader reader = new BufferedReader(isr);
		

	try
	{
	Program parse = new MiniJavaParser(reader).Goal();
	// PrintWriter writer = new PrintWriter(System.out);
	// Program PrintVisitor pv =	new Program PrintVisitor(writer);
	// pv.visit(parse);
	// writer.flush();
	checkerPhaseOne(parse);
	
	}
    catch (ParseException p)
	{
	    System.out.println(p.toString());
	    System.exit(-1);
	}
	


    }

	
}

