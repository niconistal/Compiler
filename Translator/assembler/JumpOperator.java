package assembler;

import java.util.ArrayList;
import java.util.Stack;

public abstract class JumpOperator extends AbsOperator {

	public void generate(ArrayList<String> operands) {  
		String direction = operands.get(0);
		System.out.println("JMP "+ direction + System.lineSeparator());
	}
}