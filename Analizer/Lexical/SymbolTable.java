/**
 * 
 */
package Lexical;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

/**
 * @author niconistal
 *
 */
public class SymbolTable {
	
	//We need to store type, value, identifier, id.
	//					String, 'hola', elString , 3
	//					cadena, 'hola',          , 4
	//					id,     ,     , elString , 5
	//					cte,      1234,          , 6
	
	/**
	 * TYPE			257
	 * PRINT		258
	 * ID			259
	 * CTE			260
	 * CHARCHAIN	261
	 * FUNCTION		262
	 * BEGIN		263
	 * END 			264
	 * FOR 			265
	 * COMPARATOR 	266 > < >= <= != ==
	 * ERROR 		267
	 * IF 			268
	 * THEN 		269
	 * ELSE 		270
	 * 
	 */
	
	protected HashMap<String,SymbolElement> symbolTable;
	
	protected static SymbolTable instance;
	public static final String RESERVED_WORD = "RESERVED";
	
	protected SymbolTable(){
		
		
		
		symbolTable = new HashMap<String,SymbolElement>();
		
		BufferedReader br = null;
		String reservedWordsFile = "reserved_words.txt";
		
		try {
			 
				br = new BufferedReader(new FileReader(reservedWordsFile));
				String word;
				while ((word = br.readLine() ) != null){
					SymbolElement symbol = new SymbolElement(SymbolTable.RESERVED_WORD, null);
					addSymbol(word.toUpperCase(), symbol);
				}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	/**
	 * 
	 * @return
	 */
	public static SymbolTable getInstance(){
		if (instance == null){
			instance = new SymbolTable();
		}
		return instance;
	}
	
	public void addSymbol(String idName, SymbolElement symbol){
		
		symbolTable.put(idName.toUpperCase(),symbol);
	
	}
	/**
	 * 
	 * @param idName
	 * @return
	 */
	public SymbolElement identify(String idName){
		
		return symbolTable.get(idName.toUpperCase());
		
	}
	
	public boolean contains(String idName){
		
		return symbolTable.containsKey(idName.toUpperCase());
	}
	
	public String toString(){
		Set<String> idNames = symbolTable.keySet();
		String keys = "";
		for(String s : idNames){
			keys += s+ " -> " +symbolTable.get(s).toString()+ System.lineSeparator();
		}
		return keys;
	}
}