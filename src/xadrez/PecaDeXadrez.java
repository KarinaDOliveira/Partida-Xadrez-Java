package xadrez;

import tabuleiroJogo.Peca;
import tabuleiroJogo.Posicao;
import tabuleiroJogo.Tabuleiro;

public abstract class PecaDeXadrez extends Peca {
	
	private Cor cor;

	public PecaDeXadrez(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
	}
	
	
	protected boolean haUmaPecaDoOpnente(Posicao posicao) {
		PecaDeXadrez p = (PecaDeXadrez)getTabuleiro().peca(posicao);
		return p!=null && p.getCor() != cor;
	}
	

}
