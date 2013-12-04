package assembler;

import java.util.ArrayList;

public abstract class ConmutativeOperator extends AbsBinOperator {

	@Override
	public ArrayList<String> resolveMemory(Variable m1, Register r2) {
		ArrayList<String> result = new ArrayList<String>();
		result.add(r2.getName());
		result.add(m1.getName());
		return result;
	}

	public abstract void generate(ArrayList<String> operands);

	public String getRegA() {
		// TODO Auto-generated method stub
		return null;
	}

}
