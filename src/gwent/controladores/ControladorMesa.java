package gwent.controladores;

import java.util.HashMap;
import java.util.List;

import br.ufsc.inf.leobr.cliente.Jogada;
import gwent.entidades.*;
import gwent.netGames.AtorNetGames;
import gwent.visao.JMesa;

public class ControladorMesa {

	protected HashMap<TipoUnidade,Fileira> fileiras;
    protected HashMap<TipoUnidade,Fileira> fileirasAdversario;
    protected Jogador jogadorAtual;
    protected boolean conectado;
    protected Mesa mesa;
    protected JMesa jMesa;
    protected AtorNetGames atorNetGames;
    protected Deck cemiterio;

    public ControladorMesa(JMesa jMesa,
                           Fileira fileiraInfantaria,
                           Fileira fileiraLongaDistancia,
                           Fileira fileiraCerco,
                           Fileira fileiraInfantariaAd,
                           Fileira fileiraLongaDistanciaAd,
                           Fileira fileiraCercoAd) {
        this.jMesa = jMesa;
        this.atorNetGames = new AtorNetGames(this);
        this.mesa = new Mesa();
		this.fileiras = new HashMap<>();
		this.fileiras.put(TipoUnidade.INFANTARIA, fileiraInfantaria);
		this.fileiras.put(TipoUnidade.LONGA_DISTANCIA, fileiraLongaDistancia);
		this.fileiras.put(TipoUnidade.CERCO, fileiraCerco);
        this.fileirasAdversario = new HashMap<>();
        this.fileirasAdversario.put(TipoUnidade.INFANTARIA, fileiraInfantariaAd);
        this.fileirasAdversario.put(TipoUnidade.LONGA_DISTANCIA, fileiraLongaDistanciaAd);
        this.fileirasAdversario.put(TipoUnidade.CERCO, fileiraCercoAd);
	}

    public ControladorMesa(JMesa jMesa) {
        this.jMesa = jMesa;
        this.atorNetGames = new AtorNetGames(this);
        this.mesa = new Mesa();
    }

    public void iniciarPartida() {
        this.atorNetGames.iniciarPartida();

        List<Jogador> jogadores = this.atorNetGames.getJogadores();

        if (jogadores.size() == 2) {
            this.mesa.setJogadores(jogadores);
            this.mesa.criarJogadores();
            jogadores.stream()
                    .filter(jogador -> jogador.getNome().equals(this.jogadorAtual.getNome()))
                    .forEach(jogador -> this.jogadorAtual = jogador);
            this.iniciarNovaPartida();
        }
    }

    private void iniciarNovaPartida() {
        this.mesa.embaralhar();
        this.mesa.setJogadorDaVez(jogadorAtual);
        this.mesa.setStatusMesa(StatusMesa.INICAR_PARTIDA);
        this.mesa.iniciarRound(jogadorAtual);
        this.enviarJogada(this.mesa);
        this.jMesa.inicioPartida(mesa);
    }

    public boolean processarCarta(Carta carta){
        boolean precisaSelecionar = false;
        if(carta instanceof CartaUnidade){
            CartaUnidade c = (CartaUnidade) carta;
            TipoUnidade t = c.getTipo();
            Fileira f = this.fileiras.get(t);
            Habilidade habilidadeCarta = c.getHabilidade();

            if(habilidadeCarta != null){
                TipoHabilidade tipoHabilidadeCarta = habilidadeCarta.getTipoHabilidade();
                switch(tipoHabilidadeCarta){
                    case MEDICO:
                        habilidadeCarta.setChamador(this);
                        habilidadeCarta.setReferenciaDeck(this.cemiterio);

                        break;
                    case AGILIDADE:
//                        precisaSelecionar = true;
//                        return precisaSelecionar;
                    	break;
                    case CORNETA_COMANDANTE:
                        break;
                    case ESPIAO:
                        break;
                    case INCINERAR:
                        break;
                    case ISCA:
                        break;
                    default:
                        break;
                }
            }
            f.incluirCarta(carta);
        } else if(carta instanceof CartaClima){
        	CartaClima cc = (CartaClima) carta;
        	TipoCartaClima tipo = cc.getTipo();
        	switch(tipo){
        		case GEADA_MORDAZ:
        			cc.setFileiraAtingida(this.fileiras.get(TipoUnidade.INFANTARIA));
        			break;
        		case NEBLINA_IMPENETRAVEL:
        			cc.setFileiraAtingida(this.fileiras.get(TipoUnidade.LONGA_DISTANCIA));
        			break;
        		case CHUVA_TORRENCIAL:
        			cc.setFileiraAtingida(this.fileiras.get(TipoUnidade.CERCO));
        			break;
        	}
        	cc.ativarHabilidade();
        }
        return precisaSelecionar;
    }

    //INSTAVEL: a nao ser q carta de habilidade q vai em fileira tb receba um atributo tipo
    //isso vai quebrar qdo tiver q tratar cartas de habilidade 
    public Fileira determinaFileiraCarta(CartaUnidade carta){
    	return this.fileiras.get(carta.getTipo());
    }

    public boolean conectarRede(String nome, String servidor) {
        this.conectado = this.atorNetGames.conectar(nome, servidor);
        if (this.conectado) {
            this.criarJogadorAtual(nome);
        }
        return this.conectado;
    }

    public void desconectarRede() {
        if (this.conectado) {
            this.atorNetGames.desconectar();
            this.atualizarVisibilidadeTela(1);
            this.conectado = false;
        }
    }

    public void exibeMensagem(String mensagem) {
        this.jMesa.exibeMensagem(mensagem);
    }

    public void receberJogada(Jogada jogada) {
        Jogador jogando = this.mesa.getJogadorDaVez();
        Lance lance = null;

        if (jogada instanceof Mesa) {
            this.mesa = (Mesa) jogada;
            jMesa.recebeMesa(mesa);
        } else {
            lance = (Lance) jogada;
            if (lance.getCarta() == null) {
                this.jogadorAtual = this.mesa.getJogadorNaoAtual(lance.getJogador());
                this.jMesa.recebeLance(lance);
            } else {
                this.jogadorAtual = this.mesa.getJogadorNaoAtual(lance.getJogador());
                this.processarCartaAdversario(lance.getCarta());
                this.mesa.removeCartaMaoJogador(lance);
                this.jMesa.recebeLance(lance);
            }
            alterarJogadorDaVezNaMesa(jogando);
            this.jMesa.atualizaJogadorDaVez(this.mesa.getJogadorDaVez());
            this.mesa.addLance(lance);

        }
    }

    public void processarCartaAdversario(Carta carta) {
        if(carta instanceof CartaUnidade){
            CartaUnidade c = (CartaUnidade) carta;
            TipoUnidade t = c.getTipo();
            Fileira f = this.fileirasAdversario.get(t);
            Habilidade habilidadeCarta = c.getHabilidade();

            if(habilidadeCarta != null){
                TipoHabilidade tipoHabilidadeCarta = habilidadeCarta.getTipoHabilidade();
                switch(tipoHabilidadeCarta){
                    case MEDICO:
                        habilidadeCarta.setChamador(this);
                        habilidadeCarta.setReferenciaDeck(this.cemiterio);

                        break;
                    case AGILIDADE:
//                        precisaSelecionar = true;
//                        return precisaSelecionar;
                        break;
                    case CORNETA_COMANDANTE:
                        break;
                    case ESPIAO:
                        break;
                    case INCINERAR:
                        break;
                    case ISCA:
                        break;
                    default:
                        break;
                }
            }
            f.incluirCarta(carta);
        } else if(carta instanceof CartaClima){
            CartaClima cc = (CartaClima) carta;
            TipoCartaClima tipo = cc.getTipo();
            switch(tipo){
                case GEADA_MORDAZ:
                    cc.setFileiraAtingida(this.fileirasAdversario.get(TipoUnidade.INFANTARIA));
            }
            cc.ativarHabilidade();
        }
    }

    public void atualizarVisibilidadeTela(int mode) {
        this.jMesa.atualizarVisibilidadeTela(mode);
    }

    private void verificarFimDaRodada() {
//        if (this.chegouFimDaRodada()) {
//            this.computarPontos();
//            this.interfaceMesa.atualizarPontosJogadores(mesa);
//
//            this.mesa.setStatusMesa(StatusMesa.INICIAR_RODADA);
//            this.interfaceMesa.recebeMesa(mesa);
//
//            this.enviarJogada(mesa);
//            this.receberJogada(mesa);
//
//            this.verificarFimDaPartida();
//        }
    }

    private void alterarJogadorDaVezNaMesa(Jogador jogador) {
        if (this.mesa.getJogadorUm().getNome().equals(jogador.getNome())) {
            this.mesa.setJogadorDaVez(this.mesa.getJogadorDois());
        } else {
            this.mesa.setJogadorDaVez(this.mesa.getJogadorUm());
        }
    }

    private void criarJogadorAtual(String nome) {
        this.jogadorAtual = new Jogador(1, nome);
    }

    public void enviarJogada(Jogada jogada) {
        this.atorNetGames.enviarJogada(jogada);
    }

    public Jogador getJogadorAtual() {
        return jogadorAtual;
    }

    public void setJogadorAtual(Jogador jogadorAtual) {
        this.jogadorAtual = jogadorAtual;
    }

    public boolean isConectado() {
        return conectado;
    }

    public void setConectado(boolean conectado) {
        this.conectado = conectado;
    }

    public Deck getCemiterio() {
        return cemiterio;
    }

    public void setCemiterio(Deck cemiterio) {
        this.cemiterio = cemiterio;
    }

    public void passarTurno() {
        if (this.tratarPossibilidadeJogada()) {
            this.mesa.inativarJogador(this.jogadorAtual);
            Lance lance = new Lance(this.jogadorAtual);
            this.enviarJogada(lance);
            this.jogadorAtual = this.mesa.getJogadorNaoAtual(lance.getJogador());
        } else {
            this.exibeMensagem("Espere a sua vez.");
        }
    }

    public void baixarCarta(Carta carta) {
        if (this.tratarPossibilidadeJogada()) {
            this.processarCarta(carta);
            Lance lance = new Lance(carta, this.jogadorAtual);
            this.enviarJogada(lance);
            this.jogadorAtual = this.mesa.getJogadorNaoAtual(lance.getJogador());
        } else {
            this.exibeMensagem("Espere a sua vez.");
        }
    }

    private boolean tratarPossibilidadeJogada() {
        return this.isVezJogador(this.jogadorAtual) && this.isConectado();
    }

    private boolean isVezJogador(Jogador jogador) {
        return jogador.getNome().equals(this.mesa.getJogadorDaVez().getNome());
    }
}
