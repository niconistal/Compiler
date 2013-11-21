package assembler;

public class FunctionCallOperator extends AbsSingleOperator {

	@Override
	public void generate(String functionName) {
		// en functionName+"_parameter" está el parámetro
		CodeGenerator.assembler.add("CALL label_"+ functionName);
	}

}
