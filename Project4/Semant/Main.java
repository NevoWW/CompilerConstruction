package Semant;

import Parse.*;
import Absyn.*;
import Types.*;
import Symbol.Symbol;
import Symbol.Table;
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
			for(MethodDecl method : tempClass.methods){
				Types.Type ret;
				if(method.returnType == null){
					ret = new Types.VOID();
				}
				else{
					ret = method.returnType.accept(new TypesPrint(symClassTable));
				}
				// c1.methods.put(ret,method.name);

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

		for (ClassDecl tempClass : program.classes){
			Symbol key1 = Symbol.symbol(tempClass.name);
			CLASS c1 = (CLASS)symClassTable.get(key1);
			RECORD Fsource = new RECORD();
			RECORD Msource = new RECORD();
			copy(Fsource,Msource,c1);
			c1.instance.fields = Fsource;
			c1.instance.methods = Msource;

		}
	

		PrintWriter writer = new PrintWriter(System.out);
		Types.PrintTypeVisitor ptv = new Types.PrintTypeVisitor(writer);   // match its real constructor
		for (ClassDecl tempClass : program.classes) {
			CLASS c = (CLASS) symClassTable.get(Symbol.symbol(tempClass.name));
			c.accept(ptv);
		}
		writer.flush();
	}


	static void copy(RECORD Fsource, RECORD Msource, CLASS cl){
			if(cl.parent == null){
				for(FIELD f : cl.fields){
					if(Fsource.get(f.name) == null) Fsource.put(f.type,f.name);
				}
				for(FIELD f : cl.methods){
					if(Msource.get(f.name) == null) Msource.put(f.type,f.name);
				}
				return;
			}else{
				copy(Fsource, Msource, cl.parent);
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

