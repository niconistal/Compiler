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
		case "[BF]":
			return new BFOperator();	
		case "=":
			return new AssignOperator();
		case "==":
			return new CompEqOperator();
		case ">=":
			return new CompGreatEqOperator();
		case ">":
			return new CompGreatOperator();
		case "<=":
			return new CompLessEqOperator();
		case "<":
			return new CompLessOperator();
		case "!=":
			return new CompNotEqOperator();
		case "[PRINT]":
			return new PrintOperator();	
		case "[CALL]":
			return new FunctionCallOperator();	
		case "[LEA]":
			return new LeaOperator();
		case "[RET]":
			return new FunctionRetOperator();	
		default:
			return null;
		}
	}
}
