package assembler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class CompNotEqOperator extends AbsOperator {

	public void generate(ArrayList<String> operands){ 
		String first = operands.get(0);
		String second = operands.get(1);
		//guardar_en_stack(JE);
		CodeGenerator.assembler.add("CMP "+ first +" , "+ second + System.lineSeparator());
		//System.out.println("CMP "+ first +" , "+ second + System.lineSeparator());
		//System.out.println("JE label_" + dir + System.lineSeparator());
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