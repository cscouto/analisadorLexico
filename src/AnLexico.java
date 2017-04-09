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
			
			//comentarios //TODO
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
						ch = fileHandler.getNextChar();
					}
				}
			}

			// NUM_INT
			if (Character.isDigit(ch)) {
				while (Character.isDigit(ch)) {
					token = new Token();
					token.setLin(lin);
					token.setCol(col);
					token.setTokenCode(TokenID.NUM_INT);
					dados.append(ch);
					ch = fileHandler.getNextChar();

					// NUM_FLOAT
					if (ch == '.') {
						dados.append(ch);
						ch = fileHandler.getNextChar();
						if (Character.isDigit(ch)) {
							while (Character.isDigit(ch)) {
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
							StringBuilder erro = new StringBuilder();
							erro.append("Float invalido: ");
							erro.append(dados.toString());
							erro.append(" linha: ");
							erro.append(lin);
							erro.append(" col: ");
							erro.append(col);
							ErrorHandler.getInstance().addErro(erro.toString());
							
							token.setTokenCode(TokenID.ERROR);
							token.setLexema(dados.toString());
							return token;
						}
					}
				}
				fileHandler.resetLastChar();
				token.setLexema(dados.toString());
				return token;
			} else

			// LITERAl
			if (ch == '\'') {
				token = new Token();
				token.setLin(lin);
				token.setCol(col);
				token.setTokenCode(TokenID.LITERAL);
				dados.append(ch);
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
				token = new Token();
				token.setLin(lin);
				token.setCol(col);
				token.setTokenCode(TokenID.ID);
				dados.append(ch);
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
				token = new Token();
				token.setLin(lin);
				token.setCol(col);
				token.setTokenCode(TokenID.REL_OP);
				dados.append(ch);
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
						StringBuilder erro = new StringBuilder();
						erro.append("Op. Rel. invalido: ");
						erro.append(dados.toString());
						erro.append(" linha: ");
						erro.append(lin);
						erro.append(" col: ");
						erro.append(col);
						ErrorHandler.getInstance().addErro(erro.toString());
						
						token.setTokenCode(TokenID.ERROR);
						token.setLexema(dados.toString());
						return token;
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
						StringBuilder erro = new StringBuilder();
						erro.append("Op. Rel. invalido: ");
						erro.append(dados.toString());
						erro.append(" linha: ");
						erro.append(lin);
						erro.append(" col: ");
						erro.append(col);
						ErrorHandler.getInstance().addErro(erro.toString());
						
						token.setTokenCode(TokenID.ERROR);
						token.setLexema(dados.toString());
						return token;
					}
				} else {
					// erro
					fileHandler.resetLastChar();
					StringBuilder erro = new StringBuilder();
					erro.append("Op. Rel. invalido: ");
					erro.append(dados.toString());
					erro.append(" linha: ");
					erro.append(lin);
					erro.append(" col: ");
					erro.append(col);
					ErrorHandler.getInstance().addErro(erro.toString());
					
					token.setTokenCode(TokenID.ERROR);
					token.setLexema(dados.toString());
					return token;
				}
			}else

			// ADDSUB_OP
			if (ch == '+' || ch == '-') {
				token = new Token();
				token.setLin(lin);
				token.setCol(col);
				token.setTokenCode(TokenID.ADDSUB_OP);
				dados.append(ch);
				token.setLexema(dados.toString());
				return token;
			}else

			// MULTDIV_OP
			if (ch == '*' || ch == '/') {
				token = new Token();
				token.setLin(lin);
				token.setCol(col);
				token.setTokenCode(TokenID.MULTDIV_OP);
				dados.append(ch);
				token.setLexema(dados.toString());
				return token;
			}

			// ATTRIB_OP
			if (ch == '<') {
				token = new Token();
				token.setLin(lin);
				token.setCol(col);
				token.setTokenCode(TokenID.ATTRIB_OP);
				dados.append(ch);
				ch = fileHandler.getNextChar();
				if (ch == '-') {
					dados.append(ch);
					token.setLexema(dados.toString());
					return token;
				} else {
					// error
					fileHandler.resetLastChar();
					StringBuilder erro = new StringBuilder();
					erro.append("Op. Atrib. invalido: ");
					erro.append(dados.toString());
					erro.append(" linha: ");
					erro.append(lin);
					erro.append(" col: ");
					erro.append(col);
					ErrorHandler.getInstance().addErro(erro.toString());
					
					token.setTokenCode(TokenID.ERROR);
					token.setLexema(dados.toString());
					return token;
				}
			}else

			// TERM
			if (ch == ';') {
				token = new Token();
				token.setLin(lin);
				token.setCol(col);
				token.setTokenCode(TokenID.TERM);
				dados.append(ch);
				token.setLexema(dados.toString());
				return token;
			}else

			// L_PAR
			if (ch == '(') {
				token = new Token();
				token.setLin(lin);
				token.setCol(col);
				token.setTokenCode(TokenID.L_PAR);
				dados.append(ch);
				token.setLexema(dados.toString());
				return token;
			}else

			// R_PAR
			if (ch == ')') {
				dados.append(ch);
				token = new Token();
				token.setLin(lin);
				token.setCol(col);
				token.setTokenCode(TokenID.R_PAR);
				dados.append(ch);
				token.setLexema(dados.toString());
				return token;
			} else {
				token = new Token();
				dados.append(ch);
				StringBuilder erro = new StringBuilder();
				erro.append("Character invalido: ");
				erro.append(dados.toString());
				erro.append(" linha: ");
				erro.append(lin);
				erro.append(" col: ");
				erro.append(col);
				ErrorHandler.getInstance().addErro(erro.toString());
				
				token.setLin(lin);
				token.setCol(col);
				token.setTokenCode(TokenID.ERROR);
				token.setLexema(dados.toString());
				return token;
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
}
