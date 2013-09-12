/**
 * 
 */
package Lexical;

import java.text.StringCharacterIterator;

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
	public void performAction(Token tok, StringCharacterIterator source,Integer line) {
		// TODO Auto-generated method stub
		source.previous();
	}
	
	@Override
	public String toString(){
		return "AS5";
	}

}
