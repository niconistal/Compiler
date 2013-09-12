/**
 * 
 */
package Lexical;

import java.text.StringCharacterIterator;

/**
 * @author niconistal
 *
 */
public class CharacterAdder implements ISemanticAction{

	public void performAction(Token tok, StringCharacterIterator source,Integer line) {
		tok.addCharacter(source.current());
	}
	
	public String toString(){
		return "AS2";
	}
}
