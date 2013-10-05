/**
 * 
 */
package semanticActions;

import java.text.StringCharacterIterator;

import Lexical.Token;

/**
 * @author niconistal
 *
 *
 *	AS5
 *	Moves the iterator of the source code a step back.
 *
 *
 */
public class CharacterReturner implements ISemanticAction {

	@Override
	public void performAction(Token tok, StringCharacterIterator source,int[] line) {

		source.previous();
	}
	
	@Override
	public String toString(){
		return "AS5";
	}

}
