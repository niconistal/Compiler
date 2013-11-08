package assembler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class MinusOperator extends NonConmutativeOperator{

	public void generate(ArrayList<String> operands) { 
		String second = operands.get(1);
 		String first = operands.get(0); 
 		CodeGenerator.assembler.add("SUB "+first+" , "+second);
 		CodeGenerator.operandStack.push(first);
		
	}

}
