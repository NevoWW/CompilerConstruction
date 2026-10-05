package Semant;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.text.ParseException;

public class Main {
    public static void main(String [] args) 
    {
	InputStreamReader isr =	new InputStreamReader(System.in);
        Reader reader = new BufferedReader(isr);

	try
	{
	PrintWriter writer = new PrintWriter(System.out);
	Types.PrintTypeVisitor pv =	new Types.PrintTypeVisitor(writer);
	pv.visit();
	writer.flush();
	}
    catch (ParseException p)
	{
	    System.out.println(p.toString());
	    System.exit(-1);
	}

	
    }
}