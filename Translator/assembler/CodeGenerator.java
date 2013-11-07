package assembler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.regex.Pattern;


/**
 * CodeGenerator will iterate through the rpn translating the intermediate code
 * to the actual assembler code
 * @author Inti
 *
 */
public class CodeGenerator {
	
	private ArrayList<String> operands;
	
	public CodeGenerator(){
		this.operands = new ArrayList<String>();
	}
	/**
	 * Generates the assembler code given the rpn
	 * @param rpn the intermediate code in Reverse Polish Notation
	 */
	public void generate(HashMap<String,ArrayList<String>> rpn) {
		this.addDeclarations();
		this.parseIntCode(rpn, "MAIN");
		System.out.println(rpn);
	}
	
	/**
	 * Add to the file the necessary libraries for the MASM
	 */
	private void addHeader(){
		
		
	}
	/**
	 * Takes the Symbol Table, and adds the declaration statements, given the variables
	 * defined in it.
	 */
	private void addDeclarations() {
		//TODO implement this
	}
	
	/**
	 * Parses the intermediate code, stacking the operands, and executing the operators
	 * @param rpn the intermediate code in Reverse Polish Notation
	 */
	
	private void parseIntCode(HashMap<String,ArrayList<String>> rpn, String context) {
		ArrayList<String> intermediateCode = rpn.get(context);
		OperatorFactory factory = new OperatorFactory();

		
		for(int i=0; i< intermediateCode.size();i++) {
			String codeItem = intermediateCode.get(i);
			System.out.println(codeItem.toString());
			//if(labels.get[i]!=null)
			// agregar_lista("label_"+i+": ");
			//if it's not an operator
//			if(Pattern.matches("\\w+",codeItem)) {
//				this.operands.add(codeItem);
//			} else {
//				factory.create(codeItem).generate(this.operands);
//			count++;
//		} 
			

	
	}
	}
}
