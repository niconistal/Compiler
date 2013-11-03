package assembler;

import java.util.Stack;

/**
 * AbsOperator will represent the family of the operator classes
 * @author Inti
 *
 */
public abstract class AbsOperator {
	
	public abstract void operate(Stack<String> operandStack);

}
