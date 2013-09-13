/**
 * 
 */
package Lexical;

import java.text.StringCharacterIterator;

/**
 * @author niconistal
 *
 *	AS2
 *	Adds a character to the token literal
 *
 */
public class CharacterAdder implements ISemanticAction{

	public void performAction(Token tok, StringCharacterIterator source,int[] line) {
		tok.addCharacter(source.current());
	}
	
	public String toString(){
		return "AS2";
	}
}
