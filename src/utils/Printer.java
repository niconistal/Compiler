package utils;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Printer {

	public static String stringify(HashMap<String,ArrayList<String>> polish){
		String stringvar = new String();
		
		String newline = System.getProperty("line.separator");
		
		Set<String> keys = polish.keySet();
		for (String key : keys){
			stringvar += key.toUpperCase() + " -> ";
			stringvar += polish.get(key).toString();
			stringvar += newline;
			stringvar += newline;
		}
		
		return stringvar;
	}
	
	public static void printPolish(HashMap<String,ArrayList<String>> polish){
		PrintWriter out = null;
		try {
			out = new PrintWriter("polish.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.println(Printer.stringify(polish));
		out.flush();
		
	}
}
