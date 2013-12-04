package assembler;

import java.util.Stack;

public abstract class AbsOperator {
	
	public abstract void operate(Stack<String> operandStack);
}
