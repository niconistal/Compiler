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
	private HashMap<String,ArrayList<String>> rpn;
	
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
		this.rpn=rpn;
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
	 * return the item located in the ith position of the 
	 * polish (context applied)
	 */
	protected String getPosition(int i, String context){
		return rpn.get(context).get(i);
		
	}
	
	/**
	 * Parses the intermediate code, stacking the operands, and executing the operators
	 * @param rpn the intermediate code in Reverse Polish Notation
	 */
	
	private void parseIntCode(HashMap<String,ArrayList<String>> rpn, String context) {
		ArrayList<String> intermediateCode = rpn.get(context);
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
