package assembler;

import Lexical.SymbolTable;

/**
 * This Singleton class will manage the retrieving and availability of the registers
 * @author Inti
 *
 */
public class RegisterHandler {
	
	//a false value in the ith field will indicate that the i register is
	// AVAILABLE
	private boolean registers[];
	private static final int REG_AMOUNT = 4;
	private static RegisterHandler instance;
	
	public RegisterHandler() {
		for(int i = 0;i < REG_AMOUNT;i++) {
			this.registers[i] = false;
		}
	}
	
	/**
	 * Handles the singleton instance
	 * @return RegisterHandler instance
	 */
	public static RegisterHandler getInstance() {
		if (instance == null){
			instance = new RegisterHandler();
		}
		return instance;
	}
	/**
	 * Finds and returns the first available register
	 * @return int the register number
	 */
	public int getRegister() {
		for(int i = 0; i < REG_AMOUNT; i++) {
			if(!this.registers[i]) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * Sets an indicated register as free
	 * @param reg
	 */
	public void freeRegister(int reg){
		this.registers[reg] = false;
	}
}
