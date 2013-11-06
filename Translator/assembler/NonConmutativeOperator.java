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
		ArrayList<String> result = new ArrayList<String>();
		RegisterHandler regi = RegisterHandler.getInstance();
		String rega = regi.getRegister();
		//Llamar a la clase de augusto MOV RA, M1
		//FinalCodeContainer.add("MOV "+rega+", "+m1.getName());
		result.add(rega);
		result.add(r2.getName());
		//El regi se va =(
		regi.freeRegister(r2.getName());
		return result;
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
