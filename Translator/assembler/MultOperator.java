package assembler;

import java.util.ArrayList;
import java.util.Stack;

public abstract class MultOperator extends AbsOperator {

	public void generate(ArrayList<String> operands) { 
		String second = operands.get(1);
		String first = operands.get(0); 
		//first must be EAX
		System.out.println("imul "+first+" , "+second+System.lineSeparator());
		System.out.println("JO _overflowed"); //TODO add _overflowed: sentence, invoke message,
												//invoke exit process

	}

}
