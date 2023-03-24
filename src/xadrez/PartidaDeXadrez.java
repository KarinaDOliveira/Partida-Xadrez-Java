package xadrez;


import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiroJogo.Peca;
import tabuleiroJogo.Posicao;
import tabuleiroJogo.Tabuleiro;
import xadrez.pecas.Bispo;
import xadrez.pecas.Cavaleiro;
import xadrez.pecas.Peao;
import xadrez.pecas.Rainha;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaDeXadrez {
	
	
	private int turno;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;
	private boolean check;
	private boolean checkMate;
	private PecaDeXadrez passanteVulneravel;
	private PecaDeXadrez promocao;

//criação de lista para armazenamento das peças que forem capturadas e para as peças contidas no tabuleiro
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
	
	public boolean getCheckMate() {
		return checkMate;
	}
	
	public PecaDeXadrez getPassanteVulneravel() {
		return passanteVulneravel;
	}

	public PecaDeXadrez getPromocao() {
		return promocao;
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
		
		if (testCheck(jogadorAtual)) {
			desfazMovimento(origem, alvo, capturaPeca);
			throw new XadrexExcecao("voce nao pode se colocar em check!");
		}
		
		PecaDeXadrez moverPeca = (PecaDeXadrez)tabuleiro.peca(alvo);
		
		// #specialmove promotion
				promocao = null;
				if (moverPeca instanceof Peao) {
					if ((moverPeca.getCor() == Cor.BRANCO && alvo.getLinha() == 0) || (moverPeca.getCor() == Cor.PRETO && alvo.getLinha() == 7)) {
						promocao = (PecaDeXadrez)tabuleiro.peca(alvo);
						promocao = substiruirPecaPromovida("é");
					}
				}

		check = (testCheck(oponente(jogadorAtual))) ? true : false;
		
		if (testCheckMate(oponente(jogadorAtual))) {
			checkMate = true;
		}
		else {
			proximoTurno();
		}
		
		// #specialmove en passant
				if (moverPeca instanceof Peao && (alvo.getLinha() == origem.getLinha() - 2 || alvo.getLinha() == origem.getLinha() + 2)) {
					passanteVulneravel = moverPeca;
				}
				else {
					passanteVulneravel = null;
				}

//proximoTurno();
		return (PecaDeXadrez)capturaPeca;
	}
	
	public PecaDeXadrez substiruirPecaPromovida (String type) {
		if (promocao == null) {
			throw new IllegalStateException("Nao ha peca a ser promovida");
		}
		if (!type.equals("B") && !type.equals("C") && !type.equals("T") & !type.equals("é")) {
			throw new InvalidParameterException("tipo invalido para promocao");
		}

		Posicao pos = promocao.getPosicaoXadrez().toPosicao();
		Peca p = tabuleiro.removePeca(pos);
		pecasNoTabuleiro.remove(p);

		PecaDeXadrez novaPeca = novaPeca(type, promocao.getCor());
		tabuleiro.lugarPeca(novaPeca, pos);
		pecasNoTabuleiro.add(novaPeca);

		return novaPeca;
	}

	private PecaDeXadrez novaPeca(String type, Cor cor) {
		if (type.equals("B")) return new Bispo(tabuleiro, cor);
		if (type.equals("C")) return new Cavaleiro(tabuleiro, cor);
		if (type.equals("é")) return new Rainha(tabuleiro, cor);
		return new Torre(tabuleiro, cor);
	}
	
	
	
//metodo de fazer mover da posição origem para posição alvo
	private Peca fazerMover(Posicao origem, Posicao alvo) {
		//Peca p = tabuleiro.removePeca(origem);
		PecaDeXadrez p = (PecaDeXadrez)tabuleiro.removePeca(origem);
		p.incrementoContMovimento();
		Peca capturaPeca = tabuleiro.removePeca(alvo);
		tabuleiro.lugarPeca(p, alvo);
		//se a captura de peça for diferente de nulo remover peça do tabuleiro e adicionar na lista de peças capturadas
		
		if (capturaPeca != null) {
			pecasNoTabuleiro.remove(capturaPeca);
			capturaDePecas.add(capturaPeca);
		}
		
		// #specialmove castling kingside rook
		
		if (p instanceof Rei && alvo.getColuna() == origem.getColuna() + 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao alvoT = new Posicao(origem.getLinha(), origem.getColuna() + 1);
			PecaDeXadrez torre = (PecaDeXadrez)tabuleiro.removePeca(origemT);
			tabuleiro.lugarPeca(torre, alvoT);
			torre.incrementoContMovimento();		}

		
		// #specialmove castling queenside rook
		
		if (p instanceof Rei && alvo.getColuna() == origem.getColuna() - 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() - 4);
			Posicao alvoT = new Posicao(origem.getLinha(), origem.getColuna() - 1);
			PecaDeXadrez torre = (PecaDeXadrez)tabuleiro.removePeca(origemT);
			tabuleiro.lugarPeca(torre, alvoT);
			torre.incrementoContMovimento();
		}		

			// #specialmove en passant
		if (p instanceof Peao) {
			if (origem.getColuna() != alvo.getColuna() && capturaPeca == null) {
				Posicao peaoPosicao;
				if (p.getCor() == Cor.BRANCO) {
					peaoPosicao = new Posicao(alvo.getLinha() + 1, alvo.getColuna());
				}
				else {
					peaoPosicao = new Posicao(alvo.getLinha() - 1, alvo.getColuna());
				}
				capturaPeca = tabuleiro.removePeca(peaoPosicao);
				capturaDePecas.add(capturaPeca);
				pecasNoTabuleiro.remove(capturaPeca);
			}
		}

				
		return capturaPeca;
	}
	
	private void desfazMovimento(Posicao origem, Posicao alvo, Peca capturaPeca) {
		
		//Peca p = tabuleiro.removePeca(alvo);
		PecaDeXadrez p = (PecaDeXadrez)tabuleiro.removePeca(alvo);
		p. decrementoContMovimento();
		tabuleiro.lugarPeca(p, origem);

		if (capturaPeca != null) {
			tabuleiro.lugarPeca(capturaPeca, alvo);
			capturaDePecas.remove(capturaPeca);
			pecasNoTabuleiro.add(capturaPeca);
		}
		
		// #specialmove castling kingside rook
		
		
				if (p instanceof Rei && alvo.getColuna() == origem.getColuna() + 2) {
					Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() + 3);
					Posicao alvoT = new Posicao(origem.getLinha(), origem.getColuna() + 1);
					PecaDeXadrez torre = (PecaDeXadrez)tabuleiro.removePeca(alvoT);
					tabuleiro.lugarPeca(torre, origemT);
					torre.decrementoContMovimento();
				}

				// #specialmove castling queenside rook
				if (p instanceof Rei && alvo.getColuna() == origem.getColuna() - 2) {
					Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() - 4);
					Posicao alvoT = new Posicao(origem.getLinha(), origem.getColuna() - 1);
					PecaDeXadrez torre = (PecaDeXadrez)tabuleiro.removePeca(alvoT);
					tabuleiro.lugarPeca(torre, origemT);
					torre.decrementoContMovimento();
				}
		
		
				
				// #specialmove en passant
				if (p instanceof Peao) {
					if (origem.getColuna() != alvo.getColuna() && capturaPeca == passanteVulneravel) {
						PecaDeXadrez peao = (PecaDeXadrez)tabuleiro.removePeca(alvo);
						Posicao peaoPosicao;
						if (p.getCor() == Cor.BRANCO) {
							peaoPosicao = new Posicao(3, alvo.getColuna());
						}
						else {
							peaoPosicao = new Posicao(4, alvo.getColuna());
						}
						tabuleiro.lugarPeca(peao, peaoPosicao);
					}
				}
		
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

		
		private boolean testCheckMate(Cor cor) {
			if (!testCheck(cor)) {
				return false;
			}
			List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez)x).getCor() == cor).collect(Collectors.toList());
			for (Peca p : list) {
				boolean[][] mat = p.possiveisMovimentos();
				for (int i=0; i<tabuleiro.getLinhas(); i++) {
					for (int j=0; j<tabuleiro.getColunas(); j++) {
						if (mat[i][j]) {
							Posicao origem = ((PecaDeXadrez)p).getPosicaoXadrez().toPosicao();
							Posicao alvo = new Posicao(i, j);
							Peca capturaDePeca = fazerMover(origem, alvo);
							boolean testCheck = testCheck(cor);
							desfazMovimento(origem, alvo, capturaDePeca);
							if (!testCheck) {
								return false;
							}
						}
					}
				}
			}
			return true;
		}	
		
		
		
	private void lugarNovaPeca(char coluna, int linha, PecaDeXadrez peca) {
		tabuleiro.lugarPeca(peca, new PosicaoXadrez(coluna, linha).toPosicao());
		pecasNoTabuleiro.add(peca); //incluido para deixar a peça do oponete no lugar da peça capturada 
		
	}

	
	//Localização das peças no inicio da partida
	private void inicioPartida() {
	
	     	lugarNovaPeca('a', 1, new Torre(tabuleiro, Cor.BRANCO));
	     	lugarNovaPeca('b', 1, new Cavaleiro(tabuleiro, Cor.BRANCO));
	     	lugarNovaPeca('c', 1, new Bispo(tabuleiro, Cor.BRANCO));
	     	lugarNovaPeca('d', 1, new Rainha(tabuleiro, Cor.BRANCO));
	        lugarNovaPeca('e', 1, new Rei(tabuleiro, Cor.BRANCO, this));
	    	lugarNovaPeca('f', 1, new Bispo(tabuleiro, Cor.BRANCO));
	    	lugarNovaPeca('g', 1, new Cavaleiro(tabuleiro, Cor.BRANCO));
	        lugarNovaPeca('h', 1, new Torre(tabuleiro, Cor.BRANCO));
	        lugarNovaPeca('a', 2, new Peao(tabuleiro, Cor.BRANCO, this));
	        lugarNovaPeca('b', 2, new Peao(tabuleiro, Cor.BRANCO, this));
	        lugarNovaPeca('c', 2, new Peao(tabuleiro, Cor.BRANCO, this));
	        lugarNovaPeca('d', 2, new Peao(tabuleiro, Cor.BRANCO, this));
	        lugarNovaPeca('e', 2, new Peao(tabuleiro, Cor.BRANCO, this));
	        lugarNovaPeca('f', 2, new Peao(tabuleiro, Cor.BRANCO, this));
	        lugarNovaPeca('g', 2, new Peao(tabuleiro, Cor.BRANCO, this));
	        lugarNovaPeca('h', 2, new Peao(tabuleiro, Cor.BRANCO, this));

	        
	        lugarNovaPeca('a', 8, new Torre(tabuleiro, Cor.PRETO));
	        lugarNovaPeca('b', 8, new Cavaleiro(tabuleiro, Cor.PRETO));
	        lugarNovaPeca('c', 8, new Bispo(tabuleiro, Cor.PRETO));
	        lugarNovaPeca('d', 8, new Rainha(tabuleiro, Cor.PRETO));
	        lugarNovaPeca('e', 8, new Rei(tabuleiro, Cor.PRETO, this));
	        lugarNovaPeca('f', 8, new Bispo(tabuleiro, Cor.PRETO));
	        lugarNovaPeca('g', 8, new Cavaleiro(tabuleiro, Cor.PRETO));
	        lugarNovaPeca('h', 8, new Torre(tabuleiro, Cor.PRETO));
	        lugarNovaPeca('a', 7, new Peao(tabuleiro, Cor.PRETO, this));
	        lugarNovaPeca('b', 7, new Peao(tabuleiro, Cor.PRETO, this));
	        lugarNovaPeca('c', 7, new Peao(tabuleiro, Cor.PRETO, this));
	        lugarNovaPeca('d', 7, new Peao(tabuleiro, Cor.PRETO, this));
	        lugarNovaPeca('e', 7, new Peao(tabuleiro, Cor.PRETO, this));
	        lugarNovaPeca('f', 7, new Peao(tabuleiro, Cor.PRETO, this));
	        lugarNovaPeca('g', 7, new Peao(tabuleiro, Cor.PRETO, this));
	        lugarNovaPeca('h', 7, new Peao(tabuleiro, Cor.PRETO, this));
	}
}