package assembler;

public class JumpOperator extends AbsSingleOperator {

	public void generate(String operand) {  
		CodeGenerator.assembler.add("JMP "+ operand);
		//System.out.println("JMP "+ direction + System.lineSeparator());
	}
}