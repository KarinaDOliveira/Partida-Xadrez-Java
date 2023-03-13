package tabuleiroJogo;

public class Posicao {
	
	private int linha;
	private int coluna;
	
	//criação do contrutor, recebendo os argumentos. O construtor é um método especial para criar e inicializar um objeto criado a partir de uma classe
	public Posicao(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
	}
	//encapsulamento com o get(exibir/retornar) e set(alterar valor ou conteudo) (usado pois colocamos as variaveis no modo privado
	public int getLinha() {
		return linha;
	}
	public void setLinha(int linha) {
		this.linha = linha;
	}
	public int getColuna() {
		return coluna;
	}
	public void setColuna(int coluna) {
		this.coluna = coluna;
	}
	
	
	//aplicação do toString objeto é a classe das superClasses
	@Override //sobreposição 
	//O método toString retorna uma representação string(string é p texto ou forma a ser lida) de um objeto
	public String toString() {
		return linha + "," + coluna;
		
	}
	
	
}
