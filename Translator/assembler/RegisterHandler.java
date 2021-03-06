package assembler;

/**
 * This Singleton class will manage the retrieving and availability of the registers
 * @author Inti
 *
 */
public class RegisterHandler {
	
	//a false value in the ith field will indicate that the i register is
	// AVAILABLE
	public static int REG_A = 3;
	public static int REG_B = 2;
	public static int REG_C = 1;
	public static int REG_D = 0;
	private String[] regs = {"EBX","ECX","EDX","EAX"};
	private boolean[] registers;
	private static final int REG_AMOUNT = 4;
	private static RegisterHandler instance;
	
	public RegisterHandler() {
		registers = new boolean[REG_AMOUNT];
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
				this.registers[i] = true;
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
			this.registers[r] = true;
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
	/**
	 * 
	 */
	public boolean isFree(int register){
		return  !this.registers[register];
	}
	
/**
 * 
 */
	public String reallocate(String register){
		String reg = this.getRegister();
		this.freeRegister(register);
		CodeGenerator.assembler.add("MOV "+reg+" , "+register);
		return reg;
		
	}
}
