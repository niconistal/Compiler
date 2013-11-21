package assembler;

public class FunctionCallOperator extends AbsSingleOperator {

	@Override
	public void generate(String functionName) {
		// en functionName+"_parameter" est� el par�metro
		CodeGenerator.assembler.add("CALL label_"+ functionName);
	}

}
