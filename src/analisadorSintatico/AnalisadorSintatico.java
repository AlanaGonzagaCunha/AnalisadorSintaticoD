package analisadorSintatico;

import java.util.Iterator;
import java.util.LinkedList;
import analisadorLexico.Token;

public class AnalisadorSintatico {
	static int i;
	static boolean testa;
	static int aux;
	static String test[] = { "if", "integer", "program", "var", "array", "it", "then", "of", "repeat", "until", "read",
			"write", "do", "not", "or", "div", "mod", "end", "$" };

	static private LinkedList<Token> tokens;

	public static void result() {
		if (testa) {
			System.out.println("Analise sintática correta");
		} else {
			System.out.println("Analise sintática incorreta");

		}
	}

	public static Token getToken(int i) {
		for (i = aux; i < tokens.size(); i++) {
			return tokens.get(i);
		}
		return null;
	}

	public AnalisadorSintatico(LinkedList<Token> tokens) {
		super();
		this.i = 0;
		this.tokens = tokens;
		this.testa = false;
		this.aux = i;
	}

	public static void prog() {
		// <prog>::= program var <dec_var> begin <list_cmd> end
		if (tokens.get(i).getValor().equals("program")) {
			getToken(i++);
			if (tokens.get(i).getValor().equals("var")) {
				getToken(i++);
				dec_var();

				if (tokens.get(i).getValor().equals("begin")) {
					getToken(i++);
					list_cmd();

					if (tokens.get(i).getValor().equals("end")) {
						getToken(i++);
						testa = true;
					} else {
						testa = false;
					}
				}
			}
		}
	}

	public static void parcela() {
		// <parcela>::= <termo><parcela'>
		termo();
		parcelaLinha();
	}

	public static void termo() {
		// <termo>::= <fator><termo'>
		fator();
		termoLinha();
	}

	public static void parcelaLinha() {
		// <parcela'>::= +<termo><parcela'>|-<termo><parcela'>|or <termo>
		// <parcela'>|E
		if (tokens.get(i).getValor().equals("+") || tokens.get(i).getValor().equals("-")
				|| tokens.get(i).getValor().equals("or")) {
			getToken(i++);
			termo();
			parcelaLinha();
		}
	}

	public static void ct() {
		// <ct>::= <digito><ct'>
		digito();
		ctLinha();
	}

	public static void fator() {
		// fator::= <id><indice> | ct | "(" <exp> ")"
		if (tokens.get(i).getTipo().equals(Token.INTEGER_TOKEN)) {
			ct();
		} else if (tokens.get(i).getValor().equals("(")) {
			getToken(i++);
			exp();
			if (tokens.get(i).getValor().equals(")")) {
				getToken(i++);
			} else {
				System.out.println(") esperado! ");
			}
		} else {
			id();
			indice();
		}
	}

	public static void ctLinha() {
		// <ct'>::= <digito><ct'>|E
		if (tokens.get(i).getValor().equals(Token.INTEGER_TOKEN)) {
			getToken(i++);
			digito();
			ctLinha();
		}
	}

	public static void termoLinha() {
		// <termo'> ::= *<fator><termo'> | div<fator><termo'> |
		// mod<fator><termo'>| and<fator><termo'>| E
		if (tokens.get(i).getValor().equals("*") || tokens.get(i).getValor().equals("div")
				|| tokens.get(i).getValor().equals("mod") || tokens.get(i).getValor().equals("and")) {
			getToken(i++);
			fator();
			termoLinha();
		}
	}

	public static void id() {
		// <id> ::= <letra><id'>
		idLinha();
	}

	public static void idLinha() {
		// <id'>::=<digito><id'>|<letra><id'>|E

		if (tokens.get(i).getTipo().equals(Token.INTEGER_TOKEN)
				|| tokens.get(i).getTipo().equals(Token.IDENTIFIER_TOKEN)) {
			getToken(i++);
			idLinha();
		}
	}

	public static void digito() {
		// digito::=0|...|9
		if (tokens.get(i).getTipo().equals(Token.INTEGER_TOKEN)) {
			getToken(i++);
		} else {
			System.out.println("Digito esperado! ");
		}
	}

	public static void indice() {
		// <indice>::= "[" <ct> "]" |E
		if (tokens.get(i).getValor().equals("[")) {
			getToken(i++);
			ct();
			if (tokens.get(i).getValor().equals("]")) {
				getToken(i++);
			} else {
				System.out.println("] esperado! ");
			}
		}
	}

	public static void exp() {
		// <exp>::= (+|-|not|E) <parcela> <outra_parcela>
		if (tokens.get(i).getValor().equals("+") || tokens.get(i).getValor().equals("-")
				|| tokens.get(i).getValor().equals("not")) {
			getToken(i++);
			parcela();
			outraParcela();
		} else {
		
				parcela();
				outraParcela();
		}
	}

	public static void outraParcela() {
		// <outra_parcela::= (=|<>|>|<|<=|>=)<parcela>|E
		if (tokens.get(i).getValor().equals("=") || tokens.get(i).getValor().equals("<>")
				|| tokens.get(i).getValor().equals(">") || tokens.get(i).getValor().equals("<")
				|| tokens.get(i).getValor().equals("<=") || tokens.get(i).getValor().equals(">=")) {
			getToken(i++);
			parcela();
		}
	}

	public static void cmd() {
		// <cmd>::= <id><indice> := <exp> | if <exp> then <cmd> | <repeat>
		// <list_cmd> until <exp>|
		// begin <list_cmd> end| read "(" <id> <indice> ")" | write "("<expe>
		// ")"|
		// while "(" <exp> ")"| while <exp> do <cmd>

		if (tokens.get(i).getValor().equals("repeat")) {
			list_cmd();
			if (tokens.get(i).getValor().equals("until")) {
				exp();
			}
		} else if (tokens.get(i).getValor().equals("if")) {
			getToken(i++);
			exp();
			if (tokens.get(i).getValor().equals("then")) {
				getToken(i++);
				cmd();
			}
		} else if (tokens.get(i).getValor().equals("begin")) {
			getToken(i++);
			list_cmd();
			if (tokens.get(i).getValor().equals("end")) {
				getToken(i++);
			}
		} else if (tokens.get(i).getValor().equals("read")) {
			getToken(i++);
			if (tokens.get(i).getValor().equals("(")) {
				getToken(i++);
				id();
				indice();
				if (tokens.get(i).getValor().equals(")")) {
					getToken(i++);

				}
			}
		} else if (tokens.get(i).getValor().equals("write")) {
			getToken(i++);
			if (tokens.get(i).getValor().equals("(")) {

				getToken(i++);
				exp();
				if (tokens.get(i).getValor().equals(")")) {
					getToken(i++);

				}
			}
		} else if (tokens.get(i).getValor().equals("while")) {
			getToken(i++);
			if (tokens.get(i).getValor().equals("(")) {
				getToken(i++);
				exp();
				if (tokens.get(i).getValor().equals(")")) {
					getToken(i++);
				}
			} else {
				exp();
				if (tokens.get(i).getValor().equals("do")) {
					getToken(i++);
					cmd();
				}
			}
		} else {
			id();
			indice();
			if (tokens.get(i).getValor().equals(":")) {
				getToken(i++);

				if (tokens.get(i).getValor().equals("=")) {
					getToken(i++);
					exp();
				}
			}
		}
	}

	// chamar list_cmd
	public static void list_cmd() {
		// <list_cmd> ::= <cmd> ; <list_cmd> | <cmd>
		if (tokens.get(i).getTipo().equals(Token.IDENTIFIER_TOKEN)
				|| tokens.get(i).getTipo().equals(Token.INTEGER_TOKEN)
				|| tokens.get(i).getTipo().equals(Token.RESERVED_WORD_TOKEN)) {

			cmd();
			if (tokens.get(i).getValor().equals(";")) {
				getToken(i++);
				list_cmd();
			}
		} else {
			cmd();
		}

	}

	public static void tipo() {
		// <tipo> ::= integer| array "[" <ct> "]" of integer
		if (tokens.get(i).getValor().equals("integer")) {
			getToken(i++);
		} else if (tokens.get(i).getValor().equals("array")) {
			getToken(i++);
			if (tokens.get(i).getValor().equals("[")) {
				getToken(i++);
				ct();
				if (tokens.get(i).getValor().equals("]")) {
					getToken(i++);
					if (tokens.get(i).getValor().equals("of")) {
						getToken(i++);
						if (tokens.get(i).getValor().equals("integer")) {
							getToken(i++);
						} else {
							System.out.println("integer esperado! ");
						}
					} else {
						System.out.println("of esperado! ");
					}
				} else {
					System.out.println("] esperado! ");
				}
			}
		}
	}

	public static void dec_var() {
		// <dec_var>::=<id> ; <tipo> ; <dec_var>|E

		if (tokens.get(i).getTipo().equals(Token.INTEGER_TOKEN)
				|| tokens.get(i).getTipo().equals(Token.IDENTIFIER_TOKEN)) {
			id();
			if (tokens.get(i).getValor().equals(";")) {
				getToken(i++);
				tipo();
				if (tokens.get(i).getValor().equals(";")) {
					getToken(i++);
					dec_var();
				} else {
					System.out.println("; esperado! ");
				}
			} else {
				System.out.println("; esperado! ");
			}
		}
	}

}
