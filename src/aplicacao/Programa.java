package aplicacao;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
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
			
			List<PecaDeXadrez> captura = new ArrayList<>();
			
			
			
			while (!partidaDeXadrez.getCheckMate()) {
				try {
					UI.clearScreen();
					//UI.imprimirTabuleiro(partidaDeXadrez.getPecas()); 
					UI.imprimirPartida(partidaDeXadrez, captura);
					System.out.println();
					System.out.print("Origem: ");
					PosicaoXadrez origem = UI.lerPosicaoXadrez(sc);
					
					
					boolean[][] possiveisMovimentos = partidaDeXadrez.possiveisMovimentos(origem);
						UI.clearScreen();
						UI.imprimirTabuleiro(partidaDeXadrez.getPecas(), possiveisMovimentos);
					
	
						System.out.println();
						System.out.print("Destino: ");
						PosicaoXadrez alvo = UI.lerPosicaoXadrez(sc);
		
						PecaDeXadrez capturaPeca = partidaDeXadrez.performaceDoMovimento(origem, alvo);
						
						if (capturaPeca !=null) {
							captura.add(capturaPeca);
						}
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
			UI.clearScreen();
			UI.imprimirPartida(partidaDeXadrez, captura);
		}

					
	}