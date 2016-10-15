package main;

import java.util.LinkedList;
import analisadorLexico.AnalisadorLexico;
import analisadorLexico.Token;
import analisadorSintatico.AnalisadorSintatico;

public class Principal {

	public static void main(String[] args) {
		LinkedList<Token> tokens = null;
		try {

			tokens = AnalisadorLexico.parceLexico("codigo");
						
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("An�lise L�xica: ");
		for (Token token : tokens) {
			System.out.println(token);
		}
		System.out.println();
		
		System.out.println("An�lise Sint�tica: ");
		AnalisadorSintatico as= new AnalisadorSintatico(tokens);
		as.prog();
		System.out.println();
		//as.result();
		
	}

}
