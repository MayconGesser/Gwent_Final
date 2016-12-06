package gwent.controladores;


import gwent.entidades.Carta;
import gwent.entidades.CartaUnidade;
import gwent.entidades.Jogador;
import gwent.entidades.TipoHabilidade;

public class AtorJogador {

    protected ControladorMesa controladorMesa;

    public AtorJogador(ControladorMesa controladorMesa) {
        this.controladorMesa = controladorMesa;
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

    public void iniciarPartida() {
        this.controladorMesa.iniciarPartida();
    }

    public void passarTurno() {
        this.controladorMesa.passarTurno();
    }

    public void baixarCarta(Carta carta) {
    	if(carta instanceof CartaUnidade){
    		CartaUnidade un = (CartaUnidade) carta;
    		if(un.getHabilidade() != null){
    			if(un.getHabilidade().getTipoHabilidade().equals(TipoHabilidade.AGRUPAR)){
    				controladorMesa.baixarCartaAgrupar(un);
    				return;		//para nao ir para o comando de baixo
    			}
    		}
    	}
        controladorMesa.baixarCarta(carta);
    }
}

