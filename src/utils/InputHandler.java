package utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import Lexical.ISemanticAction;
import Lexical.LexicalAnalizer;
import Lexical.SemanticActionFactory;
import Lexical.TransitionCell;
import Lexical.TransitionMatrix;

/**
 * InputHandler will manage handling and initialization of structures
 * given the source inputs
 * @author Inti
 *
 */
public class InputHandler {
	
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
	public static String readFile(String path, Charset encoding) throws IOException{
		ArrayList<String> lineList =  (ArrayList<String>) Files.readAllLines(Paths.get(path),encoding);
		String retString = "";
		lineList.add(LexicalAnalizer.EOF);
		for (String s : lineList){
			retString += s;
			retString += LexicalAnalizer.LINE_BREAK;
		}
		return retString;
		
	}
	/**
	 * Initializes the transition matrix given the finite-state machine description
	 * @param [nextStatesFile] the finite-state machine description
	 * @param transitionMatrix 
	 * @throws IOException
	 */
	public static void initializeMatrix(String nextStatesFile, TransitionMatrix transitionMatrix) throws IOException{
		
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
					ArrayList<ISemanticAction> semanticActions = SemanticActionFactory.parseSemanticActions(cellData[1]);
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
