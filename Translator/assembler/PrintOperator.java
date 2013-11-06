package assembler;

import java.util.ArrayList;
import java.util.Stack;

public abstract class PrintOperator extends AbsOperator {

	public void generate(ArrayList<String> operands) { 
		String text = operands.get(0);
		System.out.println("invoke MessageBox, NULL, addr @" + text +" ,addr @" + text +" ,MB_OK"+System.lineSeparator());
		

	}

}