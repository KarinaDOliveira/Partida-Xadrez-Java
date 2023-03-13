package xadrez.pecas;

import tabuleiroJogo.Posicao;
import tabuleiroJogo.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaDeXadrez;

public class Torre extends PecaDeXadrez{

	public Torre(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
		
	}
	
	@Override
	
	public String toString() {
		return "T";
	}

	@Override
	public boolean[][] possiveisMovimentos() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()]; //mesma dimenção do tabuleiro
		
		Posicao p = new Posicao(0, 0); 
		
		//logica para movimentar se a cima 
		p.setValues(posicao.getLinha() - 1, posicao.getColuna());
		
		//enquanto a posição P existir e não tiver uma peça la, será possivel mover pra la
		while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().haUmaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha()-1);
		}
		
		if(getTabuleiro().posicaoExistente(p) && haUmaPecaDoOpnente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			
		}
		//logica para movimentar se a Esquerda
				p.setValues(posicao.getLinha(), posicao.getColuna() -1);
				
				//enquanto a posição P existir e não tiver uma peça la, será possivel mover pra la
				while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().haUmaPeca(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
					p.setColuna(p.getColuna() - 1);
				}
				
				if(getTabuleiro().posicaoExistente(p) && haUmaPecaDoOpnente(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
					
				}
				
				//logica para movimentar se a Direita
				p.setValues(posicao.getLinha(), posicao.getColuna() +1);
				
				//enquanto a posição P existir e não tiver uma peça la, será possivel mover pra la
				while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().haUmaPeca(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
					p.setColuna(p.getColuna() + 1);
				}
				
				if(getTabuleiro().posicaoExistente(p) && haUmaPecaDoOpnente(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
					
				}
				
				//logica para movimentar se a baixo
				p.setValues(posicao.getLinha() + 1, posicao.getColuna());
				
				//enquanto a posição P existir e não tiver uma peça la, será possivel mover pra la
				while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().haUmaPeca(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
					p.setLinha(p.getLinha() + 1);
				}
				
				if(getTabuleiro().posicaoExistente(p) && haUmaPecaDoOpnente(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
					
				}				
				
		return mat;
	}

}
