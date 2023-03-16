package xadrez;

import tabuleiroJogo.Peca;
import tabuleiroJogo.Posicao;
import tabuleiroJogo.Tabuleiro;

public abstract class PecaDeXadrez extends Peca {
	
	private Cor cor;
	private int contMovimento;

	public PecaDeXadrez(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
	}
	
	public int getContMovimento() {
		return contMovimento;
	}
	
	public void incrementoContMovimento() {
		contMovimento++;
	}

	public void decrementoContMovimento() {
		contMovimento--;
	}
	
	public PosicaoXadrez getPosicaoXadrez() {
		return PosicaoXadrez.posicaofrente(posicao);
	}
	
	
	protected boolean haUmaPecaDoOpnente(Posicao posicao) {
		PecaDeXadrez p = (PecaDeXadrez)getTabuleiro().peca(posicao);
		return p!=null && p.getCor() != cor;
	}

	

}
