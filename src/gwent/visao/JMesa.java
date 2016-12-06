package gwent.visao;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.GroupLayout.Group;
import javax.swing.border.BevelBorder;

import gwent.controladores.AtorJogador;
import gwent.controladores.ControladorMesa;
import gwent.entidades.*;

public class JMesa extends javax.swing.JFrame {
	private static final long serialVersionUID = 1L;
    final Color marrom = new Color(102,51,0);
    final Color marromFileira = new Color(156,73,0);
    final Color marromEspaco = new Color(51,25,0);
    final Dimension tamFileira = new Dimension(624,100);
    final Dimension tamEspaco = new Dimension(90,110);
    protected AtorJogador atorJogador;
    protected static final int START = 1;
    protected static final int CONECTADO = 2;

    private String getNomeJogador() {
        return JOptionPane.showInputDialog(this, "Digite seu nome: ", "jogador");
    }

    private String getNomeServidor() {
        return JOptionPane.showInputDialog(this, "Digite o servidor: ", "localhost");
    }

    private void conectar() {
        String nomeAtual = this.getNomeJogador();
        String  servidor = this.getNomeServidor();
        boolean conectou = atorJogador.conectar(nomeAtual, servidor);
        if (conectou) {
			Jogador jogador = new Jogador(0, nomeAtual);
			this.atorJogador.setJogadorAtual(jogador);
			this.adicionarTitulo(nomeAtual);
            this.atualizarVisibilidadeTela(CONECTADO);
            this.exibeMensagem("Conectado com sucesso!");
        } else {
            this.exibeMensagem("Não foi possível se conectar!");
        }
    }

    public void atualizarVisibilidadeTela(int mode) {
        if (mode == START) {
            jMenuItemConectar.setEnabled(true);
            jMenuItemDesconectar.setEnabled(false);
            jMenuItemIniciarPartida.setEnabled(false);
            jMenuItemEncerrarPartida.setEnabled(false);
        } else if (mode == CONECTADO) {
            jMenuItemDesconectar.setEnabled(true);
            jMenuItemIniciarPartida.setEnabled(true);
            jMenuItemConectar.setEnabled(false);
            jMenuItemEncerrarPartida.setEnabled(true);
        }
    }

    public void recebeLance(Lance lance) {
        atualizarPlacar();
        if (lance.getCarta() == null) {
            this.exibeMensagem("O adversário passou o turno");
            this.acaoBotao(true);
        } else {
            Map<String, Object> mapDeck = BancoCartas.resgatarDeck(lance.getJogador().getDeck().getFaccao());
            this.exibeMensagem("Sua vez de jogar");
            this.acaoBotao(true);
        }
    }

    public void recebeMesa(Mesa mesa) {
        atualizarPlacar();
        if (mesa.getStatusMesa().equals(StatusMesa.INICAR_PARTIDA)) {
            this.iniciarPartidaJogadorDois(mesa);
//            this.setNomeJogadoresLabel(mesa);
            this.exibeMensagem("Uma nova partida vai iniciar");
        }  else if (mesa.getStatusMesa().equals(StatusMesa.INICIAR_RODADA)) {
//            this.iniciarNovaRodada(mesa);
//            this.atualizarPontosJogadores(mesa);
        } else if (mesa.getStatusMesa().equals(StatusMesa.ENCERRAR_PARTIDA)) {
//            this.exibeMensagem(mesa.getMensagemFim());
            System.exit(0);
        }
//        this.atualizaJogadorDaVez(mesa);
        revalidate();
        repaint();
    }

    private void iniciarPartidaJogadorDois(Mesa mesa) {
        this.atualizaCamposInicioPartida(mesa);
        this.atualizarNomeFaccaoJogador();
    }

    public void inicioPartidaJogadorUm(Mesa mesa) {
        this.preencherCartas(mesa.getJogadorUm());
        this.ctrlMesa.setJogadorAtual(mesa.getJogadorUm());
        this.acaoBotao(true);
        this.exibeMensagem("Uma nova partida vai iniciar");
        this.atualizarNomeFaccaoJogador();
    }

    private void atualizaCamposInicioPartida(Mesa mesa) {
        this.preencherCartas(mesa.getJogadorDois());
        this.acaoBotao(false);
        mesa.setStatusMesa(StatusMesa.INICIAR_RODADA);
        this.iniciarRound(mesa);
    }

    public void acaoBotao(boolean ativa) {
        this.btJogar.setEnabled(ativa);
        this.btPassar.setEnabled(ativa);
    }

    private void iniciarRound(Mesa mesa) {
        mesa.iniciarRound(mesa.getJogadorDaVez());
    }

    //metodo invocado qdo cartas sao compradas, para exibir cartas
    public void atualizarMaoJogador(Carta carta){
    	carta.addMouseListener(this.mouseCartas);
    	this.HespacoCartas.addComponent(carta);
    	this.VespacoCartas.addComponent(carta);
		revalidate();
		repaint();
    }
    
    //invocado apenas no comeco do jogo
    private void preencherCartas(Jogador jogador) {
		this.HespacoCartas = espacoCartasLayout.createSequentialGroup();
		this.VespacoCartas = espacoCartasLayout.createParallelGroup();
		
		ArrayList<Carta> mao = (ArrayList)jogador.getCartasMao();
		for(int y = 0; y<mao.size(); y++){
			System.out.println(mao.get(y).toString());
		}			
		
		for(int x = 0; x<10; x++) {
			Carta carta = jogador.mostrarCarta(x);
			carta.addMouseListener(this.mouseCartas);
			carta.setName(carta.getNomeCarta());
			this.HespacoCartas.addComponent(carta);
			this.VespacoCartas.addComponent(carta);
		}
		
		espacoCartasLayout.setHorizontalGroup(this.HespacoCartas);
		espacoCartasLayout.setVerticalGroup(this.VespacoCartas);
		
        Map<String, Object> mapDeck = BancoCartas.resgatarDeck(jogador.getDeck().getFaccao());

        cartasExibicao = (HashMap<String, Carta>) mapDeck.get("exibicao");
		cartasFileiraEx = (HashMap<String, Carta>) mapDeck.get("fileiras");
	}

    private void iniciarPartidaJogadorDois() {
		this.atorJogador.iniciarPartida();
    }

    private void adicionarTitulo(String nome) {
        this.setTitle(nome);
    }

    private void criarFileira(JPanel fileira){
        fileira.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        fileira.setBackground(marromFileira);
        fileira.setPreferredSize(tamFileira);
    }

    private void criarEspaco(JPanel espaco){
        espaco.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        espaco.setBackground(marromEspaco);
        espaco.setPreferredSize(tamEspaco);
    }

    //metodo wrapper para "esvaziar" o espaco com as cartas ampliadas
    private void trocaCartaParaDummy(){
    	java.awt.Component c = espacoExibicaoCarta.getComponent(0);
    	espacoExibicaoCartaLayout.replace(c, dummy);
    }

    private void trocaDummyExibicaoParaCarta(Carta carta){
    	java.awt.Component c = espacoExibicaoCarta.getComponent(0);
    	espacoExibicaoCartaLayout.replace(c, carta);
    }

    @SuppressWarnings("unchecked")
	public JMesa() {
        initComponents();

        ctrlMesa = new ControladorMesa(this, fileiraInfantaria, fileiraLongaDistancia, fileiraCerco, fileiraInfantariaAd, fileiraLongaDistanciaAd, fileiraCercoAd);
        this.atorJogador = new AtorJogador(ctrlMesa);
        getContentPane().setBackground(marrom);
        setVisible(true);
        this.atualizarVisibilidadeTela(1);
        this.acaoBotao(false);

//		deck.setToolTipText("Seu deck tem " + this.cartasDeck.getCartas().size() + " cartas");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    public void exibeMensagem(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

	// <editor-fold defaultstate="collapsed" desc="Generated Code">                          
	private void initComponents() {
		placar = new javax.swing.JPanel();
		fileiraCerco = new Fileira(TipoUnidade.CERCO,null);
		fileiraLongaDistancia = new Fileira(TipoUnidade.LONGA_DISTANCIA,null);
		fileiraInfantaria = new Fileira(TipoUnidade.INFANTARIA,null);
		divisor = new javax.swing.JPanel();
		fileiraInfantariaAd = new Fileira(TipoUnidade.INFANTARIA,null);
		fileiraLongaDistanciaAd = new Fileira(TipoUnidade.LONGA_DISTANCIA,null);
		fileiraCercoAd = new Fileira(TipoUnidade.CERCO,null);
		seletorFileira = new SeletorFileira();
		fileiraCerco.addMouseListener(seletorFileira);
		fileiraLongaDistancia.addMouseListener(seletorFileira);
		fileiraInfantaria.addMouseListener(seletorFileira);
		deck = new javax.swing.JPanel();
		cemiterio = new javax.swing.JPanel();
		deckAd = new javax.swing.JPanel();
		cemiterioAd = new javax.swing.JPanel();
		espacoCartas = new javax.swing.JPanel();
		espacoExibicaoCarta = new javax.swing.JPanel();
		mouseCartas = new MouseCarta();
		btPassar = new javax.swing.JButton();
		btJogar = new javax.swing.JButton();
		jMenuBar = new javax.swing.JMenuBar();
		jMenu = new javax.swing.JMenu();
		jMenuItemConectar = new javax.swing.JMenuItem();
		jMenuItemIniciarPartida = new javax.swing.JMenuItem();
		jMenuItemEncerrarPartida = new javax.swing.JMenuItem();
		jMenuItemDesconectar = new javax.swing.JMenuItem();
		
		//Placar Inimigo
        labelInimigoNome = new javax.swing.JLabel();
        labelInimigoNumeroCartas = new javax.swing.JLabel();
        labelInimigoPoder = new javax.swing.JLabel();
        inimigoUmRound = new javax.swing.JRadioButton();
        inimigoDoisRound = new javax.swing.JRadioButton();
        labelInimicoFaccao = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        meuNome = new javax.swing.JLabel();
        meuNumeroCartas = new javax.swing.JLabel();
        meuPoder = new javax.swing.JLabel();
        meuUmRound = new javax.swing.JRadioButton();
        meuDoisRound = new javax.swing.JRadioButton();
        minhaFaccao = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

        labelInimigoNome.setForeground(new java.awt.Color(0, 0, 0));
        labelInimigoNome.setText("Inimigo");

        labelInimigoNumeroCartas.setForeground(new java.awt.Color(0, 0, 0));
        labelInimigoNumeroCartas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelInimigoNumeroCartas.setText("10");

        labelInimigoPoder.setForeground(new java.awt.Color(0, 0, 0));
        labelInimigoPoder.setText("56");

        labelInimicoFaccao.setForeground(new java.awt.Color(0, 0, 0));
        labelInimicoFaccao.setText("Reinos do Norte");

        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Tem");

        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("cartas na mão");

        labelInimigoNome.setText("Inimigo");

        labelInimigoNumeroCartas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelInimigoNumeroCartas.setText("10");

        labelInimigoPoder.setText("56");

        labelInimicoFaccao.setText("Reinos do Norte");

        jLabel5.setText("Tem");

        jLabel6.setText("cartas na mão");

        jLabel7.setIcon(new javax.swing.ImageIcon("/home/rodrigo/workspace/Gwent_Final/BancoCartas/saruman.png"));

        meuNome.setForeground(new java.awt.Color(0, 0, 0));
        meuNome.setText("Você");

        meuNumeroCartas.setForeground(new java.awt.Color(0, 0, 0));
        meuNumeroCartas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        meuNumeroCartas.setText("10");

        meuPoder.setForeground(new java.awt.Color(0, 0, 0));
        meuPoder.setText("56");

        minhaFaccao.setForeground(new java.awt.Color(0, 0, 0));
        minhaFaccao.setText("Reinos do Norte");

        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Tem");

        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("cartas na mão");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        JPanel placar = new JPanel();
        placar.setBackground(marrom);
        placar.setBorder(BorderFactory.createBevelBorder(
                BevelBorder.LOWERED));

        javax.swing.GroupLayout placarLayout = new javax.swing.GroupLayout(placar);
        placar.setLayout(placarLayout);
        placarLayout.setHorizontalGroup(
                placarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(placarLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(placarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(labelInimigoNome)
                                        .addGroup(placarLayout.createSequentialGroup()
                                                .addComponent(inimigoUmRound)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(inimigoDoisRound))
                                        .addGroup(placarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addGroup(placarLayout.createSequentialGroup()
                                                        .addComponent(labelInimicoFaccao)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(labelInimigoPoder))
                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, placarLayout.createSequentialGroup()
                                                        .addComponent(jLabel5)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(labelInimigoNumeroCartas, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(jLabel6)))
                                        .addComponent(meuNome)
                                        .addGroup(placarLayout.createSequentialGroup()
                                                .addComponent(meuUmRound)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(meuDoisRound))
                                        .addGroup(placarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addGroup(placarLayout.createSequentialGroup()
                                                        .addComponent(minhaFaccao)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(meuPoder))
                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, placarLayout.createSequentialGroup()
                                                        .addComponent(jLabel10)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(meuNumeroCartas, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(jLabel11))))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        placarLayout.setVerticalGroup(
                placarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(placarLayout.createSequentialGroup()
                                //.addGap(18, 18, 18)
                                .addComponent(labelInimigoNome)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(placarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(labelInimicoFaccao)
                                        .addComponent(labelInimigoPoder))
                                .addGroup(placarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(placarLayout.createSequentialGroup()
                                                //.addGap(4, 4, 4)
                                                .addGroup(placarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(inimigoDoisRound)
                                                        .addComponent(inimigoUmRound, javax.swing.GroupLayout.Alignment.TRAILING))
                                                .addGap(4, 4, 4)
                                                .addGroup(placarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(labelInimigoNumeroCartas)
                                                        .addComponent(jLabel5)
                                                        .addComponent(jLabel6))
                                                .addGap(34, 34, 34)
                                                .addComponent(meuNome)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(placarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(minhaFaccao)
                                                        .addComponent(meuPoder))
                                                .addGap(4, 4, 4))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, placarLayout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel7)
                                                .addGap(12, 12, 12)))
                                .addGroup(placarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(meuDoisRound)
                                        .addComponent(meuUmRound, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(6, 6, 6)
                                .addGroup(placarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(meuNumeroCartas)
                                        .addComponent(jLabel10)
                                        .addComponent(jLabel11))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        labelInimigoNome.getAccessibleContext().setAccessibleName("nomeJogador");
        labelInimigoNumeroCartas.getAccessibleContext().setAccessibleName("numeroCartas");
        labelInimigoPoder.getAccessibleContext().setAccessibleName("qtdPoderTotal");

        criarFileira(fileiraCerco);
        criarFileira(fileiraLongaDistancia);
        criarFileira(fileiraInfantaria);

        divisor.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        javax.swing.GroupLayout divisorLayout = new javax.swing.GroupLayout(divisor);
        divisor.setLayout(divisorLayout);
        divisorLayout.setHorizontalGroup(
            divisorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 647, Short.MAX_VALUE)
        );
        divisorLayout.setVerticalGroup(
            divisorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        criarFileira(fileiraInfantariaAd);
        criarFileira(fileiraLongaDistanciaAd);        
        criarFileira(fileiraCercoAd);        

        criarEspaco(deck);

        javax.swing.GroupLayout deckLayout = new javax.swing.GroupLayout(deck);
        deck.setLayout(deckLayout);
        deckLayout.setHorizontalGroup(
            deckLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        deckLayout.setVerticalGroup(
            deckLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        criarEspaco(cemiterio);

        javax.swing.GroupLayout cemiterioLayout = new javax.swing.GroupLayout(cemiterio);
        cemiterio.setLayout(cemiterioLayout);
        cemiterioLayout.setHorizontalGroup(
            cemiterioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        cemiterioLayout.setVerticalGroup(
            cemiterioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        criarEspaco(deckAd);

        javax.swing.GroupLayout deckAdLayout = new javax.swing.GroupLayout(deckAd);
        deckAd.setLayout(deckAdLayout);
        deckAdLayout.setHorizontalGroup(
            deckAdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        deckAdLayout.setVerticalGroup(
            deckAdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        criarEspaco(cemiterioAd);

        javax.swing.GroupLayout cemiterioAdLayout = new javax.swing.GroupLayout(cemiterioAd);
        cemiterioAd.setLayout(cemiterioAdLayout);
        cemiterioAdLayout.setHorizontalGroup(
            cemiterioAdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        cemiterioAdLayout.setVerticalGroup(
            cemiterioAdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        espacoCartas.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        espacoCartas.setBackground(marrom);
        espacoCartas.setPreferredSize(new java.awt.Dimension(300,72));

        espacoCartasLayout = new javax.swing.GroupLayout(espacoCartas);
        espacoCartas.setLayout(espacoCartasLayout);
        espacoCartasLayout.setHorizontalGroup(
            espacoCartasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 295, Short.MAX_VALUE)
        );
        espacoCartasLayout.setVerticalGroup(
            espacoCartasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 72, Short.MAX_VALUE)
        );

        espacoExibicaoCarta.setBackground(marrom);
        espacoExibicaoCarta.setPreferredSize(new java.awt.Dimension(180,244));
        espacoExibicaoCarta.setBorder(BorderFactory.createBevelBorder(
        		BevelBorder.LOWERED));

        espacoExibicaoCartaLayout = new javax.swing.GroupLayout(espacoExibicaoCarta);
        espacoExibicaoCarta.setLayout(espacoExibicaoCartaLayout);
        dummy = new JPanel();
        dummy.setBackground(marrom);
        dummy.setPreferredSize(new java.awt.Dimension(168,234));
        Hexibicao = espacoExibicaoCartaLayout.createSequentialGroup();

        Hexibicao.addComponent(dummy);
        espacoExibicaoCartaLayout.setHorizontalGroup(
            Hexibicao
        );

        Vexibicao = espacoExibicaoCartaLayout.createParallelGroup();
        Vexibicao.addComponent(dummy);
        espacoExibicaoCartaLayout.setVerticalGroup(
            Vexibicao
        );

        btPassar.setText("Passar Turno");

        btJogar.setText("Jogar Carta");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addComponent(placar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(espacoCartas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btJogar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btPassar, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(espacoExibicaoCarta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 92, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fileiraLongaDistancia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fileiraCerco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fileiraCercoAd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fileiraLongaDistanciaAd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fileiraInfantaria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(divisor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fileiraInfantariaAd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(92,92,92)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(deck, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cemiterio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deckAd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cemiterioAd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                        .addComponent(deckAd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cemiterioAd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cemiterio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deck, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btPassar)
                                .addGap(33, 33, 33)
                                .addComponent(btJogar))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(fileiraCercoAd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(fileiraLongaDistanciaAd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(placar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(fileiraInfantariaAd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(divisor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(fileiraInfantaria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(fileiraLongaDistancia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(espacoExibicaoCarta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(fileiraCerco, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(espacoCartas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap())))
        );

        jMenu.setText("Menu");

        jMenuItemConectar.setText("Conectar");
        jMenuItemConectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemConectarActionPerformed(evt);
            }
        });
        jMenu.add(jMenuItemConectar);

        jMenuItemIniciarPartida.setText("Iniciar Partida");
        jMenuItemIniciarPartida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemIniciarPartidaActionPerformed(evt);
            }
        });
        jMenu.add(jMenuItemIniciarPartida);

        jMenuItemEncerrarPartida.setText("Encerrar Partida");
        jMenuItemEncerrarPartida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemEncerrarPartidaActionPerformed(evt);
            }
        });
        jMenu.add(jMenuItemEncerrarPartida);

        jMenuItemDesconectar.setText("Desconectar");
        jMenuItemDesconectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemDesconectarActionPerformed(evt);
            }
        });
        jMenu.add(jMenuItemDesconectar);

        jMenuBar.add(jMenu);

        setJMenuBar(jMenuBar);

        btPassar.setActionCommand(btPassar.getText());
        btJogar.setActionCommand(btJogar.getText());
        MouseBotoes m = new MouseBotoes();
        btPassar.addActionListener(m);
        btJogar.addActionListener(m);

        getContentPane().addMouseListener(new MouseGeral());
        pack();

    }// </editor-fold>

    private void jMenuItemDesconectarActionPerformed(ActionEvent evt) {
        this.atorJogador.desconectar();
    }

    private void jMenuItemIniciarPartidaActionPerformed(ActionEvent evt) {
        this.iniciarPartidaJogadorDois();
    }

    private void jMenuItemEncerrarPartidaActionPerformed(ActionEvent evt) {
//        atorJogador.encerrarPartida();
    }

    private void jMenuItemConectarActionPerformed(ActionEvent evt) {
        this.conectar();
    }



	// Variables declaration - do not modify
	private ControladorMesa ctrlMesa;
	private javax.swing.JPanel cemiterio;
	private javax.swing.JPanel cemiterioAd;
	private javax.swing.JPanel deck;
	private javax.swing.JPanel deckAd;
	private javax.swing.JPanel divisor;
	private javax.swing.JPanel espacoCartas;
	private JPanel dummy;
	private GroupLayout espacoCartasLayout;
	private Group HespacoCartas; 
	private Group VespacoCartas;
	private GroupLayout espacoExibicaoCartaLayout;
	private Fileira fileiraCerco;
	private Fileira fileiraCercoAd;
	private Fileira fileiraInfantaria;
	private Fileira fileiraInfantariaAd;
	private Fileira fileiraLongaDistancia;
	private Fileira fileiraLongaDistanciaAd;
	private SeletorFileira seletorFileira;
	private JPanel expF_cerco;
	private JPanel expF_longd;
	private JPanel expF_inf;
	private javax.swing.JPanel placar;
	private javax.swing.JButton btPassar;
	private javax.swing.JButton btJogar;
	private javax.swing.JPanel espacoExibicaoCarta;
	private Carta cartaSelecionada;
	private MouseCarta mouseCartas;
	private Group Hexibicao;
	private Group Vexibicao;
	private Deck cartasDeck;
	private Deck cartasCemiterio;
	private HashMap<String,Carta> cartasExibicao;
	private HashMap<String,Carta> cartasFileiraEx;
	private boolean passouTurno;
	private boolean jogadorDaVez;
	private boolean precisaSelecionar;
	private javax.swing.JMenu jMenu;
	private javax.swing.JMenuBar jMenuBar;
	private javax.swing.JMenuItem jMenuItemConectar;
	private javax.swing.JMenuItem jMenuItemDesconectar;
	private javax.swing.JMenuItem jMenuItemIniciarPartida;
	private javax.swing.JMenuItem jMenuItemEncerrarPartida;
    //Placar Iinimigo
    private javax.swing.JRadioButton inimigoDoisRound;
    private javax.swing.JRadioButton inimigoUmRound;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel labelInimicoFaccao;
    private javax.swing.JLabel labelInimigoNome;
    private javax.swing.JLabel labelInimigoNumeroCartas;
    private javax.swing.JLabel labelInimigoPoder;
    private javax.swing.JRadioButton meuDoisRound;
    private javax.swing.JLabel meuNome;
    private javax.swing.JLabel meuNumeroCartas;
    private javax.swing.JLabel meuPoder;
    private javax.swing.JRadioButton meuUmRound;
    private javax.swing.JLabel minhaFaccao;
	// End of variables declaration        

	public void atualizaJogadorDaVez(Jogador jogadorDaVez) {
//        jLabelTextoJogadorDaVez.setText(mesa.getJogadorDaVez().getNome());
	}

    public void atualizarPlacar() {
	    if(ctrlMesa.getJogadorAtual().getNome().equals(ctrlMesa.getMesa().getJogadorUm().getNome())){
            meuPoder.setText(ctrlMesa.getMesa().getJogadorDois().getPontuacao().toString());
            meuNumeroCartas.setText("" + ctrlMesa.getMesa().getJogadorDois().getCartasMao().size());

            labelInimigoPoder.setText(ctrlMesa.getJogadorAtual().getPontuacao().toString());
            labelInimigoNumeroCartas.setText("" + ctrlMesa.getJogadorAtual().getCartasMao().size());
        } else{
            meuPoder.setText(ctrlMesa.getJogadorAtual().getPontuacao().toString());
            meuNumeroCartas.setText("" + ctrlMesa.getJogadorAtual().getCartasMao().size());

            labelInimigoPoder.setText(ctrlMesa.getMesa().getJogadorUm().getPontuacao().toString());
            labelInimigoNumeroCartas.setText("" + ctrlMesa.getMesa().getJogadorUm().getCartasMao().size());
        }

        System.out.println("JOGADOR ATUAL PODER: "+ctrlMesa.getJogadorAtual().getPontuacao());
        System.out.println("JOGADOR UM PODER: "+ctrlMesa.getMesa().getJogadorUm().getPontuacao());
        System.out.println("JOGADOR DOIS PODER: "+ctrlMesa.getMesa().getJogadorDois().getPontuacao());


        System.out.println("JOGADOR ATUAL: "+ctrlMesa.getJogadorAtual().getNome());
        System.out.println("JOGADOR UM: "+ctrlMesa.getMesa().getJogadorUm().getNome());
        System.out.println("JOGADOR DOIS: "+ctrlMesa.getMesa().getJogadorDois().getNome());

    }

    public void atualizarNomeFaccaoJogador(){
	    labelInimigoNome.setText(ctrlMesa.getMesa().getJogadorUm().getNome());
	    labelInimicoFaccao.setText(ctrlMesa.getMesa().getJogadorUm().getDeck().getFaccao().getNome());
	    labelInimigoNumeroCartas.setText("" + ctrlMesa.getMesa().getJogadorUm().getCartasMao().size());
	    labelInimigoPoder.setText(ctrlMesa.getMesa().getJogadorUm().getPontuacao().toString());

	    meuNome.setText(ctrlMesa.getMesa().getJogadorDois().getNome());
        minhaFaccao.setText(ctrlMesa.getMesa().getJogadorDois().getDeck().getFaccao().getNome());
        meuNumeroCartas.setText("" + ctrlMesa.getMesa().getJogadorDois().getCartasMao().size());
        meuPoder.setText(ctrlMesa.getMesa().getJogadorDois().getPontuacao().toString());
    }

    private class MouseGeral implements MouseListener{

    	//se nao for um componente carta q foi clicado
		//como o resto da janela inteira estah vinculado a uma instancia dessa classe
		//um clique em qlqr lugar fora da carta anulara a selecao dela
		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			if(cartaSelecionada == null){
				return;
			}

			cartaSelecionada.setBorder(null);
			cartaSelecionada = null;
			Carta cartaHover = (Carta)espacoExibicaoCarta.getComponent(0);
			cartaHover.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}
    }

    private class MouseCarta implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
//			if(passouTurno){
//				return;
//			}

			Carta c = (Carta) e.getSource();

			//carta estah numa fileira, nao pode ser selecionada
			if(c.getParent() instanceof Fileira){
				System.out.println("Está numa fileira"); //debugar habilidade espiao
				return;
			}

			//se jah houver uma carta selecionada remove a borda vermelha
			//da carta previamente selecionada
			if(cartaSelecionada != null){
				cartaSelecionada.setBorder(null);

				//verifica se eh a msma carta sendo clicada novamente
				//se for deve anular a selecao
				if(c.equals(cartaSelecionada)){
					Carta cartaHover = (Carta)espacoExibicaoCarta.getComponent(0);
					cartaHover.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));
					cartaSelecionada = null;
					return;
				}
			}

			cartaSelecionada = c;
			Carta novaCartaHover = cartasExibicao.get(c.getNomeCarta());
			cartaSelecionada.setBorder(BorderFactory.createLineBorder(Color.RED));
            java.awt.Component cartaAnterior = espacoExibicaoCarta.getComponent(0);
			espacoExibicaoCartaLayout.replace(cartaAnterior, novaCartaHover);
			novaCartaHover.setBorder(BorderFactory.createLineBorder(Color.RED,3));
		}


		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

			Object o = e.getSource();
			if(!(o instanceof Carta)){return;}

			if(cartaSelecionada != null){return;}

			Carta c = (Carta) o;
			String n = c.getNomeCarta();
			c = cartasExibicao.get(n);
			c.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
			java.awt.Component co = espacoExibicaoCarta.getComponent(0);	//getComponent(0) pq esse espaco tem sempre apenas um componente (q eh a carta amplificada)
			espacoExibicaoCartaLayout.replace(co, c);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			if(cartaSelecionada != null){return;}
			Object o = e.getSource();
			if(!(o instanceof Carta)){return;}
				java.awt.Component  c = espacoExibicaoCarta.getComponent(0);
				espacoExibicaoCartaLayout.replace(
						c, dummy);

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

    }

    private class MouseBotoes implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			String s = arg0.getActionCommand();
			if(s.equals(btJogar.getActionCommand())){
				if(cartaSelecionada == null){
					JOptionPane.showMessageDialog(null, "Selecione uma carta para jogar!");
					return;
				}
				java.awt.Container c = cartaSelecionada.getParent();
				c.remove(cartaSelecionada);
				c.repaint();
				cartaSelecionada.setBorder(null);
				Carta cartaAdicionada = null;
				String n = cartaSelecionada.getNomeCarta();
				if(cartaSelecionada instanceof CartaUnidade){
					cartaAdicionada = (CartaUnidade) cartasFileiraEx.get(n);  //precisa-se manter um ponteiro para o objeto da carta q vai ser add pra manter o mouselistener
					cartaAdicionada.addMouseListener(mouseCartas);
					TipoHabilidade tipoHabilidadeCarta = null;

					if(cartaSelecionada.getHabilidade() != null){
						tipoHabilidadeCarta = cartaSelecionada.getHabilidade().getTipoHabilidade();
						switch(tipoHabilidadeCarta){
							case MEDICO:
								ctrlMesa.setCemiterio(cartasCemiterio);
								break;

							default:
								break;
						}
					}
//                    precisaSelecionar = ctrlMesa.processarCarta(cartaAdicionada);
                    atorJogador.baixarCarta(cartaAdicionada);
				}
				else if(cartaSelecionada instanceof CartaClima){	//nao precisa adicionar na fileira, soh joga
					CartaClima cartaClima = (CartaClima) cartaSelecionada;
					atorJogador.baixarCarta(cartaClima);
				}

//				if(precisaSelecionar){
//					Fileira fileiraSelecionada = null;
//					JOptionPane.showMessageDialog(null, "Selecione uma fileira para jogar a carta");
//					while(precisaSelecionar){
//						
//					}
//					fileiraSelecionada.incluirCarta(cartaAdicionada);
//				}
                atualizarPlacar();
				cartaSelecionada = null;
				trocaCartaParaDummy();
				revalidate();
				repaint();
			}
			else if(s.equals(btPassar.getActionCommand())){
				if(JOptionPane.showConfirmDialog(null,
						"Tem certeza que deseja passar seu turno?",
						"Passar Turno", JOptionPane.YES_NO_OPTION)
						== JOptionPane.YES_OPTION){
					//trata do caso de passar turno com uma carta selecionada
					if(cartaSelecionada != null){
						cartaSelecionada.setBorder(null);
						cartaSelecionada = null;
						trocaCartaParaDummy();
					}
					acaoBotao(false);
					atorJogador.passarTurno();
					passouTurno = true;
					jogadorDaVez = false;
				}				
			}
		}    	
	}

	private class SeletorFileira implements MouseListener{

		private Fileira fileiraSelecionada;

		public Fileira getFileiraSelecionada(){
			return this.fileiraSelecionada;
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			if(!precisaSelecionar){
				return;
			}
			this.fileiraSelecionada = (Fileira)arg0.getSource();
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			if(!precisaSelecionar){
				return;
			}
			Fileira fileira = (Fileira) arg0.getSource();
			fileira.setBorder(BorderFactory.createLineBorder(Color.RED,3));
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			if(!precisaSelecionar){
				return;
			}
			Fileira fileira = (Fileira) arg0.getSource();
			fileira.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

	}
}
