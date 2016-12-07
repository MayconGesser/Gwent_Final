package gwent.controladores;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ufsc.inf.leobr.cliente.Jogada;
import gwent.entidades.*;
import gwent.netGames.AtorNetGames;
import gwent.visao.JMesa;

public class ControladorMesa implements Jogada {

	protected HashMap<TipoUnidade,Fileira> fileiras;
    protected HashMap<TipoUnidade,Fileira> fileirasAdversario;
    protected Jogador jogadorAtual;
    protected boolean conectado;
    protected Mesa mesa;
    protected JMesa jMesa;
    protected AtorNetGames atorNetGames;

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

	public void limpaFileiras(Fileira fileiraInfantaria,
                              Fileira fileiraLongaDistancia,
                              Fileira fileiraCerco,
                              Fileira fileiraInfantariaAd,
                              Fileira fileiraLongaDistanciaAd,
                              Fileira fileiraCercoAd) {
        this.fileiras = new HashMap<>();
        this.fileiras.put(TipoUnidade.INFANTARIA, fileiraInfantaria);
        this.fileiras.put(TipoUnidade.LONGA_DISTANCIA, fileiraLongaDistancia);
        this.fileiras.put(TipoUnidade.CERCO, fileiraCerco);
        this.fileirasAdversario = new HashMap<>();
        this.fileirasAdversario.put(TipoUnidade.INFANTARIA, fileiraInfantariaAd);
        this.fileirasAdversario.put(TipoUnidade.LONGA_DISTANCIA, fileiraLongaDistanciaAd);
        this.fileirasAdversario.put(TipoUnidade.CERCO, fileiraCercoAd);
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
        this.jMesa.inicioPartidaJogadorUm(mesa);
    }

    //metodo usado na habilidade AGRUPAR
    //compara nomes de cartas
    private String sanitizarString(String s){
    	String saux = s.substring(0,s.indexOf("_"));
		System.out.println("saux : " + saux);
		int upos = saux.length()-1;
		StringBuilder sb = new StringBuilder(saux);
		if(Character.isDigit(saux.charAt(upos))){
			sb.deleteCharAt(upos);
		}
		return sb.toString();
    }

    public void processarCarta(Carta carta){
        Jogador jogador = this.jogadorAtual;
        if(carta instanceof CartaUnidade){
            CartaUnidade c = (CartaUnidade) carta;
            TipoUnidade t = c.getTipo();
            Map<TipoUnidade, Fileira> f = this.fileiras;
            Habilidade habilidadeCarta = c.getHabilidade();

            if(habilidadeCarta != null){
                TipoHabilidade tipoHabilidadeCarta = habilidadeCarta.getTipoHabilidade();
                switch(tipoHabilidadeCarta){
                    case ESPIAO:	//deve ser baixada na fileira do adversario
                    	f = this.fileirasAdversario;		//troca a referencia para a fileira do adversario
                    	Carta cs1 = this.jogadorAtual.comprarCarta();
                    	Carta cs2 = this.jogadorAtual.comprarCarta();   //compra 2 cartas, conforme habilidade
                    	this.jMesa.adicionarCartasMaoJogador(cs1);
                    	this.jMesa.adicionarCartasMaoJogador(cs2);
                        jogador = this.mesa.getJogadorNaoAtual(this.jogadorAtual);
                        break;

                    case AGRUPAR:
                    	Deck deckJogador = this.jogadorAtual.getDeck();
                    	String nomeAComparar = sanitizarString(c.getNomeCarta());
                    	HashMap<String,Carta> cartas = this.jMesa.getCartasFileira();
                    	for(Carta cds : deckJogador.getCartas()){
                    		if(cds instanceof CartaUnidade){
                    			CartaUnidade u = (CartaUnidade)cds;
                    			String nomeDaCarta = sanitizarString(u.getNomeCarta());
                    			if(nomeAComparar.equals(nomeDaCarta) &&
                    					u.getHabilidade().getTipoHabilidade().equals(TipoHabilidade.AGRUPAR)
                    					&& c != u){
                    				Fileira fi = this.fileiras.get(u.getTipo());
                    				u = (CartaUnidade)cartas.get(u.getNomeCarta());
                    				fi.incluirCarta(u);
                    			}
                    		}
                    	}
                    	break;
                    default:
                        break;
                }
            }
            f.get(t).incluirCarta(carta);
            this.computaPontosJogador(jogador, f);
        } else if(carta instanceof CartaClima){
        	CartaClima cc = (CartaClima) carta;
        	cc.ativarHabilidade(this.fileiras.get(cc.getTipo().getFileiraAtingida()));
        	cc.ativarHabilidade(this.fileirasAdversario.get(cc.getTipo().getFileiraAtingida()));
            this.computaPontosJogador(jogador, this.fileiras);
            this.computaPontosJogador(this.mesa.getJogadorNaoAtual(jogador), this.fileirasAdversario);
        }
        getJogadorAtual().getCartasMao().remove(carta);
    }

    public ListaCartasAgrupar<CartaUnidade> processarCartaAgrupar(Jogada jogada){
    	CartaUnidade carta = null;
    	Jogador jogador = null;
        LanceCartaAgrupar lance = (LanceCartaAgrupar) jogada;
        carta = (CartaUnidade)lance.getCartaJogada();
        jogador = lance.getJogador();
    	ListaCartasAgrupar<CartaUnidade> retorno = new ListaCartasAgrupar<>();
    	TipoUnidade t = carta.getTipo();
        Fileira f = this.fileiras.get(t);
        Deck deckJogador = this.jogadorAtual.getDeck();
    	String nomeAComparar = sanitizarString(carta.getNomeCarta());
    	HashMap<String,Carta> cartasFileira = this.jMesa.getCartasFileira();
    	for(Carta cds : deckJogador.getCartas()){		//percorre o deck do jogador
    		if(cds instanceof CartaUnidade){
    			CartaUnidade u = (CartaUnidade)cds;
    			String nomeDaCarta = sanitizarString(u.getNomeCarta());
    			if(nomeAComparar.equals(nomeDaCarta) && 
    					(u.getHabilidade() != null) &&
    					(u.getHabilidade().getTipoHabilidade().equals(TipoHabilidade.AGRUPAR))
    					&& carta != u){
    				Fileira fi = this.fileiras.get(u.getTipo());
    				u = (CartaUnidade)cartasFileira.get(u.getNomeCarta());
    				int pontos = fi.incluirCarta(u);	//inclui cartas q vieram com ativacao da habilidade
    				if(jogador != null){
    					jogador.addPontuacao(pontos);
    				}
    				retorno.add(u);
    			}
    		}
    	}
    	//joga cartas q fazem parte da jogada de agrupar q estao na mao do jogador
//    	for(Carta ccc : this.jogadorAtual.getCartasMao()){
//    		if(ccc instanceof CartaUnidade){
//    			CartaUnidade uu = (CartaUnidade)ccc;
//    			String nomeUU = sanitizarString(uu.getNomeCarta());
//    			if(nomeAComparar.equals(nomeUU) &&
//    					uu.getHabilidade() != null &&
//    					uu.getHabilidade().getTipoHabilidade().equals(TipoHabilidade.AGRUPAR)
//    					&& carta != uu){
//    						Fileira fi = this.fileiras.get(uu.getTipo());
//    						uu = (CartaUnidade)cartasFileira.get(uu.getTipo());
//    						int pontos = fi.incluirCarta(uu);
//    						if(jogador != null){
//    							jogador.addPontuacao(pontos);
//    						}
//    					retorno.add(uu);
//    					getJogadorAtual().getCartasMao().remove(carta);
//    			}
//    		}
//    	}

    	f.incluirCarta(carta);		//inclui a carta q ocasionou a habilidade
        this.computaPontosJogador(this.mesa.getJogador(jogador), this.fileiras);
    	return retorno;
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
        if (jogada instanceof Mesa) {
            this.mesa = (Mesa) jogada;
            jMesa.recebeMesa(mesa);
        } else {
            Lance lance = (Lance) jogada;
            if (lance.getCartaJogada() == null) {		//jogador adversario passou o round
                this.mesa.inativarJogador(lance.getJogador());
                this.jogadorAtual = this.mesa.getJogadorNaoAtual(lance.getJogador());
                if (this.mesa.verificaFimDoRound()) {
                    this.acabouRound();
                } else {
                    this.mesa.setJogadorDaVez(this.jogadorAtual);
                    this.jMesa.recebeLance(lance);
                    getJogadorAtual().getCartasMao().remove(lance.getCartaJogada());
                }
            } else {		//jogador adversario jogou uma carta
                if (this.mesa.getJogadorNaoAtual(lance.getJogador()).getStatusJogador().equals(StatusJogador.ATIVO)) {
                    this.jogadorAtual = this.mesa.getJogadorNaoAtual(lance.getJogador());
                    this.mesa.setJogadorDaVez(this.jogadorAtual);
                    this.jMesa.recebeLance(lance);
                }
                if(lance instanceof LanceCartaAgrupar){
                	LanceCartaAgrupar lanceAgrupar = (LanceCartaAgrupar) lance;
                	this.processarCartaAgruparAdversario(jogada);
                }
                else{
                	this.processarCartaAdversario(lance);
                }                
                this.mesa.removeCartaMaoJogador(lance);
            }
            this.mesa.addLance(lance);
        }

        this.jMesa.atualizarPlacar();
    }

    public void processarCartaAgruparAdversario(Jogada jogada){
    	LanceCartaAgrupar lanceAgrupar = (LanceCartaAgrupar) jogada;
    	Jogador jogador = lanceAgrupar.getJogador();
    	ListaCartasAgrupar<CartaUnidade> cartasDoLance = lanceAgrupar.getCartasAgrupar();
    	for(CartaUnidade u : cartasDoLance){
    		Fileira f = this.fileirasAdversario.get(u.getTipo());
    		f.incluirCarta(u);
    	}
    	CartaUnidade cartaJogada = (CartaUnidade)lanceAgrupar.getCartaJogada();
    	this.fileirasAdversario.get(cartaJogada.getTipo()).incluirCarta(cartaJogada);
        this.computaPontosJogador(this.mesa.getJogador(jogador), this.fileirasAdversario);
    	this.jMesa.atualizarPlacar();
    }

    public void processarCartaAdversario(Jogada jogada) {
        Lance lance = (Lance) jogada;
        Jogador jogador = lance.getJogador();

        if(lance.getCartaJogada() instanceof CartaUnidade){
            CartaUnidade c = (CartaUnidade) lance.getCartaJogada();
            TipoUnidade t = c.getTipo();
            Map<TipoUnidade, Fileira> f = this.fileirasAdversario;
            Habilidade habilidadeCarta = c.getHabilidade();

            if(habilidadeCarta != null){
                TipoHabilidade tipoHabilidadeCarta = habilidadeCarta.getTipoHabilidade();
                switch(tipoHabilidadeCarta){
                    case ESPIAO:
                    	f = this.fileiras;	//troca a referencia, deve ser baixada na propria fileira
                        jogador = this.jogadorAtual;
                        break;
                    case AGRUPAR:
                    	break;
                    default:
                        break;
                }
            }
            f.get(t).incluirCarta(lance.getCartaJogada());
            this.jMesa.addCartasExibicaoAdversario(lance.getCartaExibicao());
            this.computaPontosJogador(this.mesa.getJogador(jogador), f);
        } else if(lance.getCartaJogada() instanceof CartaClima){
            CartaClima cc = (CartaClima) lance.getCartaJogada();
            TipoCartaClima tipo = cc.getTipo();
            cc.ativarHabilidade(this.fileiras.get(tipo.getFileiraAtingida()));
            cc.ativarHabilidade(this.fileirasAdversario.get(tipo.getFileiraAtingida()));
            this.computaPontosJogador(jogador, this.fileirasAdversario);
            this.computaPontosJogador(this.mesa.getJogadorNaoAtual(jogador), this.fileiras);
        }
        this.jMesa.atualizarPlacar();
    }

    private void computaPontosJogador(Jogador jogador, Map<TipoUnidade, Fileira> fileiraMap) {
        jogador.setPontuacao(0);
        for (Fileira fileira : fileiraMap.values()) {
            jogador.addPontuacao(fileira.getPoderTotal());
        }
    }

    public void atualizarVisibilidadeTela(int mode) {
        this.jMesa.atualizarVisibilidadeTela(mode);
    }

    private void acabouRound() {
        this.jMesa.exibeMensagem("Acabou o round, computando os pontos ...");
        Jogador jogadorVencedor = this.mesa.verificaVencedorRound();
        jMesa.atualizarVencedorRound(jogadorVencedor);
        this.jMesa.exibeMensagem("Jogador vencedor: " + jogadorVencedor.toString());
        this.limparFileiras();
        this.mesa.iniciarNovoRound(jogadorVencedor);
        if (this.jogadorAtual.equals(jogadorVencedor))
            this.jMesa.acaoBotao(true);

        this.jogadorAtual = this.mesa.getJogador(jogadorVencedor);
        this.jMesa.recebeMesa(this.mesa);

        this.verificarFimDaPartida(false);
        this.exibeMensagem("Um novo round vai iniciar");
    }

    public void verificarFimDaPartida(boolean encerrarPartida) {
        Jogador jogadorVencedor = this.mesa.verificaFimPartida(encerrarPartida);

        if (jogadorVencedor != null) {
            this.encerrarPartida(jogadorVencedor);
        }
    }

    private void encerrarPartida(Jogador jogadorVencedor) {
        this.mesa.setJogadorDaVez(jogadorVencedor);
        this.mesa.setStatusMesa(StatusMesa.ENCERRAR_PARTIDA);
        this.enviarJogada(this.mesa);
        this.jMesa.recebeMesa(this.mesa);
    }

    private void limparFileiras() {
        this.fileiras.values().forEach(Fileira::limpar);
        this.fileirasAdversario.values().forEach(Fileira::limpar);
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

    public void passarTurno() {
        if (this.tratarPossibilidadeJogada()) {
            this.mesa.inativarJogador(this.jogadorAtual);
            Lance lance = new Lance(this.jogadorAtual);
            this.enviarJogada(lance);
            if (this.mesa.verificaFimDoRound()) {
                this.acabouRound();
            } else {
                this.jogadorAtual = this.mesa.getJogadorNaoAtual(lance.getJogador());
                this.mesa.setJogadorDaVez(this.jogadorAtual);
            }
        } else {
            this.exibeMensagem("Espere a sua vez.");
        }
    }

    public void baixarCarta(Carta cartaJogada, Carta cartaExibicao) {
        if (this.tratarPossibilidadeJogada()) {
            Lance lance = new Lance(cartaJogada, this.jogadorAtual);
            if(cartaExibicao != null){
            	lance.setCartaExibicao(cartaExibicao);	//qdo eh null eh uma carta de clima
            }
            this.processarCarta(cartaJogada);
            this.enviarJogada(lance);
            Jogador jogadorNaoAtual = this.mesa.getJogadorNaoAtual(lance.getJogador());
            if (jogadorNaoAtual.getStatusJogador().equals(StatusJogador.ATIVO)) {
                this.jogadorAtual = jogadorNaoAtual;
                this.mesa.setJogadorDaVez(jogadorNaoAtual);
                this.jMesa.acaoBotao(false);
            }
        } else {
            this.exibeMensagem("Espere a sua vez.");
        }
    }
    
    public void baixarCartaAgrupar(Carta cartaJogada, Carta cartaExibicao){
    	if (this.tratarPossibilidadeJogada()) {
            LanceCartaAgrupar lance = new LanceCartaAgrupar(cartaJogada, this.jogadorAtual);
            ListaCartasAgrupar<CartaUnidade> cartasLance = this.processarCartaAgrupar(lance);
            for(CartaUnidade u : cartasLance){
            	lance.setCartaAgrupar(u);
            }
            this.enviarJogada(lance);
            Jogador jogadorNaoAtual = this.mesa.getJogadorNaoAtual(lance.getJogador());
            if (jogadorNaoAtual.getStatusJogador().equals(StatusJogador.ATIVO)) {
                this.jogadorAtual = jogadorNaoAtual;
                this.mesa.setJogadorDaVez(jogadorNaoAtual);
                this.jMesa.acaoBotao(false);
            }
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

    public Mesa getMesa (){
        return this.mesa;
    }
}
