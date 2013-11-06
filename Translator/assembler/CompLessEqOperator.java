package assembler;

import java.util.ArrayList;
import java.util.Stack;

public abstract class CompLessEqOperator extends AbsOperator {

	public void generate(ArrayList<String> operands) { 
		String first = operands.get(0);
		String second = operands.get(1);
		String dir = "";
		System.out.println("CMP "+ first +" , "+ second + System.lineSeparator());
		System.out.println("JG label_" + dir + System.lineSeparator());
	}
}