package xadrez;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.text.Position;

import tabuleiroJogo.Peca;
import tabuleiroJogo.Posicao;
import tabuleiroJogo.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaDeXadrez {
	
	
	private int turno;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;
	private boolean check;

//cria��o de lista para armazenamento das pe�as que forem capturadas e para as pe�as contidas no tabuleiro
	private List<Peca> pecasNoTabuleiro = new ArrayList<>();
	private List<Peca> capturaDePecas = new ArrayList<>();
	


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
	
	public boolean getCheck() {
		return check;
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
	
	//declara��o dos movimentos possiveis dentro da matriz booleana 
	public boolean [][] possiveisMovimentos(PosicaoXadrez posicaoOrigem) {
		Posicao posicao = posicaoOrigem.toPosicao();
		validacaoPosicaoOrigem(posicao);
		return tabuleiro.peca(posicao).possiveisMovimentos();
		
	}
	//performace do movimento criando a posi��o origem e a posi��o alvo de destino
	public PecaDeXadrez performaceDoMovimento(PosicaoXadrez posicaoOrigem, PosicaoXadrez posicaoAlvo) {
		//criando uma variavel para a posi��o instanciando a variavel do objeto 
		Posicao origem = posicaoOrigem.toPosicao();//convertendo na matriz
		Posicao alvo = posicaoAlvo.toPosicao();
		validacaoPosicaoOrigem(origem);//responsavel por validar a posi��o de origem
		validacaoPosicaoAlvo(origem, alvo);
		Peca capturaPeca = fazerMover(origem, alvo);
		
		if (testCheck(jogadorAtual)) {
			desfazMovimento(origem, alvo, capturaPeca);
			throw new XadrexExcecao("voce nao pode se colocar em check!");
		}

		check = (testCheck(oponente(jogadorAtual))) ? true : false;
		
		
		proximoTurno();
		return (PecaDeXadrez)capturaPeca;
	}
	
	private void desfazMovimento(Posicao origem, Posicao alvo, Peca capturaPeca) {
		Peca p = tabuleiro.removePeca(alvo);
		tabuleiro.lugarPeca(p, origem);

		if (capturaPeca != null) {
			tabuleiro.lugarPeca(capturaPeca, alvo);
			capturaDePecas.remove(capturaPeca);
			pecasNoTabuleiro.add(capturaPeca);
		}
	}
//metodo de fazer mover da posi��o origem para posi��o alvo
	private Peca fazerMover(Posicao origem, Posicao alvo) {
		Peca p = tabuleiro.removePeca(origem);
		Peca capturaPeca = tabuleiro.removePeca(alvo);
		tabuleiro.lugarPeca(p, alvo);
		//se a captura de pe�a for diferente de nulo remover pe�a do tabuleiro e adicionar na lista de pe�as capturadas
		
		if (capturaPeca != null) {
			pecasNoTabuleiro.remove(capturaPeca);
			capturaDePecas.add(capturaPeca);
		}
		
		return capturaPeca;
	}
	
	
	//responsavel por validar a posi��o de origem
	private void validacaoPosicaoOrigem(Posicao posicao) {
		if (!tabuleiro.haUmaPeca(posicao)) {//nega��o, se a pe�a n�o existir na oriem dever� exibir o erro 
			throw new XadrexExcecao("N�o existe uma pe�a na oriem da posi��o!");
		}
		
		
		if (jogadorAtual != ((PecaDeXadrez)tabuleiro.peca(posicao)).getCor()) {
			throw new XadrexExcecao("A peca selecionada nao e sua");
		}
		
		if (!tabuleiro.peca(posicao).haUmPossivelMovimento()) {
			throw new XadrexExcecao("N�o existe movimento possivel para peca escolida");
			
		}
	}

		private void validacaoPosicaoAlvo(Posicao origem, Posicao alvo) {
			if(!tabuleiro.peca(origem).possivelMovimento(alvo)) {
				throw new XadrexExcecao("A peca escolida n�o pode se mover para o destino selecionado");
				
			}
		}
		
		private void proximoTurno() {
			turno++;
			jogadorAtual = (jogadorAtual == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
		}
	
		
		
		private Cor oponente(Cor cor) {
			return (cor == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
		}

		private PecaDeXadrez rei(Cor cor) {
			List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez)x).getCor() == cor).collect(Collectors.toList());
			for (Peca p : list) {
				if (p instanceof Rei) {
					return (PecaDeXadrez)p;
				}
			}
			throw new IllegalStateException("Nao ha " + cor + " Rei no tabuleiro");
		}

		private boolean testCheck(Cor cor) {
			Posicao reiPosicao = rei(cor).getPosicaoXadrez().toPosicao();
			List<Peca> pecaDoOponente = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez)x).getCor() == oponente(cor)).collect(Collectors.toList());
			for (Peca p : pecaDoOponente ) {
				boolean[][] mat = p.possiveisMovimentos();
				if (mat[reiPosicao.getLinha()][reiPosicao.getColuna()]) {
					return true;
				}
			}
			return false;
		}

		
		
		
	private void lugarNovaPeca(char coluna, int linha, PecaDeXadrez peca) {
		tabuleiro.lugarPeca(peca, new PosicaoXadrez(coluna, linha).toPosicao());
		pecasNoTabuleiro.add(peca); //incluido para deixar a pe�a do oponete no lugar da pe�a capturada 
		
	}

	
	//Localiza��o das pe�as no inicio da partida
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