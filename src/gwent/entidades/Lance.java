package gwent.entidades;

import br.ufsc.inf.leobr.cliente.Jogada;

public class Lance implements Jogada {

    protected Carta carta;
    protected Jogador jogador;

    public Lance(Jogador jogador) {
        this.jogador = jogador;
    }

    public Lance(Carta carta, Jogador jogador) {
        this.carta = carta;
        this.jogador = jogador;
    }

    public Carta getCarta() {
        return carta;
    }

    public void setCarta(Carta carta) {
        this.carta = carta;
    }

    public Jogador getJogador() {
        return jogador;
    }

    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
    }

    public boolean isInicioPartida() {
        return carta == null && jogador == null;
    }
}
