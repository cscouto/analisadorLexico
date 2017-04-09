import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;

public class AnLexico {
	private FileHandler fileHandler;

	public AnLexico(String fileName) {
		try {
			fileHandler = new FileHandler(fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Token nextToken() {
		StringBuilder dados = new StringBuilder();
		char ch = 0;
		boolean hasPoint = false;
		boolean stop = false;
		int contOp = 0;
		long lin;
		long col;
		do {
			try {
				ch = fileHandler.getNextChar();
				lin = fileHandler.getLine();
				col = fileHandler.getColumn();
				
				//pede outro character se o character for espaco em branco
				while (Character.isWhitespace(ch)){
					ch = fileHandler.getNextChar();
				}
				
				//NUM_INT
				if (Character.isDigit(ch)){
					while (Character.isDigit(ch)){
						dados.append(ch);
						ch = fileHandler.getNextChar();
						
						//NUM_FLOAT
						if (ch == '.'){
							dados.append(ch);
							ch = fileHandler.getNextChar();
							if (Character.isDigit(ch)){
								while (Character.isDigit(ch)){
									dados.append(ch);
									ch = fileHandler.getNextChar();
								}
								fileHandler.resetLastChar();
								return new Token(TokenID.NUM_FLOAT, dados.toString(), lin, col);
							}else{
								//TODO
								//erro lexico
								fileHandler.resetLastChar();
							}
						}
					}
					fileHandler.resetLastChar();
					return new Token(TokenID.NUM_INT, dados.toString(), lin, col);
				}
				
				//LITERAl
				if (ch == '\''){
					dados.append(ch);
					ch = fileHandler.getNextChar();
					while (ch != '\''){
						dados.append(ch);
						ch = fileHandler.getNextChar();
					}
					dados.append(ch);
					return new Token(TokenID.LITERAL, dados.toString(), lin, col);
				}
				
				//ID TODO
				
				//REL_OP
				if (ch == '$'){
					dados.append(ch);
					ch = fileHandler.getNextChar();
					if (ch == 'l' ||  ch == 'g'){
						dados.append(ch);
						ch = fileHandler.getNextChar();
						if (ch == 't'){
							dados.append(ch);
							return new Token(TokenID.REL_OP, dados.toString(), lin, col);
						} else
						if (ch == 'e'){
							dados.append(ch);
							return new Token(TokenID.REL_OP, dados.toString(), lin, col);
						} else {
							//erro TODO
							fileHandler.resetLastChar();
						}
					}else if (ch == 'd'){
						dados.append(ch);
						ch = fileHandler.getNextChar();
						if (ch == 'f'){
							dados.append(ch);
							return new Token(TokenID.REL_OP, dados.toString(), lin, col);
						} else {
							//erro TODO
							fileHandler.resetLastChar();
						}
					} else {
						//erro TODO
						fileHandler.resetLastChar();
					}
				}
				
				//
				
				
			} catch (IOException e) {
			}

		} while (!stop);
		return null;
	}
}
