package xadrez.pecas;

import tabuleiroJogo.Posicao;
import tabuleiroJogo.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;

public class Peao extends PecaDeXadrez {

	
	
	//public Peao(Tabuleiro tabuleiro, Cor cor) {
	private PartidaDeXadrez partidaDeXadrez;
	
	public Peao(Tabuleiro tabuleiro, Cor cor, PartidaDeXadrez partidaDeXadrez) {
		super(tabuleiro, cor);
		this.partidaDeXadrez = partidaDeXadrez;
		
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
				
				// #specialmove en passant white
				if (posicao.getLinha() == 3) {
					Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
					if (getTabuleiro().posicaoExistente(esquerda) && haUmaPecaDoOpnente(esquerda) && getTabuleiro().peca(esquerda) == partidaDeXadrez.getPassanteVulneravel()) {
						mat[esquerda.getLinha() - 1][esquerda.getColuna()] = true;
					}
					Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
					if (getTabuleiro().posicaoExistente(direita) && haUmaPecaDoOpnente(direita) && getTabuleiro().peca(direita) == partidaDeXadrez.getPassanteVulneravel()) {
						mat[direita.getLinha() - 1][direita.getColuna()] = true;
					}
					
				}
				
			}
		   
		   
			else {
				p.setValues(posicao.getLinha() + 1, posicao.getColuna());
				if (getTabuleiro().posicaoExistente(p) && !getTabuleiro().haUmaPeca(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
				}
				p.setValues(posicao.getLinha() + 2, posicao.getColuna());
				Posicao p2 = new Posicao(posicao.getLinha() + 1, posicao.getColuna());
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
				
				
				// #specialmove passante PRETO
				if (posicao.getLinha() == 4) {
					Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
					if (getTabuleiro().posicaoExistente(esquerda) && haUmaPecaDoOpnente(esquerda) && getTabuleiro().peca(esquerda) == partidaDeXadrez.getPassanteVulneravel()) {
						mat[esquerda.getLinha() + 1][esquerda.getColuna()] = true;
					}
					Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
					if (getTabuleiro().posicaoExistente(direita) && haUmaPecaDoOpnente(direita) && getTabuleiro().peca(direita) == partidaDeXadrez.getPassanteVulneravel()) {
						mat[direita.getLinha() + 1][direita.getColuna()] = true;
					}
				}			
				
			}
			return mat;
		}

		@Override
		public String toString() {
			return "P";
		}
	}
	


