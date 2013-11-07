package assembler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class BFOperator extends AbsOperator {

	public void generate(ArrayList<String> operands) {  
		String direction = operands.get(1);
		String jumpType = operands.get(0);
		CodeGenerator.assembler.add(jumpType+" label_"+ direction);
		//System.out.println(jumpType+" label_"+ direction + System.lineSeparator());
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