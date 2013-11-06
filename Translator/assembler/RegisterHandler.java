package assembler;

import java.util.ArrayList;
import java.util.Collection;

import Lexical.SymbolTable;

/**
 * This Singleton class will manage the retrieving and availability of the registers
 * @author Inti
 *
 */
public class RegisterHandler {
	
	//a false value in the ith field will indicate that the i register is
	// AVAILABLE
	private String[] regs = {"B","C","D","A"};
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
	 * @return String the register name
	 */
	public String getRegister() {
		for(int i = 0; i < REG_AMOUNT; i++) {
			if(!this.registers[i]) {
				return regs[i];
			}
		}
		return null;
	}
	/**
	 * Returns a specific register 
	 * @param r the register number
	 * @return String the register name
	 */
	public String getRegister(int r) {
		if(!this.registers[r]) {
			return this.regs[r];
		}
		return null;
	}
	/**
	 * Sets an indicated register as free
	 * @param reg
	 */
	public void freeRegister(String regName){
		for(int i = 0;i < this.regs.length; i++) {
			if(this.regs[i].equals(regName)) {
				this.registers[i] = false;
			}
		}
	}
}
