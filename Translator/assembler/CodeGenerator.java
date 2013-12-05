package assembler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
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
	
	public static Stack<String> operandStack;
	public static String check; 
	public static String context; 
	private HashMap<String, String> labels;
	public static ArrayList<String> assembler;
	
	public CodeGenerator() {		
		check = "";
		context = "";
		assembler = new ArrayList<String>();
		operandStack = new Stack<String>();
		this.labels = new HashMap<String,String>();
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
		this.addOverflowDeclaration();
		this.addFunctions(rpn);
		this.addExitDeclarations();
		save(assembler);
		printCode(assembler);
	}
	
	public void save(ArrayList<String> assembler) throws IOException {
	    PrintWriter pw = new PrintWriter(new FileOutputStream("code.asm"));
	    for (String codeItem: assembler){
	        pw.println(codeItem);
	    }
	    
		pw.close();
		

	}
	
	public static void printCode(ArrayList<String> assembler) throws IOException{

		//code.asm is already coded

		String comc = "cmd /c \\masm32\\bin\\ml /c /Zd /coff code.asm";
        Process ptasm32 = Runtime.getRuntime().exec(comc);
        InputStream is = ptasm32.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String aux = br.readLine();
        while (aux!=null){
            System.out.println(aux);
            aux = br.readLine();
        }
		String coml = "cmd /c \\masm32\\bin\\Link /SUBSYSTEM:CONSOLE code.obj";
		Process ptlink32 = Runtime.getRuntime().exec(coml);
		InputStream is2 = ptlink32.getInputStream();
        BufferedReader br2 = new BufferedReader(new InputStreamReader(is2));
        String aux2 = br2.readLine();
        while (aux2!=null){
            System.out.println(aux2);
            aux2 = br2.readLine();
        }
        Runtime aplicacion = Runtime.getRuntime();
        aplicacion.exec("code.exe"); 

        
	}
	
	/**
	 * Add to the file the necessary libraries for the MASM
	 * @throws IOException 
	 */
	private void addHeader() {
		
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
	private void addDeclarations() {
		assembler.add(".data");
		 
		SymbolTable symbolTable = SymbolTable.getInstance();
		symbolTable.addSymbol("0", new SymbolElement("CTE","0"));
		symbolTable.addSymbol("1", new SymbolElement("CTE","1"));
		
		Set<String> idNames =  symbolTable.keySet();
		for(String id : idNames){
			if(symbolTable.get(id).getUse()=="VAR" || symbolTable.get(id).getUse()=="PARAM"){
				assembler.add(id.toLowerCase() +" DD ?");
			} else if(symbolTable.get(id).getType()=="CTE") {
				assembler.add("_"+id +" DD "+id);
			} else if(symbolTable.get(id).getType()=="CHARCHAIN") {
				String idWhithoutSpaces = id.replaceAll("\\s", "").replaceAll("'", "");
				String idWhithoutSingleQuotes = id.replaceAll("'", "");
				assembler.add(idWhithoutSpaces +" DB \""+idWhithoutSingleQuotes+"\",0");
			}
		}
		assembler.add(" _OFmsg DB \"OVERFLOW \",0");
		assembler.add("rtn DD ?");
		assembler.add(".code");
		assembler.add("start:");
	}
	
	private void addOverflowDeclaration(){
		assembler.add("invoke ExitProcess, 0");
		assembler.add("_overflowed:");
		assembler.add("invoke MessageBox, NULL, addr _OFmsg ,addr _OFmsg ,MB_OK");
		assembler.add("invoke ExitProcess, 0");
	}
	
	/**
	 * Adds the exit declarations to the assembler code
	 */
	private void addExitDeclarations() {
		assembler.add("end start");
	}
	
	/**
	 * Generates and stores the labels for each jump
	 * @param rpn The main RPN intermediate code
	 */
	private void generateLabels(ArrayList<String> rpn, String context) {
		for(int i = 0; i< rpn.size();i++){
			//If it's a jump instruction
			if(Pattern.matches("\\[BF\\]|\\[JMP\\]",rpn.get(i))) {
				this.labels.put(rpn.get(i-1), context+"_label_"+rpn.get(i-1));
			}
		}
	}
	/**
	 * Returns true is the varName is identified as a parameter
	 * @param varName the variable name
	 * @return {boolean}
	 */
	public static boolean isParameter(String varName) {
		SymbolTable symbolTable = SymbolTable.getInstance();
//		System.out.println(varName);
//		if (symbolTable.contains(varName))
//		System.out.println(symbolTable.identify(varName).getUse());
		
		if (symbolTable.contains(varName))
			if (symbolTable.identify(varName).getUse()!=null && symbolTable.identify(varName).getUse()=="PARAM")
				return true;
				
		return false;
		
		//return Pattern.matches("^@\\w+", varName);
	}
	/**
	 * Adds function declarations to the assembler code
	 * @param rpn
	 */
	private void addFunctions(HashMap<String,ArrayList<String>> rpn){
		for(String func: rpn.keySet()) {
			if(func != "MAIN") {
				assembler.add("label_"+func.toLowerCase()+":");
				this.parseIntCode(rpn,func);
			}
		}
	}
	/**
	 * Parses the intermediate code, stacking the operands, and executing the operators
	 * @param rpn the intermediate code in Reverse Polish Notation
	 * @throws IOException 
	 */
	private void parseIntCode(HashMap<String,ArrayList<String>> rpn, String context) {
		ArrayList<String> intermediateCode = rpn.get(context);
		this.generateLabels(intermediateCode, context);
		CodeGenerator.context = context;
		OperatorFactory factory = new OperatorFactory();
		String codeItem = new String();
		for(int i=0; i< intermediateCode.size();i++) {
			codeItem = intermediateCode.get(i);
			//add label to assembler code
			if(labels.containsKey(Integer.toString(i))) {
				assembler.add(labels.get(Integer.toString(i))+":");
				labels.remove(Integer.toString(i));
			}
			
			//if it's not an operator
			if(Pattern.matches("\\w+|'(\\w|\\s)*'",codeItem)) {
				//identify if the operand is a parameter
				if(SymbolTable.getInstance().get(codeItem)!=null && SymbolTable.getInstance().get(codeItem).getUse()=="PARAM") {
					codeItem = "@"+codeItem;
				}
				//prepend _ if operand is a constant
				if(Pattern.matches("\\d+",codeItem)) {
					codeItem = "_"+codeItem;
				} else if (Pattern.matches("'(\\w|\\s)*'",codeItem)) {
					codeItem = codeItem.replaceAll("\\s", "").replaceAll("'", "");
				}
				operandStack.push(codeItem);
			} else {
				factory.create(codeItem).operate(operandStack);
			} 
			
		} 
		if(labels.values().size() > 0&& context=="MAIN") {
			ArrayList<String> remainingLabels = new ArrayList<String>(labels.values());
			String lastLabel = remainingLabels.get(0);
			assembler.add(lastLabel+":");
		}
	}
	
}

