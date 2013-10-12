package utils;
/**
 * This class will allow the parser to get the path to the source code
 * without needing to pass any parameter
 * @author Inti
 *
 */
public class PathContainer {
	
	private static String path;
	
	public static String getPath() {
		return path;
	}
	public static void setPath(String p) {
		path = p;
	}
}
