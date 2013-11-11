package assembler;

import java.io.IOException;
import java.util.ArrayList;

public abstract class NonConmutativeOperator extends AbsOperator {

	@Override
	public ArrayList<String> resolveMemory(Variable m1, Register r2) {
		ArrayList<String> result = new ArrayList<String>();
		RegisterHandler regi = RegisterHandler.getInstance();
		String rega = regi.getRegister();
		CodeGenerator.assembler.add("MOV "+rega+" , "+ r2.getName()); //MOV Ri, M1
		result.add(rega);
		result.add(r2.getName());
		//El regi se va =(
		regi.freeRegister(r2.getName());
		return result;
	}


}
