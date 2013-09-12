package Lexical;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

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
	
	@Override
	public String toString(){
		String eol = System.getProperty("line.separator");  
		String returnString = "";
		Set<String> keys = matrix[0].keySet();
		for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
			String s = iterator.next();
			returnString += "|  ";
			returnString += s;
			returnString += "  |";
		}
		returnString += eol;
		for(HashMap<String, Object> row : matrix){
			for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
				String s = iterator.next();
				returnString += "| ";
				returnString += row.get(s).toString();
				returnString += " |";
			}
			returnString += eol;
		}
		
		
		return returnString;
	}
}