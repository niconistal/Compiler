package Lexical;

import java.util.ArrayList;

public class ErrorHandler {

	protected static ErrorHandler instance;
	protected ArrayList<Error> errors;
	private boolean errorState = false;
	
	
	protected ErrorHandler(){
		
		errors = new ArrayList<Error>();
	}
	
	public static ErrorHandler getInstance(){
		if(instance == null){
			instance = new ErrorHandler();
		}
		return ErrorHandler.instance;
	}
	
	public void addError(Error error){
		this.errors.add(error);
		if(error.getType() == Error.TYPE_FATAL) {
			this.errorState = true;
		}
	}
	
	public boolean isInErrorState(){
		return this.errorState;
	}
	
	public String toString(){
		String resp = "";
		
		for(Error e : errors){
			resp += e.toString()+"\n";
		}
		
		return resp;
	}
	
	
}
