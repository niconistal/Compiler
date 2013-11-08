package assembler;

public class OperatorFactory {

	public AbsOperator create(String operatorName) {
		switch (operatorName) {
		case "+":
			return new SumOperator();
		case "-":
			return new MinusOperator();
		case "*":
			return new MultOperator();
		case "/":
			return new DivOperator();
		case "[JMP]":
			return new JumpOperator();
		case "=":
			return new AssignOperator();
		default:
			return null;
		}
	}
}
