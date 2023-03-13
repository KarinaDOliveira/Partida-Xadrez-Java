package xadrez;


import tabuleiroJogo.Peca;
import tabuleiroJogo.Posicao;
import tabuleiroJogo.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaDeXadrez {
	
	private Tabuleiro tabuleiro;


	//tamanho do tabuleiro de xadrez 8 x 8
	public PartidaDeXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		inicioPartida();
		
	}
	
	public PecaDeXadrez[][] getPecas(){
		PecaDeXadrez[][] mat = new PecaDeXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i=0; i<tabuleiro.getLinhas(); i++) {
			for (int j=0; j<tabuleiro.getColunas(); j++) {
				mat[i][j] = (PecaDeXadrez)tabuleiro.peca(i, j);			
				
			}
		 }
			
	 	   return mat;
	 }
	
	
	//performace do movimento criando a posição origem e a posição alvo de destino
	public PecaDeXadrez performaceDoMovimento(PosicaoXadrez posicaoOrigem, PosicaoXadrez posicaoAlvo) {
		//criando uma variavel para a posição instanciando a variavel do objeto 
		Posicao origem = posicaoOrigem.toPosicao();//convertendo na matriz
		Posicao alvo = posicaoAlvo.toPosicao();
		validacaoPosicaoOrigem(origem);//responsavel por validar a posição de origem
		Peca capturaPeca = fazerMover(origem, alvo);
		return (PecaDeXadrez)capturaPeca;
	}
//metodo de fazer mover da posição origem para posição alvo
	private Peca fazerMover(Posicao origem, Posicao alvo) {
		Peca p = tabuleiro.removePeca(origem);
		Peca capturaPeca = tabuleiro.removePeca(alvo);
		tabuleiro.lugarPeca(p, alvo);
		return capturaPeca;
	}
	//responsavel por validar a posição de origem
	private void validacaoPosicaoOrigem(Posicao posicao) {
		if (!tabuleiro.haUmaPeca(posicao)) {//negação, se a peça não existir na oriem deverá exibir o erro 
			throw new XadrexExcecao("Não existe uma peça na oriem da posição!");
		}
		if (!tabuleiro.peca(posicao).haUmPossivelMovimento()) {
			throw new XadrexExcecao("Não existe movimento possivel para peca escolida");
			
		}
	}

	
	private void lugarNovaPeca(char coluna, int linha, PecaDeXadrez peca) {
		tabuleiro.lugarPeca(peca, new PosicaoXadrez(coluna, linha).toPosicao());
		
	}

	
	//Localização das peças no inicio da partida
	private void inicioPartida() {
	
	    lugarNovaPeca('c', 1, new Torre(tabuleiro, Cor.BRANCO));
        lugarNovaPeca('c', 2, new Torre(tabuleiro, Cor.BRANCO));
        lugarNovaPeca('d', 2, new Torre(tabuleiro, Cor.BRANCO));
        lugarNovaPeca('e', 2, new Torre(tabuleiro, Cor.BRANCO));
        lugarNovaPeca('e', 1, new Torre(tabuleiro, Cor.BRANCO));
        lugarNovaPeca('d', 1, new Rei(tabuleiro, Cor.BRANCO));

        lugarNovaPeca('c', 7, new Torre(tabuleiro, Cor.PRETO));
        lugarNovaPeca('c', 8, new Torre(tabuleiro, Cor.PRETO));
        lugarNovaPeca('d', 7, new Torre(tabuleiro, Cor.PRETO));
        lugarNovaPeca('e', 7, new Torre(tabuleiro, Cor.PRETO));
        lugarNovaPeca('e', 8, new Torre(tabuleiro, Cor.PRETO));
        lugarNovaPeca('d', 8, new Rei(tabuleiro, Cor.PRETO));
	    
	}
}