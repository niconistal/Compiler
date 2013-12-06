package assembler;

import java.util.Stack;

public abstract class AbsUnaryOperator {
	
	public void operate(Stack<String> operandStack) {
		String op = operandStack.pop();
		this.generate(op);
	}
	
	public abstract void generate(String operand);
}
