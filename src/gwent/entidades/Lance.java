package gwent.entidades;

import br.ufsc.inf.leobr.cliente.Jogada;

public class Lance implements Jogada {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Carta cartaJogada;
	protected Carta cartaExibicao;		//manda junto para o adversario poder enxergar
    protected Jogador jogador;

    public Lance(Jogador jogador) {
        this.jogador = jogador;
    }

    public Lance(Carta carta, Jogador jogador) {
        this.cartaJogada = carta;
        this.jogador = jogador;
    }

    public Carta getCartaJogada() {
        return cartaJogada;
    }

    public void setCartaJogada(Carta carta) {
        this.cartaJogada = carta;
    }
    
    public Carta getCartaExibicao(){
    	return cartaExibicao;
    }
    
    public void setCartaExibicao(Carta carta){
    	this.cartaExibicao = carta;
    }
    
    public Jogador getJogador() {
        return jogador;
    }

    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
    }

    public boolean isInicioPartida() {
        return cartaJogada == null && jogador == null;
    }
    
    @Override
    public String toString(){
    	String detalhes = "";
    	detalhes += "Carta Jogada : " + (this.cartaJogada != null ? this.cartaJogada.toString() : "");
    	detalhes += "\nJogador : " + this.jogador.toString();
    	return detalhes;
    }
}
