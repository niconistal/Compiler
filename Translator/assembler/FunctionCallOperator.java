package assembler;

import java.util.ArrayList;

public class FunctionCallOperator extends AbsSingleOperator {

//	@Override
//	public void generate(ArrayList<String> operands) {
//		String var = operands.get(0);
//		String fun = operands.get(1);
//		CodeGenerator.assembler.add("lea ebx, ["+ var +"]");
//		CodeGenerator.assembler.add("mov parameter_"+fun+ ", ebx"); /** TODO add to the STable parameter_ for each function**/
//		CodeGenerator.assembler.add("CALL label_"+ fun);
//		CodeGenerator.operandStack.push(operands.get(0));
//	}
	
	@Override
	public void generate(String functionName) {
		// en functionName+"_parameter" está el parámetro
		CodeGenerator.assembler.add("CALL label_"+ functionName);
	}

}
