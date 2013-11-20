package assembler;

import java.util.ArrayList;

public class AssignOperator extends AbsBinOperator {

	public void generate(ArrayList<String> operands) { 
		String second = operands.get(1);
		String first = operands.get(0);
		CodeGenerator.assembler.add("MOV "+first+" , "+second);
		//System.out.println("mov "+first+" , "+second+System.lineSeparator());

	}

	@Override
	public ArrayList<String> resolveMemory(Variable m1, Register r2) {
		ArrayList<String> result = new ArrayList<String>();
		result.add(m1.getName());
		result.add(r2.getName());
		RegisterHandler.getInstance().freeRegister(r2.getName());
		return result;
	}

}