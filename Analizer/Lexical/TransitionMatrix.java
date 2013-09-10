package Lexical;

import java.util.HashMap;

public class TransitionMatrix {
	
	protected HashMap<String, Object> [] matrix;
	
	public TransitionMatrix(int size){
		matrix = new HashMap[size];
		for (int i = 0 ; i < size; i++){
			matrix[i] = new HashMap<String, Object>();
		}
	}
	
	public void setCellValue(int state,String character, Object cell ){
		matrix[state].put(character, cell);
	}
	
	public Object getCellValue(int state, String character){
		return matrix[state].get(character);
	}
}