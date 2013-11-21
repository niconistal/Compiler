package assembler;

import java.util.ArrayList;

public class AssignOperator extends AbsBinOperator {

	public void generate(ArrayList<String> operands) { 
		String second = operands.get(1);
		String first = operands.get(0);
		if(CodeGenerator.isParameter(first)) {
			//mov ebx, first
			//mov ecx second
			//mov [ebx] ecx
		} else {
			CodeGenerator.assembler.add("MOV "+first+" , "+second);
		}
		
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
	@Override
	public  ArrayList<String> resolveMemory(Variable m1, Variable m2) {
		RegisterHandler registerHandlder = RegisterHandler.getInstance();
		ArrayList<String> result = new ArrayList<String>();
		String reg1 = registerHandlder.getRegister();
		CodeGenerator.assembler.add("MOV "+reg1+" , "+m2.getName());
		result.add(m1.getName());
		result.add(reg1);	
		registerHandlder.freeRegister(reg1);
		return result;

	}
	
	@Override
	public ArrayList<String> resolveParameters(String var1, String var2) {
		ArrayList<String> result = new ArrayList<String>(); 
		result.add(var1);
		if(CodeGenerator.isParameter(var2)) {
			//mov ebx, var2
			//mov ecx, [ebx]
			result.add("ecx");
		} else {
			result.add(var2);
		}
		return result;
	}
	


}