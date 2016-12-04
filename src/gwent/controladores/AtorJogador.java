package gwent.controladores;


import gwent.entidades.Carta;
import gwent.entidades.Faccao;
import gwent.entidades.Jogador;
import gwent.visao.JMesa;

public class AtorJogador {

    protected ControladorMesa controladorMesa;

    public AtorJogador(JMesa jMesa) {
        this.controladorMesa = new ControladorMesa(jMesa);
    }

    public ControladorMesa getControladorMesa() {
        return controladorMesa;
    }

    public Jogador getJogadorAtual() {
        return controladorMesa.getJogadorAtual();
    }

    public void setJogadorAtual(Jogador jogador) {
        controladorMesa.setJogadorAtual(jogador);
    }

    public void setControladorMesa(ControladorMesa controladorMesa) {
        this.controladorMesa = controladorMesa;
    }

    public boolean conectar(String nomeJogador, String hostServidor) {
        return controladorMesa.conectarRede(nomeJogador, hostServidor);
    }

    public void desconectar() {
        this.controladorMesa.desconectarRede();
    }

    public void iniciarPartida(Faccao faccao) {
        this.controladorMesa.iniciarPartida(faccao);
    }

    public void jogarCarta(Carta carta) {
        controladorMesa.processarCarta(carta);
    }
}
