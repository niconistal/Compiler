package assembler;

import java.util.ArrayList;

public class CompEqOperator extends CompOperator {

	public void generate(ArrayList<String> operands){ 
		String first = operands.get(0);
		String second = operands.get(1);

		CodeGenerator.assembler.add("CMP "+ first +" , "+ second);
		CodeGenerator.check = "JNE";
		//System.out.println("JNE label_" + dir + System.lineSeparator());
	}
}