package xadrez.pecas;

import tabuleiroJogo.Posicao;
import tabuleiroJogo.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;

public class Rei extends PecaDeXadrez {

	//public Rei(Tabuleiro tabuleiro, Cor cor) {
	private PartidaDeXadrez partidaDeXadrez;	
	
	public Rei(Tabuleiro tabuleiro, Cor cor, PartidaDeXadrez partidaDeXadrez) { 
		super(tabuleiro, cor);
		this.partidaDeXadrez = partidaDeXadrez;
		
	 }

	@Override
	
	public String toString() {
		return "R";
	}
	private boolean podeMover(Posicao posicao) {
		PecaDeXadrez p = (PecaDeXadrez)getTabuleiro().peca(posicao);
		return p == null || p.getCor() != getCor();
	}
	
	//implementação do castling
	private boolean testeTorreCastlig(Posicao posicao) {
		PecaDeXadrez p = (PecaDeXadrez)getTabuleiro().peca(posicao);
		return p != null && p instanceof Torre && p.getCor() == getCor() && p.getContMovimento() == 0;
		}

	
	
	@Override
	public boolean[][] possiveisMovimentos() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()]; //mesma dimenção do tabuleiro
		

		Posicao p = new Posicao(0, 0);
		
		// acima
		p.setValues(posicao.getLinha() - 1, posicao.getColuna());
		if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		//abaixo
		p.setValues(posicao.getLinha() + 1, posicao.getColuna());
		if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// esquerda
		p.setValues(posicao.getLinha(), posicao.getColuna() - 1);
		if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// direita
		p.setValues(posicao.getLinha(), posicao.getColuna() + 1);
		if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// nw
		p.setValues(posicao.getLinha() - 1, posicao.getColuna() - 1);
		if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// ne
		p.setValues(posicao.getLinha() - 1, posicao.getColuna() + 1);
		if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// sw
		p.setValues(posicao.getLinha() + 1, posicao.getColuna() - 1);
		if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// se
		p.setValues(posicao.getLinha() + 1, posicao.getColuna() + 1);
		if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		
		//MOVIMENTOS ESPECIAIS COM O CASTING 
		
		if (getContMovimento() == 0 && !partidaDeXadrez.getCheck()) {
			
			// MOVIMENTO ESPECIAL DA TORRE AO LADO DO REI 
			
			Posicao posT1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 3);
			
				if(testeTorreCastlig(posT1)) {
					Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
					Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() + 2);
					
					if(getTabuleiro().peca(p1)== null && getTabuleiro().peca(p2) == null) {
						mat[posicao.getLinha()][posicao.getColuna() + 2] = true;
					}
					
				}
			
				// #specialmove castling queenside rook
				Posicao posT2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 4);
				if (testeTorreCastlig(posT2)) {
					Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
					Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 2);
					Posicao p3 = new Posicao(posicao.getLinha(), posicao.getColuna() - 3);
					if (getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null && getTabuleiro().peca(p3) == null) {
						mat[posicao.getLinha()][posicao.getColuna() - 2] = true;
					}
				}
			}


		
		return mat;
	}
}
