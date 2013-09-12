package Lexical;

import java.util.ArrayList;

public class ErrorHandler {

	protected static ErrorHandler instance;
	protected ArrayList<Error> errors;
	
	
	protected ErrorHandler(){
		instance = new ErrorHandler();
		errors = new ArrayList<Error>();
	}
	
	public ErrorHandler getInstance(){
		if(instance == null){
			instance = new ErrorHandler();
		}
		return ErrorHandler.instance;
	}
	
	public void addError(Error error){
		this.errors.add(error);
	}
	
	public String toString(){
		String resp = "";
		
		for(Error e : errors){
			resp += e+"\n";
		}
		
		return resp;
	}
	
	
}
