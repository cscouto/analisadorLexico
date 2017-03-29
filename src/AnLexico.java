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
		int contOp = 0;
		Token token = new Token();
		do {
			try {
				ch = fileHandler.getNextChar();
				token.setLin(fileHandler.getLine());
				token.setCol(fileHandler.getColumn());
				if (Character.isWhitespace(ch)){
					return token;
				}
					
				dados.append(ch);
				
				// numero
				if (Character.isDigit(dados.charAt(0))) {
					token.setTokenCode(TokenID.NUM_INT);
					if (!Character.isDigit(ch)) {
						if (ch != '.') {
							// erro no token (valor invalido)
						} else {
							if (hasPoint) {
								// erro no token (2 pontos)
							} else {
								// float
								hasPoint = true;
								token.setTokenCode(TokenID.NUM_FLOAT);
							}
						}
					}

				} else
				// literal
				if (dados.charAt(0) == '\'') {
					if (!Character.isLetter(ch) || ch != '\'') {
						// erro literal
					}
				} else
				// id ou palavra reservada
				if (Character.isLetter(dados.charAt(0))) {
					if (!Character.isLetter(ch)) {
						if (ch != '_') {
							// erro token
						} else {
							// id
						}
					}
				} else
				// id
				if (dados.charAt(0) == '_') {
					if (!Character.isLetter(ch) || ch != '_') {
						// erro token
					}
				} else
				// rel_op
				if (dados.charAt(0) == '$') {
					if (contOp == 1) {
						if (ch != 'l' || ch != 'g' || ch != 'e' || ch != 'd') {
							// erro token
						}
					}
					if (contOp == 2) {
						if (ch == 'e') {
							if (dados.charAt(contOp - 1) != 'l' || dados.charAt(contOp - 1) != 'g') {
								// erro
							}
						} else if (ch == 'q') {
							if (dados.charAt(contOp - 1) != 'e') {
								// erro
							}
						} else if (ch == 'f') {
							if (dados.charAt(contOp - 1) != 'd') {
								// erro
							}
						} else {
							// erro
						}
					}
					contOp++;
				} else
				// ADDSUB_OP
				if (dados.charAt(0) == '+' || dados.charAt(0) == '-') {
					// return token
				} else
				// MULTDIV_OP
				if (dados.charAt(0) == '*' || dados.charAt(0) == '/') {
					// return token
				} else
				// 8 ATTRIB_OP
				if (dados.charAt(0) == '<') {
					if (dados.length() == 2) {
						if (dados.charAt(1) != '-') {
							// erro
						} else {
							// return token
						}
					}
				} else
				// TERM
				if (dados.charAt(0) == ';') {
					// return token
				} else
				// L_PAR
				if (dados.charAt(0) == '(') {
					// return token
				} else
				// R_PAR
				if (dados.charAt(0) == ')') {
					// return token
				} else {
					// character invalido
				}
			} catch (EOFException e) {
				try {
					fileHandler.resetLastChar();
				} catch (IOException e1) {
				}
				break;
			} catch (IOException e) {
			}
			token.setLexema(dados.toString());
		} while (!Character.isWhitespace(ch));
		return token;
	}
}
