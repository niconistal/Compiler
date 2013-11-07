package assembler;

import java.util.ArrayList;
import java.util.Stack;

public class CompLessEqOperator extends CompOperator {

	public void generate(ArrayList<String> operands) { 
		String first = operands.get(0);
		String second = operands.get(1);
		String dir = operands.get(2);
		System.out.println("CMP "+ first +" , "+ second + System.lineSeparator());
		System.out.println("JG label_" + dir + System.lineSeparator());
	}
}