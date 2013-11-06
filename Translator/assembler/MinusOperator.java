package assembler;

import java.util.ArrayList;
import java.util.Stack;

public class MinusOperator extends NonConmutativeOperator{

	public void generate(ArrayList<String> operands) { 
		String second = operands.get(1);
 		String first = operands.get(0); 
		System.out.println("sub "+first+" , "+second+System.lineSeparator());
		
	}

}
