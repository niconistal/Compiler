package assembler;

import java.util.ArrayList;

public class NonConmutativeOperator extends AbsOperator {

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

	@Override
	public ArrayList<String> resolveMemory(Variable m1, Variable m2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void generate(ArrayList<String> operands) {
		// TODO Auto-generated method stub

	}

}
