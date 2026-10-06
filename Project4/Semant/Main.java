package Semant;

import Absyn.ClassDecl;
import Absyn.MethodDecl;
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
import java.util.HashSet;
import java.util.LinkedList;

public class Main {

	public static void checkerPhaseOne(Absyn.Program program){
		Absyn.Program tempProgram = program;
		Table symClassTable = new Table();
		AbstractList<ClassDecl> programClassList = program.classes;
		

		
		for(Absyn.ClassDecl tempClass : programClassList){

			CLASS cl = new CLASS(tempClass.name);
			OBJECT ob;
			Symbol key1 = Symbol.symbol(tempClass.name);
			symClassTable.put(key1,cl);
			ob = cl.instance;
			ob.myClass = cl;
		}
		for(Absyn.ClassDecl tempClass : programClassList){
			Symbol key1 = Symbol.symbol(tempClass.name);
			CLASS c1 = (CLASS)symClassTable.get(key1);
			for(MethodDecl method : tempClass.methods){

				c1.methods.put(method.returnType,method.name);
			}



		}

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