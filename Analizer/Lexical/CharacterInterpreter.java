/**
 * 
 */
package Lexical;

import java.util.regex.Pattern;

/**
 * @author ipiolanti
 *
 */
public class CharacterInterpreter {
	
	private static final String digitMatcher = "\\d";
	private static final String characterMatcher = "[a-z]|[A-Z]" ;
	
	public static String getInterpretedChar(String character) {
		 if (Pattern.matches(digitMatcher, character)) {
			return "D"; 
		 }
		 if (Pattern.matches(characterMatcher, character)) {
			return "L"; 
		 }
		 if (character.equals(String.valueOf(LexicalAnalizer.LINE_BREAK))){
			 return "\\n";
		 }
		 return character;
 
	}
}
