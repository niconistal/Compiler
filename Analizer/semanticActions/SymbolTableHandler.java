package semanticActions;

import java.text.StringCharacterIterator;

import Lexical.SymbolElement;
import Lexical.SymbolTable;
import Lexical.Token;
/**
 * 
 * @author niconistal
 *
 *
 *	AS3
 *	Checks if the given symbol is already on the table, if not its added
 *
 */
public class SymbolTableHandler implements ISemanticAction {

	@Override
	public void performAction(Token tok, StringCharacterIterator source,int[] line) {
		if (! SymbolTable.getInstance().contains(tok.getLiteralValue())){
			SymbolElement symbol = new SymbolElement(tok.getTokenValue(),tok.getLiteralValue());
			SymbolTable.getInstance().addSymbol(tok.getLiteralValue(), symbol);
		}
		
	}

	@Override
	public String toString(){
		return "AS3";
	}
	
	
}
