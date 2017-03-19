import java.io.FileNotFoundException;

public class AnLexico {
	 private FileHandler fileHandler;
	 
	public AnLexico(String fileName) {
		try {
			fileHandler = new FileHandler(fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Token nextToken(){
		
		return null;
	}
}
