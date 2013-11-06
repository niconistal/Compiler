package assembler;

import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.Pattern;

/**
 * AbsOperator will represent the family of the operator classes
 * @author Inti
 *
 */
public abstract class AbsOperator {
	
	public void operate(Stack<String> operandStack) {
		String op2 = operandStack.pop();
		String op1 = operandStack.pop();
		ArrayList<String> resolvedOperands = new ArrayList<String>();
		if(isRegister(op1) && isRegister(op2)) {
			Register r1 = new Register(op1);
			Register r2 = new Register(op2);
			resolvedOperands = this.resolveMemory(r1, r2);
		} else if(isRegister(op1) && !isRegister(op2)) {
			Register r1 = new Register(op1);
			Variable v2 = new Variable(op2);
			resolvedOperands = this.resolveMemory(r1, v2);
		} else if(!isRegister(op1) && isRegister(op2)){
			Variable v1 = new Variable(op1);
			Register r2 = new Register(op2);
			resolvedOperands = this.resolveMemory(v1, r2);
		} else {
			Variable v1 = new Variable(op1);
			Variable v2 = new Variable(op2);
			resolvedOperands = this.resolveMemory(v1, v2);
		}
		this.generate(resolvedOperands);
	}
	public abstract ArrayList<String> resolveMemory(Register r1, Register r2);
	public abstract ArrayList<String> resolveMemory(Register r1, Variable m2);
	public abstract ArrayList<String> resolveMemory(Variable m1, Register r2);
	/**
	 * This method of memory resolution will be the same for all binary operators
	 * @param m1 a variable
	 * @param m2 a variable
	 * @return the resolved variables
	 */
	public  ArrayList<String> resolveMemory(Variable m1, Variable m2) {
		RegisterHandler registerHanlder = RegisterHandler.getInstance();
		registerHanlder.getRegister();
	}
	
	public abstract void generate(ArrayList<String> operands);
	
	public boolean isRegister(String m){
		return !Pattern.matches("^_",m);
	}

}
