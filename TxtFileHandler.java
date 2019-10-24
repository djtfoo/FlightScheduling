import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Control class to read and write data to and from text files.
 * @author foojingting
 * @version 1.0
 * @since 2019-04-19
 */
public class TxtFileHandler {
	
	/**
	 * Static method to read an array of DataFromText from contents of a text file.
	 * @param filepath	The relative filepath to read from.
	 * @param commentSymbol	If this symbol is the first character of the line, it is treated as a comment.
	 * @return ArrayList of the valid lines in the text file.
	 */
	public static ArrayList<String> readTxt(String filepath, char commentSymbol) {
		
		try {
            File file = new File(filepath);
            Scanner sc = new Scanner(file);
            
            ArrayList<String> strList = new ArrayList<String>();
            while (sc.hasNextLine())
            {
            	String line = sc.nextLine();
            	if (line.charAt(0) != commentSymbol)
            		strList.add(line);
            }
    		sc.close();
            
            // check if strList is empty - means CSV file is empty
            if (strList.size() == 0) {
            	//PrintToConsole.printLine("File: " + filepath + " is empty!");
				System.out.println("File: " + filepath + " is empty!");
            	return null;
            }
    		return strList;
		}
		catch (FileNotFoundException ex) {
			ex.printStackTrace();
            System.out.println("File not found: " + ex.getMessage());
            return null;
        }
	}
	
	/**
	 * Static method to write contents to a text file.
	 * Would automatically generate the file if it does not exist in the directory yet.
	 * @param filepath	The relative filepath to read from.
	 * @param contents	The contents to write to the text file.
	 * @return Whether write to specified text file was successful.
	 */
	public static boolean writeToTxt(String filepath, String contents) {

		PrintWriter out = null;
		try {
			// create a new file if it does not exist yet
			File file = new File(filepath);
			file.getParentFile().mkdirs();	// create parent directories if it does not exist yet
			file.createNewFile();
			
			out = new PrintWriter(new FileWriter(filepath));
			out.println(contents);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (out != null)
				out.close();
		}
		
		return true;
	}
}
