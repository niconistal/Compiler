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
	
	private Stack<String> operandStack;
	
	public CodeGenerator(){
		this.operandStack = new Stack<String>();
	}
	/**
	 * Generates the assembler code given the rpn
	 * @param rpn the intermediate code in Reverse Polish Notation
	 */
	public void generate(HashMap<String,ArrayList<String>> rpn) {
		this.addDeclarations();
		
		System.out.println(rpn);
	}
	/**
	 * Takes the Symbol Table, and adds the declaration statements, given the variables
	 * defined in it.
	 */
	private void addDeclarations() {
		
	}
	/**
	 * Parses the intermediate code, stacking the operands, and executing the operators
	 * @param rpn the intermediate code in Reverse Polish Notation
	 */
	private void parseIntCode(HashMap<String,ArrayList<String>> rpn, String context) {
		ArrayList<String> intermediateCode = rpn.get(context);
		OperatorFactory factory = new OperatorFactory();
		for(String codeItem : intermediateCode) {
			//if it's not an operator
			if(Pattern.matches("\\w+",codeItem)) {
				this.operandStack.push(codeItem);
			} else {
				factory.create(codeItem).operate(this.operandStack);
			}
		} 
	
	}
}
