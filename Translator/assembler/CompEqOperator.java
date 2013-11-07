package assembler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class CompEqOperator extends CompOperator {

	public void generate(ArrayList<String> operands){ 
		String first = operands.get(0);
		String second = operands.get(1);
		//guardar_en_stack(JNE);
		CodeGenerator.assembler.add("CMP "+ first +" , "+ second);
		//System.out.println("CMP "+ first +" , "+ second + System.lineSeparator());
		//System.out.println("JNE label_" + dir + System.lineSeparator());
	}
}