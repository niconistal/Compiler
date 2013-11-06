package assembler;

import java.util.ArrayList;
import java.util.Stack;

public class MultOperator extends ConmutativeOperator {

	public void generate(ArrayList<String> operands) { 
		String second = operands.get(1);
		String first = operands.get(0); 
		//first must be EAX
		System.out.println("imul "+first+" , "+second+System.lineSeparator());
		System.out.println("JO _overflowed"); //TODO add _overflowed: sentence, invoke message,
												//invoke exit process

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
