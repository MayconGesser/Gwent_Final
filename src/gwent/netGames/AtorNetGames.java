package gwent.netGames;

import br.ufsc.inf.leobr.cliente.Jogada;
import br.ufsc.inf.leobr.cliente.OuvidorProxy;
import br.ufsc.inf.leobr.cliente.Proxy;
import br.ufsc.inf.leobr.cliente.exception.*;
import gwent.controladores.ControladorMesa;
import gwent.entidades.BancoCartas;
import gwent.entidades.Deck;
import gwent.entidades.Faccao;
import gwent.entidades.Jogador;

import java.util.ArrayList;
import java.util.List;

public class AtorNetGames implements OuvidorProxy {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected ControladorMesa controladorMesa;
    protected Proxy proxy;

    public AtorNetGames(ControladorMesa controladorMesa) {
        super();
        this.controladorMesa = controladorMesa;
        this.proxy = Proxy.getInstance();
        proxy.addOuvinte(this);
    }

    public boolean conectar(String nome, String servidor) {
        boolean retorno = true;

        try {
            proxy.conectar(servidor, nome);
        } catch (JahConectadoException | NaoPossivelConectarException | ArquivoMultiplayerException ex) {
            ex.printStackTrace();
            retorno = false;
        }
        return retorno;
    }

    public void desconectar() {
        try {
            this.proxy.desconectar();
        } catch (NaoConectadoException e) {
            this.controladorMesa.exibeMensagem(e.getMessage());
            this.controladorMesa.setConectado(false);
        }
    }

    public List<Jogador> getJogadores() {
        List<Jogador> jogadores = new ArrayList<Jogador>();
        if (controladorMesa.getJogadorAtual().getNome().equals(proxy.obterNomeAdversario(1))) {
            for (int i = 1; i <= 2; i++) {
                Jogador jogador = null;
                try {
                    jogador = new Jogador(i, proxy.obterNomeAdversario(i));
                    if (jogador.getIdJogador() == 1)
                        jogador.setDeck((Deck) BancoCartas.resgatarDeck(Faccao.REINOS_DO_NORTE).get("deck"));
                    else
                        jogador.setDeck((Deck) BancoCartas.resgatarDeck(Faccao.MONSTROS).get("deck"));
                    jogadores.add(jogador);
                } catch (Exception e) {
                    System.out.println("Sem jogadores suficientes");
                }
            }
        } else {
            Jogador jogador = controladorMesa.getJogadorAtual();
            jogador.setDeck((Deck) BancoCartas.resgatarDeck(Faccao.REINOS_DO_NORTE).get("deck"));
            jogadores.add(jogador);
            jogador = new Jogador(2, proxy.obterNomeAdversario(2));
            jogador.setDeck((Deck) BancoCartas.resgatarDeck(Faccao.MONSTROS).get("deck"));
            jogadores.add(jogador);
        }

        return jogadores;
    }

    public void enviarJogada(Jogada jogada) {
    	System.out.println(jogada.toString());
        try {
            proxy.enviaJogada(jogada);
        } catch (NaoJogandoException ignored) {
        }
    }

    public void iniciarPartida() {
        try {
            proxy.iniciarPartida(2);
        } catch (NaoConectadoException ignored) {
        }
    }

    @Override
    public void iniciarNovaPartida(Integer posicao) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void finalizarPartidaComErro(String message) {
        controladorMesa.exibeMensagem(message);
//        controladorMesa.limparTodosCampos();
    }

    @Override
    public void receberMensagem(String msg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void receberJogada(Jogada jogada) {
        controladorMesa.receberJogada(jogada);
    }

    @Override
    public void tratarConexaoPerdida() {
        controladorMesa.exibeMensagem("Conexão perdida com o servidor.");
    }

    @Override
    public void tratarPartidaNaoIniciada(String message) {
        controladorMesa.exibeMensagem(message);
    }

    public void encerrarPartida() {
        try {
            proxy.finalizarPartida();
        } catch (NaoConectadoException | NaoJogandoException ignored) {
        }
    }


}

