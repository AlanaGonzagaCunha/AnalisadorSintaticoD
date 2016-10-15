package analisadorLexico;

import java.util.LinkedList;

public class Token {

	private int linha;
	private String tipo;
	private String valor;

	public final static String RESERVED_WORD_TOKEN = "RESERVED_WORD_TOKEN";
	public final static String SYMBOL_TOKEN = "SYMBOL_TOKEN";
	public final static String OPERATOR_TOKEN = "OPERATOR_TOKEN";
	public final static String OPERATOR_TOKEN_COMP = "OPERATOR_TOKEN_COMPOUND";
	public final static String IDENTIFIER_TOKEN = "IDENTIFIER_TOKEN";
	public final static String INTEGER_TOKEN = "INTEGER_TOKEN";
	
	public Token(int linha, String tipo, String valor) {
		super();
		this.linha = linha;
		this.tipo = tipo;
		this.valor = valor;
		
	}

	public int getLinha() {
		return linha;
	}

	public void setLinha(int linha) {
		this.linha = linha;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {
		return "Token linha = " + linha + ", tipo = " + tipo + ", value = ' " + valor + " '";
	}

}
