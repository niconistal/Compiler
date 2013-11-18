package assembler;

import java.util.ArrayList;

public abstract class CompOperator extends AbsBinOperator {

	@Override
	public ArrayList<String> resolveMemory(Register r1, Register r2) {
		ArrayList<String> result = new ArrayList<String>();
		result.add(r1.getName());
		result.add(r2.getName());
		RegisterHandler.getInstance().freeRegister(r1.getName());
		RegisterHandler.getInstance().freeRegister(r2.getName());
		return result;
	}
	@Override
	public ArrayList<String> resolveMemory(Register r1, Variable v2) {
		ArrayList<String> result = new ArrayList<String>();
		result.add(r1.getName());
		result.add(v2.getName());
		RegisterHandler.getInstance().freeRegister(r1.getName());
		return result;
	}
	@Override
	public ArrayList<String> resolveMemory(Variable v1, Register r2) {
		ArrayList<String> result = new ArrayList<String>();
		result.add(v1.getName());
		result.add(r2.getName());
		RegisterHandler.getInstance().freeRegister(r2.getName());
		return result;
	}
	@Override
	public ArrayList<String> resolveMemory(Variable v1, Variable v2) {
		RegisterHandler registerHanlder = RegisterHandler.getInstance();
		ArrayList<String> result = new ArrayList<String>();
		String reg1 = registerHanlder.getRegister();
		//ADD TO FinalCodeContainer MOV reg1 v2.getName()
		result.add(v1.getName());
		result.add(reg1);
		registerHanlder.freeRegister(reg1);
		return result;
	}

	public abstract void generate(ArrayList<String> operands);
}
