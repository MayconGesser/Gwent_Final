package gwent.visao;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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
        if (mesa.getStatusMesa().equals(StatusMesa.INICAR_PARTIDA)) {
            this.iniciarPartida(mesa);
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

    private void iniciarPartida(Mesa mesa) {
        this.atualizaCamposInicioPartida(mesa);
    }

    public void inicioPartida(Mesa mesa) {
        this.preencherCartas(mesa.getJogadorUm());
        this.ctrlMesa.setJogadorAtual(mesa.getJogadorUm());
        this.acaoBotao(true);
        this.exibeMensagem("Uma nova partida vai iniciar");
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

    private void preencherCartas(Jogador jogador) {
		Group gh = espacoCartasLayout.createSequentialGroup();
		Group gv = espacoCartasLayout.createParallelGroup();
		MouseCarta b = new MouseCarta();

		//apenas para teste de carta de clima
//		ArrayList<Carta> d = (ArrayList<Carta>)jogador.getDeck().getCartas();
//		CartaClima geadamordaz = null;
//		int i = 0;
//		for(Carta c : d){
//			i++;
//			if(c instanceof CartaClima){
//				geadamordaz = (CartaClima) c;
//			}
//		}
//		if(geadamordaz != null){
//			jogador.getDeck().getCartas().remove(i);
//			geadamordaz.addMouseListener(b);
//			geadamordaz.setName(geadamordaz.getNomeCarta());
//			gh.addComponent(geadamordaz);
//			gv.addComponent(geadamordaz);
//		}
//
		for(int x = 0; x<10; x++) {
			Carta carta = jogador.mostrarCarta(x);
			carta.addMouseListener(b);
			carta.setName(carta.getNomeCarta());
			gh.addComponent(carta);
			gv.addComponent(carta);
		}
		
		espacoCartasLayout.setHorizontalGroup(gh);
		espacoCartasLayout.setVerticalGroup(gv);

        Map<String, Object> mapDeck = BancoCartas.resgatarDeck(jogador.getDeck().getFaccao());

        cartasExibicao = (HashMap<String, Carta>) mapDeck.get("exibicao");
		cartasFileiraEx = (HashMap<String, Carta>) mapDeck.get("fileiras");
	}

    private void iniciarPartida() {
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
		btPassar = new javax.swing.JButton();
		btJogar = new javax.swing.JButton();
		jMenuBar = new javax.swing.JMenuBar();
		jMenu = new javax.swing.JMenu();
		jMenuItemConectar = new javax.swing.JMenuItem();
		jMenuItemIniciarPartida = new javax.swing.JMenuItem();
		jMenuItemEncerrarPartida = new javax.swing.JMenuItem();
		jMenuItemDesconectar = new javax.swing.JMenuItem();
		
		//Placar Inimigo
        placarInimigoJLabel1 = new javax.swing.JLabel();
        placarInimigoNumeroCartasMao = new javax.swing.JLabel();
        placarInimigoJLabel3 = new javax.swing.JLabel();
        placarInimigoUmRound = new javax.swing.JRadioButton();
        placarInimigoDoisRound = new javax.swing.JRadioButton();
        placarInimigoNomeFaccao = new javax.swing.JLabel();
        placarInimigoJLabel5 = new javax.swing.JLabel();
        placarInimigoJLabel6 = new javax.swing.JLabel();
        placarInimigoJLabel7 = new javax.swing.JLabel();

        setForeground(new java.awt.Color(0, 0, 0));

        placarInimigoJLabel1.setForeground(new java.awt.Color(0, 0, 0));
        placarInimigoJLabel1.setText("Rodrigo");

        placarInimigoNumeroCartasMao.setForeground(new java.awt.Color(0, 0, 0));
        placarInimigoNumeroCartasMao.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        placarInimigoNumeroCartasMao.setText("7");

        placarInimigoJLabel3.setForeground(new java.awt.Color(0, 0, 0));
        placarInimigoJLabel3.setText("56");

        placarInimigoUmRound.setForeground(new java.awt.Color(204, 0, 0));

        placarInimigoDoisRound.setForeground(new java.awt.Color(204, 0, 0));

        placarInimigoNomeFaccao.setForeground(new java.awt.Color(0, 0, 0));
        placarInimigoNomeFaccao.setText("Reinos do Norte");

        placarInimigoJLabel5.setForeground(new java.awt.Color(0, 0, 0));
        placarInimigoJLabel5.setText("Tem");

        placarInimigoJLabel6.setForeground(new java.awt.Color(0, 0, 0));
        placarInimigoJLabel6.setText("cartas na mão");

        placarInimigoJLabel7.setIcon(new javax.swing.ImageIcon("BancoCartas/gandalf.png")); 
        
        //Placar do jogador
        jLabel1 = new javax.swing.JLabel();
        numeroCartasMao = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        umRound = new javax.swing.JRadioButton();
        doisRound = new javax.swing.JRadioButton();
        nomeFaccao = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setForeground(new java.awt.Color(0, 0, 0));

        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Rodrigo");

        numeroCartasMao.setForeground(new java.awt.Color(0, 0, 0));
        numeroCartasMao.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        numeroCartasMao.setText("7");

        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("56");

        umRound.setForeground(new java.awt.Color(204, 0, 0));

        doisRound.setForeground(new java.awt.Color(204, 0, 0));

        nomeFaccao.setForeground(new java.awt.Color(0, 0, 0));
        nomeFaccao.setText("Reinos do Norte");

        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Tem");

        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("cartas na mão");

        jLabel7.setIcon(new javax.swing.ImageIcon("/home/rodrigo/workspace/Gwent_Final/BancoCartas/saruman.png")); // NOI18N


        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        JPanel placarInimigo = new JPanel();
        placarInimigo.setBackground(marrom);
        placarInimigo.setBorder(BorderFactory.createBevelBorder(
                BevelBorder.LOWERED));

        javax.swing.GroupLayout placarInimigoLayout = new javax.swing.GroupLayout(placarInimigo);
        placarInimigo.setLayout(placarInimigoLayout);
        placarInimigoLayout.setHorizontalGroup(
                placarInimigoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(placarInimigoLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(placarInimigoJLabel7)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(placarInimigoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(placarInimigoLayout.createSequentialGroup()
                            .addComponent(placarInimigoUmRound)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(placarInimigoDoisRound)
                            .addGap(127, 127, 127)
                            .addComponent(placarInimigoJLabel3))
                        .addComponent(placarInimigoNomeFaccao)
                        .addComponent(placarInimigoJLabel1)
                        .addGroup(placarInimigoLayout.createSequentialGroup()
                            .addComponent(placarInimigoJLabel5)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(placarInimigoNumeroCartasMao, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(placarInimigoJLabel6)))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
            placarInimigoLayout.setVerticalGroup(
                placarInimigoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(placarInimigoLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(placarInimigoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(placarInimigoJLabel7)
                        .addGroup(placarInimigoLayout.createSequentialGroup()
                            .addComponent(placarInimigoNomeFaccao)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(placarInimigoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(placarInimigoUmRound)
                                .addComponent(placarInimigoDoisRound)
                                .addComponent(placarInimigoJLabel3))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(placarInimigoJLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(placarInimigoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(placarInimigoNumeroCartasMao)
                                .addComponent(placarInimigoJLabel5)
                                .addComponent(placarInimigoJLabel6))))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );

            placarInimigoJLabel1.getAccessibleContext().setAccessibleName("nomeJogador");
            placarInimigoNumeroCartasMao.getAccessibleContext().setAccessibleName("numeroCartas");
            placarInimigoJLabel3.getAccessibleContext().setAccessibleName("qtdPoderTotal");
            
            JPanel placar = new JPanel();
            placarInimigo.setBackground(marrom);
            placarInimigo.setBorder(BorderFactory.createBevelBorder(
                    BevelBorder.LOWERED));
            
            javax.swing.GroupLayout placarLayout = new javax.swing.GroupLayout(this);
            this.setLayout(placarLayout);
            placarLayout.setHorizontalGroup(
                placarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(placarLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel7)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(placarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(placarLayout.createSequentialGroup()
                            .addComponent(umRound)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(doisRound)
                            .addGap(127, 127, 127)
                            .addComponent(jLabel3))
                        .addComponent(nomeFaccao)
                        .addComponent(jLabel1)
                        .addGroup(placarLayout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(numeroCartasMao, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel6)))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
            placarLayout.setVerticalGroup(
                placarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(placarLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(placarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel7)
                        .addGroup(placarLayout.createSequentialGroup()
                            .addComponent(nomeFaccao)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(placarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(umRound)
                                .addComponent(doisRound)
                                .addComponent(jLabel3))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(placarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(numeroCartasMao)
                                .addComponent(jLabel5)
                                .addComponent(jLabel6))))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );

            jLabel1.getAccessibleContext().setAccessibleName("nomeJogador");
            numeroCartasMao.getAccessibleContext().setAccessibleName("numeroCartas");
            jLabel3.getAccessibleContext().setAccessibleName("qtdPoderTotal");

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
                        .addComponent(placarInimigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                                    .addComponent(placarInimigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        this.iniciarPartida();
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
    private javax.swing.JRadioButton placarInimigoDoisRound;
    private javax.swing.JLabel placarInimigoJLabel1;
    private javax.swing.JLabel placarInimigoNumeroCartasMao;
    private javax.swing.JLabel placarInimigoJLabel3;
    private javax.swing.JLabel placarInimigoJLabel5;
    private javax.swing.JLabel placarInimigoJLabel6;
    private javax.swing.JLabel placarInimigoJLabel7;
    private javax.swing.JLabel placarInimigoNomeFaccao;
    private javax.swing.JRadioButton placarInimigoUmRound;
    //Placar Jogador
    private javax.swing.JRadioButton doisRound;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel numeroCartasMao;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel nomeFaccao;
    private javax.swing.JRadioButton umRound;
	// End of variables declaration        

	public void atualizaJogadorDaVez(Jogador jogadorDaVez) {
//        jLabelTextoJogadorDaVez.setText(mesa.getJogadorDaVez().getNome());
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
			if(passouTurno){
				return;
			}

			Carta c = (Carta) e.getSource();

			//carta estah numa fileira, nao pode ser selecionada
			if(c.getParent() instanceof Fileira){
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
					cartaAdicionada.addMouseListener(new MouseCarta());
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
                    acaoBotao(false);
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
				cartaSelecionada = null;
				trocaCartaParaDummy();
				jogadorDaVez = false;
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
