package tabuleiroJogo;

public class Tabuleiro {

	private int linhas;
	private int colunas;
	private Peca[][] pecas;
	
	public Tabuleiro(int linhas, int colunas) {
		//programa��o defensiva cria��o das exce��es
		if(linhas < 1 || colunas < 1) {
			throw new TabuleiroExcecao("Erro criando tabuleiro, � necessario que tenha pelo menos uma linha e uma coluna");
		}
		this.linhas = linhas;
		this.colunas = colunas;
		pecas = new Peca[linhas][colunas];
	}

	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}

		
	public Peca peca(int linha, int coluna) {
		if(!posicaoExistente(linha, coluna)) {
			throw new TabuleiroExcecao("Posicao n�o existe no tabuleiro");
		}
		return pecas[linha][coluna];
		
	}
	
	//posicao das pe�as na matriz
	public Peca peca (Posicao posicao) {
		if(!posicaoExistente(posicao)) {
			throw new TabuleiroExcecao("Posicao n�o existe no tabuleiro");
		}
		return pecas[posicao.getLinha()][posicao.getColuna()];
	}
	
	
	public void lugarPeca(Peca peca, Posicao posicao) {
		if(haUmaPeca(posicao))
			throw new TabuleiroExcecao("J� existe uma pe�a nessa posi��o : " +  posicao);
		pecas[posicao.getLinha()][posicao.getColuna()] = peca; //pe�a instaciada no tabuleiro e criado construtor
		peca.posicao = posicao;
				
	}
	
	//metodo de remo��o de pe�as
	public Peca removePeca(Posicao posicao) {
		if(!posicaoExistente(posicao)) {
			throw new TabuleiroExcecao("Posi��o n�o existente no Tabuleiro!");
		}
		if (peca(posicao) == null) { // se a pe�a do tabuleiro � igual a nulo
			return null;
		}
		
		//variavel auxiliar
		Peca aux = peca(posicao);
		aux.posicao = null;
		pecas[posicao.getLinha()][posicao.getColuna()]= null; //na matriz de pe�as passar� a ser nula
		return aux; //retorno ser� a pe�a auxiliar 
		
	}
	
	//Metodo para testar se a posica��o no tabuleiro � existente.
	private boolean posicaoExistente(int linha, int coluna) {
	//fun��o privada boleana da posicaoExistente que recebe a linha e a coluna
		//retorna se a linha >= 0 na matriz e tamb�m linha � menos que as linhas e tbm a coluna maior ou igual a zero e tbm a coluna e menor que as colunas existentes na matriz
		return linha >= 0 && linha < linhas && coluna>= 0 && coluna < colunas;
		
	}
	
	public boolean posicaoExistente(Posicao posicao) {
		return posicaoExistente(posicao.getLinha(), posicao.getColuna());
		
	}
	
	public boolean haUmaPeca(Posicao posicao) {
		if(!posicaoExistente(posicao)) {
			throw new TabuleiroExcecao("Posicao n�o existe no tabuleiro");
		}
		return peca(posicao) != null;
		
	}
}
