package xadrez.pecas;

import tabuleiroJogo.Posicao;
import tabuleiroJogo.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaDeXadrez;

public class Cavaleiro extends PecaDeXadrez{

	public Cavaleiro(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "C";
	}

	private boolean podeMover(Posicao posicao) {
		PecaDeXadrez p = (PecaDeXadrez)getTabuleiro().peca(posicao);
		return p == null || p.getCor() != getCor();
	}
	@Override
	public boolean[][] possiveisMovimentos() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
			Posicao p = new Posicao(0, 0);
			
		p.setValues(posicao.getLinha() -1, posicao.getColuna() -2);
		if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		p.setValues(posicao.getLinha() - 2, posicao.getColuna() - 1);
		if(getTabuleiro().posicaoExistente(p) && podeMover(p)) {
			mat [p.getLinha()][p.getColuna()] = true; 
		}
		
		p.setValues(posicao.getLinha() - 2, posicao.getColuna() + 1);
		if(getTabuleiro().posicaoExistente(p) && podeMover(p)) {
			mat [p.getLinha()][p.getColuna()] = true; 
		}
		
		p.setValues(posicao.getLinha() - 1, posicao.getColuna() + 2);
		if(getTabuleiro().posicaoExistente(p) && podeMover(p)) {
			mat [p.getLinha()][p.getColuna()] = true; 
		}
		
		p.setValues(posicao.getLinha() + 1, posicao.getColuna() + 2);
		if(getTabuleiro().posicaoExistente(p) && podeMover(p)) {
			mat [p.getLinha()][p.getColuna()] = true; 
		}
		
		p.setValues(posicao.getLinha() + 2, posicao.getColuna() + 1);
		if(getTabuleiro().posicaoExistente(p) && podeMover(p)) {
			mat [p.getLinha()][p.getColuna()] = true; 
		}
		
		p.setValues(posicao.getLinha() + 2, posicao.getColuna() - 1);
		if(getTabuleiro().posicaoExistente(p) && podeMover(p)) {
			mat [p.getLinha()][p.getColuna()] = true; 
		}
		
		p.setValues(posicao.getLinha() + 1, posicao.getColuna() - 2);
		if(getTabuleiro().posicaoExistente(p) && podeMover(p)) {
			mat [p.getLinha()][p.getColuna()] = true; 
		}
		
		return mat;
	}

	
	
}
