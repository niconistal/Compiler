package assembler;

import java.util.ArrayList;
import java.util.Stack;

public class PrintOperator extends AbsOperator {

	public void generate(ArrayList<String> operands) { 
		String text = operands.get(0);
		System.out.println("invoke MessageBox, NULL, addr @" + text +" ,addr @" + text +" ,MB_OK"+System.lineSeparator());
		

	}

	@Override
	public ArrayList<String> resolveMemory(Register r1, Register r2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> resolveMemory(Register r1, Variable m2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> resolveMemory(Variable m1, Register r2) {
		// TODO Auto-generated method stub
		return null;
	}

}