package assembler;

import java.util.ArrayList;

public class LeaOperator extends AbsBinOperator {

	public void generate(ArrayList<String> operands) { 
		String second = operands.get(1);
		String first = operands.get(0);
		CodeGenerator.assembler.add("MOV "+first+" , "+second);

		

	}

	@Override
	public ArrayList<String> resolveMemory(Variable m1, Register r2) {
		ArrayList<String> result = new ArrayList<String>();
		result.add(m1.getName());
		result.add(r2.getName());
		RegisterHandler.getInstance().freeRegister(r2.getName());
		return result;
	}
	@Override
	public  ArrayList<String> resolveMemory(Variable m1, Variable m2) {
		RegisterHandler registerHandlder = RegisterHandler.getInstance();
		ArrayList<String> result = new ArrayList<String>();
		String reg1 = registerHandlder.getRegister();
		CodeGenerator.assembler.add("LEA "+reg1+" , ["+m2.getName()+"]");
		result.add(m1.getName());
		result.add(reg1);	
		registerHandlder.freeRegister(reg1);
		return result;

	}
	
	@Override
	public ArrayList<String> resolveParameters(String var1, String var2) {
		ArrayList<String> result = new ArrayList<String>(); 
		result.add(var1);
		result.add(var2);

		return result;
	}
	


}