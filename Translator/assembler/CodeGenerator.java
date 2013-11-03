package assembler;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * CodeGenerator will iterate through the rpn translating the intermediate code
 * to the actual assembler code
 * @author Inti
 *
 */
public class CodeGenerator {

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
}
