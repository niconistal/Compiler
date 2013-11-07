package assembler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class MultOperator extends ConmutativeOperator {

	public void generate(ArrayList<String> operands){ 
		String second = operands.get(1);
		String first = operands.get(0); 
		//first must be EAX
		CodeGenerator.assembler.add("imul "+first+" , "+second);
		CodeGenerator.assembler.add("JO _overflowed");
		//System.out.println("imul "+first+" , "+second+System.lineSeparator());
		//System.out.println("JO _overflowed"); //TODO add _overflowed: sentence, invoke message,
												//invoke exit process

	}
	@Override
	public ArrayList<String> resolveMemory (Variable m1, Variable m2){
		ArrayList<String> result = new ArrayList<String>();
		RegisterHandler regi = RegisterHandler.getInstance();
		String rega = regi.getRegister(RegisterHandler.REG_A);
		//Llamar a la case de augusto MOV RA, M1
		//FinalCodeContainer.add("MOV "+rega+", "+m1.getName(),-1);
		result.add(rega);
		result.add(m2.getName());
		return result;
	}

	@Override
	public ArrayList<String> resolveMemory(Variable m1, Register r2) {
		// TODO Auto-generated method stub
		ArrayList<String> result = new ArrayList<String>();
		RegisterHandler regi = RegisterHandler.getInstance();
		String rega = regi.getRegister(RegisterHandler.REG_A);
		//Llamar a la clase de augusto MOV RA, M1
		//FinalCodeContainer.add("MOV "+rega+", "+m1.getName(),-1);
		result.add(rega);
		result.add(r2.getName());
		//El regi se va =(
		regi.freeRegister(r2.getName());
		return result;
	}
	
	@Override
	public ArrayList<String> resolveMemory(Register r1, Variable m2){
		ArrayList<String> result = new ArrayList<String>();
		if (! r1.getName().equals("A")){
			RegisterHandler regi = RegisterHandler.getInstance();
			String rega = regi.getRegister(RegisterHandler.REG_A);
			regi.freeRegister(r1.getName());
			//Aca llamo a la clase de augusto MOV RA, R1
			//FinalCodeContainer.add("MOV "+rega", "+r1.getName(),-1);
			result.add(rega);
			result.add(m2.getName());
		}else{
			result = super.resolveMemory(r1, m2);
		}
		
		return result;
		
		
	}
	
	@Override
	public ArrayList<String> resolveMemory(Register r1, Register r2){
		ArrayList<String> result = new ArrayList<String>();
		if(! r1.getName().equals("A")){
			RegisterHandler regi = RegisterHandler.getInstance();
			String rega = regi.getRegister(RegisterHandler.REG_A);
			regi.freeRegister(r1.getName());
			//Aca llamo a la clase de augusto MOV RA, R1
			//FinalCodeContainer.add("MOV "+rega", "+r1.getName(),-1);
			result.add(rega);
			result.add(r2.getName());
		}else{
			result = super.resolveMemory(r1,r2);
		}
		
		return result;
	}
	
	

}
