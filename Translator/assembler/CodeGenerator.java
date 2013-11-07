package assembler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Pattern;

import Lexical.SymbolElement;
import Lexical.SymbolTable;


/**
 * CodeGenerator will iterate through the rpn translating the intermediate code
 * to the actual assembler code
 * @author Inti
 *
 */
public class CodeGenerator {
	
	private Stack<String> operandStack;
	private HashMap<String, String> labels;
	private ArrayList<String> operands;
	public static ArrayList<String> assembler;
	
	public CodeGenerator() throws IOException{
		this.operands = new ArrayList<String>();		
		this.assembler =new ArrayList<String>();
	}
	/**
	 * Generates the assembler code given the rpn
	 * @param rpn the intermediate code in Reverse Polish Notation
	 * @throws IOException 
	 */
	public void generate(HashMap<String,ArrayList<String>> rpn) throws IOException {
		this.addHeader();
		this.addDeclarations();
		this.parseIntCode(rpn, "MAIN");
		System.out.println(rpn);
		
		printCode(assembler);
	}
	
	public static void printCode(ArrayList<String> assembler){
		for(String codeItem: assembler){
			System.out.println(codeItem);
		}
		
	}
	
	
	/**
	 * Add to the file the necessary libraries for the MASM
	 * @throws IOException 
	 */
	private void addHeader() throws IOException{
		
		assembler.add(".386" ); 
		assembler.add(".model flat, stdcall" );
		assembler.add("option casemap :none" );
		assembler.add("include \\masm32\\include\\windows.inc" );
		assembler.add("include \\masm32\\include\\kernel32.inc" );
		assembler.add("include \\masm32\\include\\user32.inc" );
		assembler.add("includelib \\masm32\\lib\\kernel32.lib" );
		assembler.add("includelib \\masm32\\lib\\user32.lib" );
		 
		
	}
	/**
	 * Takes the Symbol Table, and adds the declaration statements, given the variables
	 * defined in it.
	 * @throws IOException 
	 */
	private void addDeclarations() throws IOException {
		assembler.add(".data");
		 
		SymbolTable symbolTable = SymbolTable.getInstance();
		Set<String> idNames =  symbolTable.keySet();
		for(String s : idNames){
			if(symbolTable.get(s).getUse()=="VAR"){
				assembler.add(s +" DD ?");
				 
				}
		}
		assembler.add(".code");
		assembler.add("start:");
		assembler.add("invoke ExitProcess, 0");
		assembler.add("end start");
		 
	
	}
	
	/**
	 * Generates and stores the labels for each jump
	 * @param rpn The main RPN intermediate code
	 */
	private void generateLabels(ArrayList<String> rpn) {
		for(int i = 0; i< rpn.size();i++){
			//If it's a jump instruction
			if(Pattern.matches("\\[BF\\]|\\[JMP\\]",rpn.get(i))) {
				this.labels.put(rpn.get(i-1), "label_"+rpn.get(i-1));
			}
		}
	}
	/**
	 * Parses the intermediate code, stacking the operands, and executing the operators
	 * @param rpn the intermediate code in Reverse Polish Notation
	 * @throws IOException 
	 */
	
	private void parseIntCode(HashMap<String,ArrayList<String>> rpn, String context) throws IOException {
		ArrayList<String> intermediateCode = rpn.get(context);
//		this.generateLabels(intermediateCode);
		OperatorFactory factory = new OperatorFactory();

//		assembler.add(".code");
//		assembler.add("start:");
//		
//		for(int i=0; i< intermediateCode.size();i++) {
//			String codeItem = intermediateCode.get(i);
//			//System.out.println(codeItem.toString());
//			if(labels.containsKey(Integer.toString(i)))
//				assembler.add("label_"+i+": ");
//				//if it's not an operator
//			if(Pattern.matches("\\w+",codeItem)) {
//				this.operands.add(codeItem);
//			} else {
//				factory.create(codeItem).operate(this.operandStack);
//			} 
//			
//
//	
//	}
	}
}
