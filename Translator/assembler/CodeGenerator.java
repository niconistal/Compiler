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
	private HashMap<String, String> labels;
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
	 * Takes the Symbol Table, and adds the declaration statements, given the variables
	 * defined in it.
	 */
	private void addDeclarations() {
		//TODO implement this
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
	 */
	
	private void parseIntCode(HashMap<String,ArrayList<String>> rpn, String context) {
		ArrayList<String> intermediateCode = rpn.get(context);
		this.generateLabels(intermediateCode);
		OperatorFactory factory = new OperatorFactory();

		//labels
		
		for(String codeItem : intermediateCode) {
			System.out.println(codeItem.toString());
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
