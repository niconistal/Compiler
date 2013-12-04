package assembler;

public class JumpOperator extends AbsSingleOperator {

	public void generate(String operand) {  
		CodeGenerator.assembler.add("JMP "+CodeGenerator.context+"_label" + operand);
	}
}