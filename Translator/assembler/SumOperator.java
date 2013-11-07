package assembler;

import java.io.IOException;
import java.util.ArrayList;

public class SumOperator extends ConmutativeOperator{

	public void generate(ArrayList<String> operands){ 
		String second = operands.get(1);
		String first = operands.get(0); 
		CodeGenerator.assembler.add("add "+ first +" , "+ second);
		CodeGenerator.assembler.add("JO _overflowed");
		//System.out.println("add "+ first +" , "+ second + System.lineSeparator());
		//System.out.println("JO _overflowed"); //TODO add _overflowed: sentence, invoke message,
												//invoke exit process
	}

}
