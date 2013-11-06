package assembler;

import java.util.ArrayList;
import java.util.Stack;

public abstract class AssignOperator extends AbsOperator {

	public void generate(ArrayList<String> operands) { 
		String second = operands.get(1);
		String first = operands.get(0);
		System.out.println("mov "+first+" , "+second+System.lineSeparator());

	}

}