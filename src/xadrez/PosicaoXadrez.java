package xadrez;

import tabuleiroJogo.Posicao;

public class PosicaoXadrez {
	
	private char coluna;
	private int linha;
	
	public PosicaoXadrez(char coluna, int linha) {
		if (coluna < 'a' || coluna > 'h' || linha < 1 || linha > 8) {
			throw new XadrexExcecao("Erro instacia��o da posi��o. Os valores validos s�o de a1 a h8");
		}
		this.coluna = coluna;
		this.linha = linha;
	}

	public char getColuna() {
		return coluna;
	}

	public int getLinha() {
		return linha;
	}

	
	protected Posicao toPosicao() {
		return new Posicao (8 - linha, coluna - 'a');
	}
	
	//defini��o da posi��o no tabuleiro de xadrez come�ando sempre por informar a coluna e depois a linha
	protected static PosicaoXadrez posicaofrente(Posicao posicao) {
		return new PosicaoXadrez((char)('a'+ posicao.getColuna()), 8 - posicao.getLinha());
	}
	
	@Override
	
	public String toString() {
		return "" + coluna + linha;
	}
	
	
	
}
