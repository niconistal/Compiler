package Lexical;

public class Error {
	
	private String type;
	private String message;
	private int line;
	
	public Error(String type, String message, int line) {
		this.type = type;
		this.message = message;
		this.line = line;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	@Override
	public String toString() {
		return  type + ": " + message + ", in line "+ line;
	}
	
}
