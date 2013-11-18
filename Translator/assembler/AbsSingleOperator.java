package assembler;

import java.util.Stack;

public abstract class AbsSingleOperator extends AbsOperator {
	
	@Override
	public void operate(Stack<String> operandStack) {
		String operand = operandStack.pop();
		this.generate(operand);
	}
	
	public abstract void generate(String operand);
}
