package aplicacao;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import xadrez.Cor;
import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;
import xadrez.PosicaoXadrez;

public class UI {
	
	// https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
//cores do texto
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
//cores do fundo
	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
	
	
	//LOGICA PARA LIMPAR A TELA APÓS CADA RODADA
	// https://stackoverflow.com/questions/2979383/java-clear-the-console 
		public static void clearScreen() {
			System.out.print("\033[H\033[2J");
			System.out.flush();
		}	

	//tratativa do erro ao ler a posição inserida na interação com usuario
	public static PosicaoXadrez lerPosicaoXadrez (Scanner sc) {
		try {

			//Logica para leitura no console da posição que moveremos a peça
		String s = sc.nextLine();
				char coluna = s.charAt(0);//coluna da posição da coluna é a string
				int linha = Integer.parseInt(s.substring(1));
				return new PosicaoXadrez(coluna, linha);
			}
			catch (RuntimeException e) {
				throw new InputMismatchException("Erro ao ler posição. valores validos de a1 a h8!");
			}
	}
	
	
	public static void imprimirPartida(PartidaDeXadrez partidaDeXadrez, List<PecaDeXadrez>captura) {
			imprimirTabuleiro(partidaDeXadrez.getPecas());
			System.out.println();
			imprimirCapturaDePeca(captura);
			System.out.println();
			System.out.println("Turno: " + partidaDeXadrez.getTurno());
			System.out.println("Aguardando o jogador: " + partidaDeXadrez.getJogadorAtual());
		}
	
	//criação do metodo para impressão do tabuleiro 
	public static void imprimirTabuleiro(PecaDeXadrez[][] pecas) {
		//for criado para o desenho do tabuleiro iniciado a matriz com o i=0 percorrendo as peças.length()' que retorna a quantidade de caracteres que a compõe, com incremento
		for (int i=0; i<pecas.length; i++) {
			System.out.print(8 - i + " ");
			
		    for(int j=0; j<pecas.length; j++) {
			imprimirPeca(pecas[i][j], false);
		}
		    System.out.println();
		}
		
		System.out.println("  a b c d e f g h");
		    
	}
	
	public static void imprimirTabuleiro(PecaDeXadrez[][] pecas, boolean[][] possiveisMovimentos) {
			for (int i=0; i<pecas.length; i++) {
			System.out.print(8 - i + " ");
			
		    for(int j=0; j<pecas.length; j++) {
			imprimirPeca(pecas[i][j], possiveisMovimentos[i][j]);
		}
		    System.out.println();
		}
		
		System.out.println("  a b c d e f g h");
		    
	}

	//metodo para colocar a cor do fundo para os movimentos possiveis 
	private static void imprimirPeca(PecaDeXadrez peca, boolean fundoDaTela) {
		if(fundoDaTela) {
			System.out.print(ANSI_RED_BACKGROUND );
		}
		if (peca == null) {
			System.out.print("-" + ANSI_RESET);
		}
		 else {
			 
	            if (peca.getCor() == Cor.BRANCO) {
	                System.out.print(ANSI_WHITE + peca + ANSI_RESET);
	            }
	            else {
	                System.out.print(ANSI_YELLOW + peca + ANSI_RESET);
	            }
	        }
	        System.out.print(" ");
	}
	
	private static void imprimirCapturaDePeca(List<PecaDeXadrez> captura) {
		List<PecaDeXadrez> branco = captura.stream().filter(x -> x.getCor() == Cor.BRANCO).collect(Collectors.toList());
		List<PecaDeXadrez> preto = captura.stream().filter(x -> x.getCor() == Cor.PRETO).collect(Collectors.toList());
		System.out.println("Pecas capturadas:");
		System.out.print("Branco: ");
		System.out.print(ANSI_WHITE);
		System.out.println(Arrays.toString(branco.toArray()));
		System.out.print(ANSI_RESET);
		System.out.print("Preto: ");
		System.out.print(ANSI_YELLOW);
		System.out.println(Arrays.toString(preto.toArray()));
		System.out.print(ANSI_RESET);
	}
}
