package assembler;

public class BFOperator extends AbsSingleOperator {

	public void generate(String operand) {  
		CodeGenerator.assembler.add(CodeGenerator.check+" label_"+ operand);
		//System.out.println(jumpType+" label_"+ direction + System.lineSeparator());
	}
}