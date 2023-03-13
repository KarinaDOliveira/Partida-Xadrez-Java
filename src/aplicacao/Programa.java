package aplicacao;

import java.util.InputMismatchException;
import java.util.Scanner;

import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;
import xadrez.PosicaoXadrez;
import xadrez.XadrexExcecao;

public class Programa {

	public static void main(String[] args) {
		        
		    Scanner sc = new Scanner(System.in);
		
			PartidaDeXadrez partidaDeXadrez = new PartidaDeXadrez();
			//UI.imprimirTabuleiro(partidaDeXadrez.getPecas());
			
			while (true) {
				try {
					UI.clearScreen();
					UI.imprimirTabuleiro(partidaDeXadrez.getPecas()); 
					System.out.println();
					System.out.print("Origem: ");
					PosicaoXadrez origem = UI.lerPosicaoXadrez(sc);
	
					System.out.println();
					System.out.print("Destino: ");
					PosicaoXadrez alvo = UI.lerPosicaoXadrez(sc);
	
					PecaDeXadrez capturaPeca = partidaDeXadrez.performaceDoMovimento(origem, alvo);
				}
				catch(XadrexExcecao e) {
					System.out.println(e.getMessage());
					sc.nextLine();//o programa vai aguardar entrar com o enter 
				}
				catch(InputMismatchException e) {
					System.out.println(e.getMessage());
					sc.nextLine();//o programa vai aguardar entrar com o enter 
				}
			}
		}

					
	}