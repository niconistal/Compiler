/**
 * 
 */
package Lexical;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.StringCharacterIterator;
import utils.InputHandler;




/**
 * @author niconistal y gran elenco
 *
 */
public class LexicalAnalizer {
	
	public static final char LINE_BREAK = '\n'; //Our static definition of line break
	public static final char TAB = '\t';
	public static final String EOF = "#";
	//STATEQ is the amount of states in the finite-state machine
	protected static final int STATEQ = 8;
	//INITIAL_STATE and FINAL_STATE representes the intial and final states
	//of the finite-state machine
	protected static final int INITIAL_STATE = 0;
	protected static final int FINAL_STATE = 13;
	
	protected StringCharacterIterator source;
	protected TransitionMatrix transitionMatrix;
	protected int state;
	
	//line represents the current line in analysis, defined as an array to
	//keep the same reference between different argument passing
	protected int line[] = {1} ;
	
	protected String nextStatesFile = "next_states.csv";

	
	/**
	 * This is the default constructor. Takes a path and initializes the Lexical Analizer.
	 * @param path the path of the source code.
	 * 
	 */
	public LexicalAnalizer(String path){
		state = INITIAL_STATE;
		transitionMatrix = new TransitionMatrix(STATEQ);
		try {
			InputHandler.initializeMatrix(this.nextStatesFile,this.transitionMatrix);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			String theFileText = InputHandler.readFile(path,StandardCharsets.US_ASCII);
			source = new StringCharacterIterator(theFileText);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	/**
	 * Retrieves the next token, if there's any, of the current source code
	 * @return Token the token found
	 */
	public Token getToken(){
		state = INITIAL_STATE;	
		Token theToken = null;	
		while (state != FINAL_STATE){
			if (state == INITIAL_STATE){
				theToken = new Token(this.line[0]);
			}
			String character = CharacterInterpreter.getInterpretedChar(Character.toString(source.current()));
			TransitionCell currentCell = (TransitionCell) this.transitionMatrix.getCellValue(state, character);
			state = currentCell.getNextState();
			if (currentCell.getActions() != null){
				for (ISemanticAction sa : currentCell.getActions()){
					sa.performAction(theToken, source, line);
				}
			}
			
			source.next();
		}
		return theToken;
	}
	/**
	 * 
	 * @return the current line
	 * 
	 */
	public int getLine(){
		return this.line[0];
	}
}
