package assembler;

import java.util.ArrayList;

public class MultOperator extends ConmutativeOperator {

	@Override
	public void generate(ArrayList<String> operands) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public  ArrayList<String> resolveMemory(Variable m1, Variable m2) {
		RegisterHandler registerHanlder = RegisterHandler.getInstance();
		ArrayList<String> result = new ArrayList<String>();
		String reg1 = registerHanlder.getRegister(registerHanlder.REG_A);
		//ADD TO FinalCodeContainer MOV reg1 m2.getName()
		result.add(m1.getName());
		result.add(reg1);
		return result;
	}
	
	@Override
	public  ArrayList<String> resolveMemory(Register r1, Variable m2) {
		RegisterHandler registerHanlder = RegisterHandler.getInstance();
		ArrayList<String> result = new ArrayList<String>();
		String reg1 = registerHanlder.getRegister(registerHanlder.REG_A);
		//ADD TO FinalCodeContainer MOV reg1 m2.getName()
		result.add(r1.getName());
		result.add(reg1);
		return result;
	}
	
	

}
