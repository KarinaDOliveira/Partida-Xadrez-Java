package xadrez.pecas;

import tabuleiroJogo.Posicao;
import tabuleiroJogo.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaDeXadrez;

public class Peao extends PecaDeXadrez {

	
	
	public Peao(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
		// TODO Auto-generated constructor stub
	}
	
	
	
	@Override
	public boolean[][] possiveisMovimentos() {
		   boolean[][] mat = new boolean [getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		   
		   Posicao p = new Posicao(0,0);
		   if (getCor() == Cor.BRANCO) {
				p.setValues(posicao.getLinha() - 1, posicao.getColuna());
				if (getTabuleiro().posicaoExistente(p) && !getTabuleiro().haUmaPeca(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
				}
				p.setValues(posicao.getLinha() - 2, posicao.getColuna());
				Posicao p2 = new Posicao(posicao.getLinha() - 1, posicao.getColuna());
				if (getTabuleiro().posicaoExistente(p) && !getTabuleiro().haUmaPeca(p) && getTabuleiro().posicaoExistente(p2) && !getTabuleiro().haUmaPeca(p2) && getContMovimento() == 0) {
					mat[p.getLinha()][p.getColuna()] = true;
				}
				p.setValues(posicao.getLinha() - 1, posicao.getColuna() - 1);
				if (getTabuleiro().posicaoExistente(p) && haUmaPecaDoOpnente(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
				}			
				p.setValues(posicao.getLinha() - 1, posicao.getColuna() + 1);
				if (getTabuleiro().posicaoExistente(p) && haUmaPecaDoOpnente(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
				}			
				
				
			}
		   
		   
			else {
				p.setValues(posicao.getLinha() + 1, posicao.getColuna());
				if (getTabuleiro().posicaoExistente(p) && !getTabuleiro().haUmaPeca(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
				}
				p.setValues(posicao.getLinha() + 2, posicao.getColuna());
				Posicao p2 = new Posicao(posicao.getLinha() - 1, posicao.getColuna());
				if (getTabuleiro().posicaoExistente(p) && !getTabuleiro().haUmaPeca(p) && getTabuleiro().posicaoExistente(p2) && !getTabuleiro().haUmaPeca(p2) && getContMovimento() == 0) {
					mat[p.getLinha()][p.getColuna()] = true;
				}
				
				p.setValues(posicao.getLinha() + 1, posicao.getColuna() - 1);
				if (getTabuleiro().posicaoExistente(p) && haUmaPecaDoOpnente(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
				}	
				
				
				p.setValues(posicao.getLinha() + 1, posicao.getColuna() + 1);
				if (getTabuleiro().posicaoExistente(p) && haUmaPecaDoOpnente(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
				}	
			}
			return mat;
		}

		@Override
		public String toString() {
			return "P";
		}
	}
	


