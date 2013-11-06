package assembler;

import java.util.ArrayList;
import java.util.Stack;

public abstract class MinusOperator extends AbsOperator{

	public void generate(ArrayList<String> operands) { 
		String second = operands.get(1);
 		String first = operands.get(0); 
		System.out.println("sub "+first+" , "+second+System.lineSeparator());
		
	}

}
