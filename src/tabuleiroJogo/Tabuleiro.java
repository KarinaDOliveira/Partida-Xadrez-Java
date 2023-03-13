package tabuleiroJogo;

public class Tabuleiro {

	private int linhas;
	private int colunas;
	private Peca[][] pecas;
	
	public Tabuleiro(int linhas, int colunas) {
		//programação defensiva criação das exceções
		if(linhas < 1 || colunas < 1) {
			throw new TabuleiroExcecao("Erro criando tabuleiro, é necessario que tenha pelo menos uma linha e uma coluna");
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
			throw new TabuleiroExcecao("Posicao não existe no tabuleiro");
		}
		return pecas[linha][coluna];
		
	}
	
	//posicao das peças na matriz
	public Peca peca (Posicao posicao) {
		if(!posicaoExistente(posicao)) {
			throw new TabuleiroExcecao("Posicao não existe no tabuleiro");
		}
		return pecas[posicao.getLinha()][posicao.getColuna()];
	}
	
	
	public void lugarPeca(Peca peca, Posicao posicao) {
		if(haUmaPeca(posicao))
			throw new TabuleiroExcecao("Já existe uma peça nessa posição : " +  posicao);
		pecas[posicao.getLinha()][posicao.getColuna()] = peca; //peça instaciada no tabuleiro e criado construtor
		peca.posicao = posicao;
				
	}
	
	//metodo de remoção de peças
	public Peca removePeca(Posicao posicao) {
		if(!posicaoExistente(posicao)) {
			throw new TabuleiroExcecao("Posição não existente no Tabuleiro!");
		}
		if (peca(posicao) == null) { // se a peça do tabuleiro é igual a nulo
			return null;
		}
		
		//variavel auxiliar
		Peca aux = peca(posicao);
		aux.posicao = null;
		pecas[posicao.getLinha()][posicao.getColuna()]= null; //na matriz de peças passará a ser nula
		return aux; //retorno será a peça auxiliar 
		
	}
	
	//Metodo para testar se a posicação no tabuleiro é existente.
	private boolean posicaoExistente(int linha, int coluna) {
	//função privada boleana da posicaoExistente que recebe a linha e a coluna
		//retorna se a linha >= 0 na matriz e também linha é menos que as linhas e tbm a coluna maior ou igual a zero e tbm a coluna e menor que as colunas existentes na matriz
		return linha >= 0 && linha < linhas && coluna>= 0 && coluna < colunas;
		
	}
	
	public boolean posicaoExistente(Posicao posicao) {
		return posicaoExistente(posicao.getLinha(), posicao.getColuna());
		
	}
	
	public boolean haUmaPeca(Posicao posicao) {
		if(!posicaoExistente(posicao)) {
			throw new TabuleiroExcecao("Posicao não existe no tabuleiro");
		}
		return peca(posicao) != null;
		
	}
}
