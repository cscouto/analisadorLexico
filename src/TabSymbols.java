import java.util.HashMap;
import java.util.Map;

public class TabSymbols {
	private Map<String, Token> tabela = new HashMap<String, Token>();
	private static TabSymbols instance = new TabSymbols();

	private TabSymbols() {
		tabela.put("true", new Token(TokenID.LOGIC_VALUE, "true", 0, 0));
		tabela.put("false", new Token(TokenID.LOGIC_VALUE, "false", 0, 0));
		tabela.put("not", new Token(TokenID.LOGIC_OP, "not", 0, 0));
		tabela.put("and", new Token(TokenID.LOGIC_OP, "true", 0, 0));
		tabela.put("or", new Token(TokenID.LOGIC_OP, "or", 0, 0));
		tabela.put("bool", new Token(TokenID.TYPE, "bool", 0, 0));
		tabela.put("text", new Token(TokenID.TYPE, "text", 0, 0));
		tabela.put("int", new Token(TokenID.TYPE, "int", 0, 0));
		tabela.put("float", new Token(TokenID.TYPE, "float", 0, 0));
		tabela.put("program", new Token(TokenID.PROGRAM, "program", 0, 0));
		tabela.put("end_program", new Token(TokenID.END_PROG, "end_program", 0, 0));
		tabela.put("begin", new Token(TokenID.BEGIN, "begin", 0, 0));
		tabela.put("end", new Token(TokenID.END, "end", 0, 0));
		tabela.put("if", new Token(TokenID.IF, "if", 0, 0));
		tabela.put("then", new Token(TokenID.THEN, "then", 0, 0));
		tabela.put("else", new Token(TokenID.ELSE, "else", 0, 0));
		tabela.put("for", new Token(TokenID.FOR, "for", 0, 0));
		tabela.put("while", new Token(TokenID.WHILE, "while", 0, 0));
		tabela.put("declare", new Token(TokenID.DECLARE, "declare", 0, 0));
		tabela.put("to", new Token(TokenID.TO, "to", 0, 0));
	}

	public static TabSymbols getInstance() {
		return instance;
	}

	public Map<String, Token> getTabela() {
		return tabela;
	}

	public void printTable() {
		System.out.println("\n-----Tabela de simbolos-----");
		for (Token token : tabela.values()) {
			if (token.getLin() > 0) {
				System.out.println("lexema: " + token.getLexema() + " tipo: " + token.getTokenCode() + " linha: "
						+ token.getLin() + " coluna: " + token.getCol());
			}
		}
	}

	public void installToken(Token token) {
		tabela.put(token.getLexema(), token);
	}
}
