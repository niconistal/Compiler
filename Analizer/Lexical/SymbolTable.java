/**
 * 
 */
package Lexical;

import java.util.HashMap;

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
	
	protected SymbolTable(){
		
		
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
		
		symbolTable.put(idName,symbol);
	
	}
	/**
	 * 
	 * @param idName
	 * @return
	 */
	public SymbolElement identify(String idName){
		
		return symbolTable.get(idName);
		
	}
}