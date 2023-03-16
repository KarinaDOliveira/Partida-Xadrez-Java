package xadrez;


import tabuleiroJogo.Peca;
import tabuleiroJogo.Posicao;
import tabuleiroJogo.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaDeXadrez {
	
	
	private int turno;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;


	//tamanho do tabuleiro de xadrez 8 x 8
	public PartidaDeXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		turno = 1;
		jogadorAtual = Cor.BRANCO;
		inicioPartida();
		
	}
	
	
	
	public int getTurno() {
		return turno;
	}
	
	public Cor getJogadorAtual() {
		return jogadorAtual;
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
	
	//declaração dos movimentos possiveis dentro da matriz booleana 
	public boolean [][] possiveisMovimentos(PosicaoXadrez posicaoOrigem) {
		Posicao posicao = posicaoOrigem.toPosicao();
		validacaoPosicaoOrigem(posicao);
		return tabuleiro.peca(posicao).possiveisMovimentos();
		
	}
	//performace do movimento criando a posição origem e a posição alvo de destino
	public PecaDeXadrez performaceDoMovimento(PosicaoXadrez posicaoOrigem, PosicaoXadrez posicaoAlvo) {
		//criando uma variavel para a posição instanciando a variavel do objeto 
		Posicao origem = posicaoOrigem.toPosicao();//convertendo na matriz
		Posicao alvo = posicaoAlvo.toPosicao();
		validacaoPosicaoOrigem(origem);//responsavel por validar a posição de origem
		validacaoPosicaoAlvo(origem, alvo);
		Peca capturaPeca = fazerMover(origem, alvo);
		proximoTurno();
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
		
		
		if (jogadorAtual != ((PecaDeXadrez)tabuleiro.peca(posicao)).getCor()) {
			throw new XadrexExcecao("A peca selecionada nao e sua");
		}
		
		if (!tabuleiro.peca(posicao).haUmPossivelMovimento()) {
			throw new XadrexExcecao("Não existe movimento possivel para peca escolida");
			
		}
	}

		private void validacaoPosicaoAlvo(Posicao origem, Posicao alvo) {
			if(!tabuleiro.peca(origem).possivelMovimento(alvo)) {
				throw new XadrexExcecao("A peca escolida não pode se mover para o destino selecionado");
				
			}
		}
		
		private void proximoTurno() {
			turno++;
			jogadorAtual = (jogadorAtual == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
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