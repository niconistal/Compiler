package assembler;

import java.util.ArrayList;

public class SumOperator extends ConmutativeOperator{

	public void generate(ArrayList<String> operands) { 
		String second = operands.get(1);
		String first = operands.get(0); 
		System.out.println("add "+ first +" , "+ second + System.lineSeparator());
		System.out.println("JO _overflowed"); //TODO add _overflowed: sentence, invoke message,
												//invoke exit process
	}

}
