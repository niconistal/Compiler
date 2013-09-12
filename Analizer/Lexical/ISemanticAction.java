/**
 * 
 */
package Lexical;

import java.text.StringCharacterIterator;

/**
 * @author niconistal
 *
 */
public interface ISemanticAction {

	public abstract void performAction(Token tok, StringCharacterIterator source,Integer line);
	
}