package assembler;

import java.util.Stack;

public class FunctionRetOperator extends AbsSingleOperator {

	public void generate() {
		CodeGenerator.assembler.add("ret");
	}

	public void operate(Stack<String> operandStack) {
		this.generate();
	}

	@Override
	public void generate(String operand) {
		// TODO Auto-generated method stub
		
	}
	
}
