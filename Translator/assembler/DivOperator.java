package assembler;

import java.util.ArrayList;
import java.util.Stack;

public class DivOperator extends NonConmutativeOperator {

	public void generate(ArrayList<String> operands) { 
		//EDX:EAX / REGoMem = EAX
		//viene [REG1,REG2 (ok), REG1,var(ok) con REG1 en EAX ]
		//viene [REG1,cte REG1 en eax y cte almacenada en variable]
		// regs[B,C,D,A] que el A se ocupe ultimo
		String first = operands.get(0);
		System.out.println("idiv "+first+System.lineSeparator());
	}

}
