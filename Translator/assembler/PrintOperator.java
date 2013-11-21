package assembler;


public class PrintOperator extends AbsSingleOperator {

	public void generate(String operand){ 
		CodeGenerator.assembler.add("invoke MessageBox, NULL, addr " + operand.toUpperCase() +" ,addr " + operand.toUpperCase() +" ,MB_OK");
	}
}