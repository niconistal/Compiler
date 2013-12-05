package assembler;

import java.util.ArrayList;

import Lexical.SymbolTable;

public class AssignOperator extends AbsBinOperator {

	public void generate(ArrayList<String> operands) { 
		String second = operands.get(1);
		String first = operands.get(0);	
		SymbolTable symbolTable = SymbolTable.getInstance();
		RegisterHandler regi = RegisterHandler.getInstance();
		
	if(CodeGenerator.isParameter(first)&&symbolTable.identify(second).getType()=="PARAM"){ //firs line in every function
		String reg1 = regi.getRegister();	
		CodeGenerator.assembler.add("MOV "+reg1+","+second); 
		CodeGenerator.assembler.add("MOV "+first+","+reg1);
		regi.freeRegister(reg1);
	} 
	else
		if(CodeGenerator.isParameter(first)) {
			String reg1 = regi.getRegister();
			String reg2 = regi.getRegister();
			CodeGenerator.assembler.add("MOV "+reg1+","+first);//mov ebx, first //reservar un registro1
			CodeGenerator.assembler.add("MOV "+reg2+","+second);//mov ecx, second //reservar otro registro2
			CodeGenerator.assembler.add("MOV ["+reg1+"],"+reg2);//mov [ebx] ecx
			regi.freeRegister(reg1);//liberar el registro1
			regi.freeRegister(reg2);//liberar el registro1
			
		} else 
			
			if(CodeGenerator.isParameter(second))
				
				CodeGenerator.assembler.add("MOV "+second+" , "+first);
				else
			
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
		CodeGenerator.assembler.add("MOV "+reg1+" , "+m2.getName());
		result.add(m1.getName());
		result.add(reg1);	
		registerHandlder.freeRegister(reg1);
		return result;

	}
	
	@Override
	public ArrayList<String> resolveParameters(String var1, String var2) {
		ArrayList<String> result = new ArrayList<String>(); 
		RegisterHandler regi = RegisterHandler.getInstance();
		if(CodeGenerator.isParameter(var1)) { 
			String reg1 = regi.getRegister();
			String reg2 = regi.getRegister();
			CodeGenerator.assembler.add("MOV "+reg1+","+var1);//mov ebx, var2 //reservar un registro1
			CodeGenerator.assembler.add("MOV "+reg2+",["+reg1+"]");//mov ecx, [ebx] //reservar otro registro2
			regi.freeRegister(reg1);//liberar el registro1
			result.add(reg2);			
			result.add(var2);			

		} else {
			result.add(var1);
			result.add(var2);
		}
		
		return result;
	}
	


}