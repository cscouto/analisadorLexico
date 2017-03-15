import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileLoader {
	private int lines;
	private int cols;
	private ArrayList<Token> tokens;
	
	public FileLoader(String path){
		lines = 0;
		cols = 0;
		tokens  = new ArrayList<Token>();
		try(BufferedReader br = new BufferedReader(new FileReader(path))) {
		    String line = br.readLine();

		    while (line != null) {
		    	String [] token = line.split(" ");
		    	for (String value: token){
		    		tokens.add(new Token(0,value,lines,cols));
		    		cols++;
		    	}
		    	lines++;
		        line = br.readLine();
		    }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Token> getTokens() {
		return tokens;
	}
	
	public int getLines() {
		return lines;
	}
	
	public int getCols() {
		return cols;
	}
}
