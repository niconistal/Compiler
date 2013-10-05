/**
 * 
 */
package semanticActions;

import Lexical.SymbolTable;
import Lexical.Token;

/**
 * 
 * @author niconistal
 *
 *	AS12
 *	Checks if the identifier is a reserved word or if its an identifier.
 *
 */
public class IDTokenSetter extends TokenSetterTemplate {

	@Override
	public String resolveToken(Token tok) {

		if (SymbolTable.getInstance().contains(tok.getLiteralValue())){
			if(SymbolTable.getInstance().identify(tok.getLiteralValue()).getType().equals(SymbolTable.RESERVED_WORD)){
				return tok.getLiteralValue().toUpperCase();
			}
		}
		return "ID";
		
	}
	
	public String toString(){
		
		return "AS12";
	}

}