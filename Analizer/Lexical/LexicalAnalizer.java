/**
 * 
 */
package Lexical;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



/**
 * @author niconistal
 *
 */
public class LexicalAnalizer {
	
	private static final char LINE_BREAK = '\n'; //Our static definition of line break
	
	protected static final int STATEQ = 7;
	protected StringCharacterIterator source;
	protected TransitionMatrix transitionMatrix;
	
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
	
	public void initializeMatrix() throws IOException{
		FileInputStream file = new FileInputStream(new File("transitionMatrix.xls"));
	     
	    //Get the workbook instance for XLS file 
	    HSSFWorkbook workbook = new HSSFWorkbook(file);
	 
	    //Get first sheet from the workbook
	    HSSFSheet sheet = workbook.getSheetAt(0);
	     
	    //Iterate through each rows from first sheet
	    Iterator<Row> rowIterator = sheet.iterator();
	    int rowIndex = 0;
	    int columnIndex;
	    String characterColumn = "";
	    ArrayList<String> charRow = new ArrayList<String>();
	    //get the first row, the one with the characters
	    while(rowIterator.hasNext() && rowIndex < 1) {
	    	int cellIndex = 1;
	    	Row row = rowIterator.next();
	    	Iterator<Cell> cellIterator = row.cellIterator();
	        while(cellIterator.hasNext()) {
	        	Cell cell = cellIterator.next();
	        	cell.setCellType(Cell.CELL_TYPE_STRING);
	        	if(cellIndex % 2 == 0) {
		            charRow.add(cell.getStringCellValue());
	        	}
	        	cellIndex++;
	        }
	        rowIndex++;
	    }
	    while(rowIterator.hasNext()) {
	        Row row = rowIterator.next();
	        //For each row, iterate through each columns
	        Iterator<Cell> cellIterator = row.cellIterator();
	        while(cellIterator.hasNext()) {
	            Cell cell = cellIterator.next();
	            columnIndex = cell.getColumnIndex();
	            System.out.print(cell.getNumericCellValue() + "\t\t");
	            transitionMatrix.setCellValue(rowIndex, charRow.get(columnIndex), 
	            new TransitionCell( Integer.parseInt(cell.getStringCellValue()) , null ));
	            cellIterator.next();
	        }
	        System.out.println("");
	    }
	    file.close();
		
		
		
//		transitionMatrix = new TransitionMatrix(STATEQ);
//		//Add the cells corresponding to the initial state ( 0 )
//		
//		transitionMatrix.setCellValue(0, " ", new TransitionCell( 0 , null ) );
//		transitionMatrix.setCellValue(0 , "\n", new TransitionCell( 0 , null ));
		
		
	}
}
