/**
 * 
 */
package Lexical;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;




/**
 * @author niconistal
 *
 */
public class LexicalAnalizer {
	
	public static final char LINE_BREAK = '\n'; //Our static definition of line break
	public static final char TAB = '\t';
	
	protected static final int STATEQ = 8;
	protected StringCharacterIterator source;
	protected TransitionMatrix transitionMatrix;
	protected int state;
	protected static final int INITIAL_STATE = 0;
	protected static final int FINAL_STATE = 13;
	protected static final String EOF = "#";
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
			this.initializeMatrix();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			String theFileText = readFile(path,StandardCharsets.US_ASCII);
			source = new StringCharacterIterator(theFileText);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @return theToken the next Token
	 */
	public Token getToken(){
		state = INITIAL_STATE;	
		Token theToken = null;	
		while (state != FINAL_STATE){
			if (state == INITIAL_STATE){
				theToken = new Token();
			}
			String character = CharacterInterpreter.getInterpretedChar(Character.toString(source.current()));
			System.out.println("State: "+state);
			System.out.println("Character: "+character);
			TransitionCell currentCell = (TransitionCell) this.transitionMatrix.getCellValue(state, character);
			state = currentCell.getNextState();
			if (currentCell.getActions() != null){
				for (ISemanticAction sa : currentCell.getActions()){
					System.out.println(sa.toString());
					sa.performAction(theToken, source, line);
				}
			}
			
			source.next();
		}
		
		
		return theToken;
		
	}
	
	
	/**
	 * 
	 * Takes a path and returns the a string with the contents including cross platform new lines.
	 * 
	 * @param path is the string path to the file you want to open, its relative to the project path.
	 * @param encoding, the encoding that you want the text to be
	 * @return a String with the new lines as '\n'
	 * @throws IOException
	 * 
	 * 
	 */
	private static String readFile(String path, Charset encoding) throws IOException{
		ArrayList<String> lineList =  (ArrayList<String>) Files.readAllLines(Paths.get(path),encoding);
		String retString = "";
		lineList.add(EOF);
		for (String s : lineList){
			retString += s;
			retString += LINE_BREAK;
		}
		return retString;
		
	}
	
	protected ArrayList<ISemanticAction> parseSemanticActions(String actions){
		ArrayList<ISemanticAction> parsedActions = new ArrayList<ISemanticAction>();
		
		String actionSeparator = " ";
		String[] stringActions = actions.split(actionSeparator);
		for(String ac : stringActions){
			ISemanticAction acAction;
			switch (ac){
				
				case "AS1" :	acAction = new TokenInitializer();
								break;
					
				case "AS2" : 	acAction = new CharacterAdder();
								break;
				
				case "AS3" : 	acAction = new SymbolTableHandler();
								break;
				
				case "AS4" : 	acAction = new RangeChecker();
								break;
					
				case "AS5" : 	acAction = new CharacterReturner();
								break;
				
				case "AS6" :	acAction = new CharacterTruncator();
								break;
						
				case "AS9" :	acAction = new SingleQuoteAdder();
								break;
							
				case "AS10" : 	acAction = new LineCounter();
								break;
				
				case "AS11" : 	acAction = new CharchainTokenSetter();
								break;
				
				case "AS12" : 	acAction = new IDTokenSetter();
								break;
				
				case "AS13" : 	acAction = new CteTokenSetter();
								break;
								
				case "AS14" : 	acAction = new LiteralTokenSetter();
								break;
								
				
								
									
				default    :	acAction = null;
			}
			parsedActions.add(acAction);
		}
		
		return parsedActions;
	}
	
	public void initializeMatrix() throws IOException{
		
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ":";
		String cellSplitBy = "@";
		
		try {
			 
			br = new BufferedReader(new FileReader(nextStatesFile));
			String[] headers = (br.readLine()).split(cvsSplitBy);
			int stateIndex = 0;
			while ((line = br.readLine()) != null) {
	 
			    // use colon as separator
				String[] matrixLine = line.split(cvsSplitBy);
				
				int characterIndex = 0;
				for(String matrixCell : matrixLine){
					String[] cellData = matrixCell.split(cellSplitBy);
					int matrixNextState = Integer.parseInt(cellData[0]);
					ArrayList<ISemanticAction> semanticActions = parseSemanticActions(cellData[1]);
					
					transitionMatrix.setCellValue(stateIndex, headers[characterIndex], new TransitionCell(matrixNextState,semanticActions));
					characterIndex++;
				}
				stateIndex++;
	 
			}
	 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	 
	  }
		

}
