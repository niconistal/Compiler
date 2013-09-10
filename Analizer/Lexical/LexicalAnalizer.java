/**
 * 
 */
package Lexical;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.StringCharacterIterator;
import java.util.ArrayList;

/**
 * @author niconistal
 *
 */
public class LexicalAnalizer {
	
	private static final char LINE_BREAK = '\n'; //Our static definition of line break
	
	protected StringCharacterIterator source;
	
	/**
	 * This is the default constructor. Takes a path and initializes the Lexical Analizer.
	 * @param path the path of the source code.
	 * 
	 */
	public LexicalAnalizer(String path){
		try {
			source = new StringCharacterIterator(readFile(path,StandardCharsets.US_ASCII));
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
		Token theToken = new Token();
		
		
		
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
		for (String s : lineList){
			retString += s;
			retString += LINE_BREAK;
		}
		return retString;
		
	}
}
