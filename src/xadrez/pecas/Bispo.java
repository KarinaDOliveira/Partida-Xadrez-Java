package xadrez.pecas;

import tabuleiroJogo.Posicao;
import tabuleiroJogo.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaDeXadrez;

public class Bispo extends PecaDeXadrez{

	public Bispo(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
		
	}

	@Override
	public String toString() {
		return "B";
	}
		
	@Override
	public boolean[][] possiveisMovimentos() {
		   boolean [][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
				   Posicao p = new Posicao(0, 0);
	
				   p.setValues(posicao.getLinha() - 1, posicao.getColuna() - 1);
					while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().haUmaPeca(p)) {
						mat[p.getLinha()][p.getColuna()] = true;
						p.setValues(p.getLinha() - 1, p.getColuna() - 1);
					}
					if (getTabuleiro().posicaoExistente(p) && haUmaPecaDoOpnente(p)) {
						mat[p.getLinha()][p.getColuna()] = true;
					}	
					
					 p.setValues(posicao.getLinha() - 1, posicao.getColuna() + 1);
						while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().haUmaPeca(p)) {
							mat[p.getLinha()][p.getColuna()] = true;
							p.setValues(p.getLinha() - 1, p.getColuna() + 1);
						}
						if (getTabuleiro().posicaoExistente(p) && haUmaPecaDoOpnente(p)) {
							mat[p.getLinha()][p.getColuna()] = true;
						}	
						
						
					 p.setValues(posicao.getLinha() + 1, posicao.getColuna() + 1);
						while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().haUmaPeca(p)) {
							mat[p.getLinha()][p.getColuna()] = true;
							p.setValues(p.getLinha() + 1, p.getColuna() + 1);
						}
						if (getTabuleiro().posicaoExistente(p) && haUmaPecaDoOpnente(p)) {
							mat[p.getLinha()][p.getColuna()] = true;
						}
						
					 p.setValues(posicao.getLinha() + 1, posicao.getColuna() - 1);
						while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().haUmaPeca(p)) {
							mat[p.getLinha()][p.getColuna()] = true;
							p.setValues(p.getLinha() + 1, p.getColuna() - 1);
						}
						if (getTabuleiro().posicaoExistente(p) && haUmaPecaDoOpnente(p)) {
							mat[p.getLinha()][p.getColuna()] = true;
						}
						
						return mat;
	}

}
