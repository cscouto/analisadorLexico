//Tiago Henrique Do Couto
//Caique Souza

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
		char ch;
		long lin;
		long col;
		Token token = null;
		try {
			ch = fileHandler.getNextChar();
			lin = fileHandler.getLine();
			col = fileHandler.getColumn();

			// pede outro character se o character for espaco em branco
			while (Character.isWhitespace(ch)) {
				ch = fileHandler.getNextChar();
			}
			
			//comentarios 
			if (ch == ':'){
				ch = fileHandler.getNextChar();
				if (ch =='{'){
					ch = fileHandler.getNextChar();
					while (ch != '}'){
						ch = fileHandler.getNextChar();
					}
					ch = fileHandler.getNextChar();
					if (ch==':'){
						//comentario correto
						token = null;
						return nextToken();
					}else{
						// erro lexico
						fileHandler.resetLastChar();
						
						trataErros(dados, lin, col);
						
						token = null;
						return nextToken();
					}
				}else{
					// erro lexico
					fileHandler.resetLastChar();
					
					trataErros(dados, lin, col);
					
					token = null;
					return nextToken();
				}
			}
			
			token = new Token();
			token.setLin(lin);
			token.setCol(col);
			dados.append(ch);
			
			// NUM_INT
			if (Character.isDigit(ch)) {
				
				token.setTokenCode(TokenID.NUM_INT);
				ch = fileHandler.getNextChar();
				if (ch == '.') {
					dados.append(ch);
					ch = fileHandler.getNextChar();
					if (Character.isDigit(ch)) {
						while (Character.isDigit(ch) || ch == 'E' || ch == '+') {
							dados.append(ch);
							ch = fileHandler.getNextChar();
						}
						fileHandler.resetLastChar();
						token.setTokenCode(TokenID.NUM_FLOAT);
						token.setLexema(dados.toString());
						return token;
					} else {
						// erro lexico
						fileHandler.resetLastChar();
						
						trataErros(dados, lin, col);
						
						token = null;
						return nextToken();
					}
				}else {
					while (Character.isDigit(ch)) {
						dados.append(ch);
						ch = fileHandler.getNextChar();
						// NUM_FLOAT
						if (ch == '.') {
							dados.append(ch);
							ch = fileHandler.getNextChar();
							if (Character.isDigit(ch)) {
								while (Character.isDigit(ch)|| ch == 'E' || ch == '+') {
									dados.append(ch);
									ch = fileHandler.getNextChar();
								}
								fileHandler.resetLastChar();
								token.setTokenCode(TokenID.NUM_FLOAT);
								token.setLexema(dados.toString());
								return token;
							} else {
								// erro lexico
								fileHandler.resetLastChar();
								
								trataErros(dados, lin, col);
								
								token = null;
								return nextToken();
							}
						}
					}
				}
				fileHandler.resetLastChar();
				token.setLexema(dados.toString());
				return token;
			} else

			// LITERAl
			if (ch == '\'') {
				token.setTokenCode(TokenID.LITERAL);
				ch = fileHandler.getNextChar();
				while (ch != '\'') {
					dados.append(ch);
					ch = fileHandler.getNextChar();
				}
				dados.append(ch);
				token.setLexema(dados.toString());
				return token;
			}else

			// ID
			if (Character.isLetterOrDigit(ch) || ch == '_') {
				token.setTokenCode(TokenID.ID);
				ch = fileHandler.getNextChar();
				while(Character.isLetterOrDigit(ch) || ch == '_'){
					dados.append(ch);
					ch = fileHandler.getNextChar();
				}
				
				fileHandler.resetLastChar();
				
				if (TabSymbols.getInstance().getTabela().containsKey(dados.toString())){
					token = TabSymbols.getInstance().getTabela().get(dados.toString());
					token.setLin(lin);
					token.setCol(col);
					return token;
				}else{
					token.setLexema(dados.toString());
					TabSymbols.getInstance().installToken(token);
					return token;
				}
			}else

			// REL_OP
			if (ch == '$') {
				token.setTokenCode(TokenID.REL_OP);
				ch = fileHandler.getNextChar();
				if (ch == 'l' || ch == 'g') {
					dados.append(ch);
					ch = fileHandler.getNextChar();
					if (ch == 't') {
						dados.append(ch);
						token.setLexema(dados.toString());
						return token;
					} else if (ch == 'e') {
						dados.append(ch);
						token.setLexema(dados.toString());
						return token;
					} else {
						// erro 
						fileHandler.resetLastChar();
						
						trataErros(dados, lin, col);
						token =  null;
						return nextToken();
					}
				} else if (ch == 'd') {
					dados.append(ch);
					ch = fileHandler.getNextChar();
					if (ch == 'f') {
						dados.append(ch);
						token.setLexema(dados.toString());
						return token;
					} else {
						// erro 
						fileHandler.resetLastChar();
						
						trataErros(dados, lin, col);
						token = null;
						return nextToken();
					}
				} else {
					// erro
					fileHandler.resetLastChar();
					
					trataErros(dados, lin, col);
					
					token = null;
					return nextToken();
				}
			}else

			// ADDSUB_OP
			if (ch == '+' || ch == '-') {
				token.setTokenCode(TokenID.ADDSUB_OP);
				token.setLexema(dados.toString());
				return token;
			}else

			// MULTDIV_OP
			if (ch == '*' || ch == '/') {
				token.setTokenCode(TokenID.MULTDIV_OP);
				token.setLexema(dados.toString());
				return token;
			}

			// ATTRIB_OP
			if (ch == '<') {
				token.setTokenCode(TokenID.ATTRIB_OP);
				ch = fileHandler.getNextChar();
				if (ch == '-') {
					dados.append(ch);
					token.setLexema(dados.toString());
					return token;
				} else {
					// error
					fileHandler.resetLastChar();
					
					trataErros(dados, lin, col);
					
					token = null;
					return nextToken();
				}
			}else

			// TERM
			if (ch == ';') {
				token.setTokenCode(TokenID.TERM);
				token.setLexema(dados.toString());
				return token;
			}else

			// L_PAR
			if (ch == '(') {
				token.setTokenCode(TokenID.L_PAR);
				token.setLexema(dados.toString());
				return token;
			}else

			// R_PAR
			if (ch == ')') {
				token.setTokenCode(TokenID.R_PAR);
				token.setLexema(dados.toString());
				return token;
			} else {
				trataErros(dados, lin, col);
				token = null;
				return nextToken();
			}
 
		} catch (IOException e){
			if (token == null) {
				return new Token(TokenID.EOF, "", 0, 0);
			}else{
				try {
					fileHandler.resetLastChar();
					if(token.getTokenCode() == TokenID.ID){
						long linha = token.getLin();
						long coluna = token.getCol();
						if (TabSymbols.getInstance().getTabela().containsKey(dados.toString())){
							token = TabSymbols.getInstance().getTabela().get(dados.toString());
							token.setLin(linha);
							token.setCol(coluna);
							return token;
						}else{
							token.setLexema(dados.toString());
							TabSymbols.getInstance().installToken(token);
							return token;
						}
					}
					token.setLexema(dados.toString());
					return token;
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return null;
	}

	private void trataErros(StringBuilder dados, long lin, long col) {
		StringBuilder erro = new StringBuilder();
		erro.append("Token invalido: ");
		erro.append(dados.toString());
		erro.append(" linha: ");
		erro.append(lin);
		erro.append(" col: ");
		erro.append(col);
		ErrorHandler.getInstance().addErro(erro.toString());
	}
}
