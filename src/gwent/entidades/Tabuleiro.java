package gwent.entidades;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package tabuleironetbeans;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Group;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import gwent.controladores.ControladorMesa;

/**
 *
 * @author viwjcq
 */
public class Tabuleiro extends javax.swing.JFrame {

    /**
     * Creates new form Tabuleiro
     */
    final Color marrom = new Color(102,51,0);
    final Color marromFileira = new Color(156,73,0);
    final Color marromEspaco = new Color(51,25,0);
    final Dimension tamFileira = new Dimension(624,100);
    final Dimension tamEspaco = new Dimension(90,110);
    
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
    
    public Tabuleiro() {
        initComponents();
        
        ctrlMesa = new ControladorMesa(
        			fileiraInfantaria, fileiraLongaDistancia, fileiraCerco
        		);
        getContentPane().setBackground(marrom);
        setVisible(true);
        
        File bin = new File("BancoCartas/ReinosNorte/cartas.bin");
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
			fis = new FileInputStream(bin);
			ois = new ObjectInputStream(fis);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(IOException e){
			e.printStackTrace();
		}
        try {
			cartasDeck = (Deck)ois.readObject();
			cartasExibicao = (HashMap<String,Carta>)ois.readObject();
			cartasFileiraEx = (HashMap<String,Carta>)ois.readObject();
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        cartasDeck.embaralhar();
		Group gh = espacoCartasLayout.createSequentialGroup();
		Group gv = espacoCartasLayout.createParallelGroup();
		MouseCarta b = new MouseCarta();
		
		for(int x = 0; x<10; x++){			
			Carta carta = cartasDeck.sacarCarta();
			carta.addMouseListener(b);
			carta.setName(carta.getNomeCarta());
			gh.addComponent(carta);
			gv.addComponent(carta);
		}
		
		Carta carta = this.cartasDeck.sacarCarta();
		cartasCemiterio = new Deck(cartasDeck.getFaccao());
		cartasCemiterio.addCarta(carta);
		
		espacoCartasLayout.setHorizontalGroup(gh);
		espacoCartasLayout.setVerticalGroup(gv);
		deck.setToolTipText("Seu deck tem " + this.cartasDeck.getCartas().size() + " cartas");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
    
    
    @SuppressWarnings("unchecked")
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



        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        JPanel placar = new JPanel();
        placar.setBackground(Color.BLUE);

        javax.swing.GroupLayout placarLayout = new javax.swing.GroupLayout(placar);
        placar.setLayout(placarLayout);
        placarLayout.setHorizontalGroup(
            placarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 191, Short.MAX_VALUE)
        );
        placarLayout.setVerticalGroup(
            placarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 110, Short.MAX_VALUE)
        );

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

        javax.swing.GroupLayout fileiraInfantariaAdLayout = new javax.swing.GroupLayout(fileiraInfantariaAd);
        fileiraInfantariaAd.setLayout(fileiraInfantariaAdLayout);
        fileiraInfantariaAdLayout.setHorizontalGroup(
            fileiraInfantariaAdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        fileiraInfantariaAdLayout.setVerticalGroup(
            fileiraInfantariaAdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        criarFileira(fileiraLongaDistanciaAd);

        javax.swing.GroupLayout fileiraLongaDistanciaAdLayout = new javax.swing.GroupLayout(fileiraLongaDistanciaAd);
        fileiraLongaDistanciaAd.setLayout(fileiraLongaDistanciaAdLayout);
        fileiraLongaDistanciaAdLayout.setHorizontalGroup(
            fileiraLongaDistanciaAdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        fileiraLongaDistanciaAdLayout.setVerticalGroup(
            fileiraLongaDistanciaAdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        criarFileira(fileiraCercoAd);

        javax.swing.GroupLayout fileiraCercoAdLayout = new javax.swing.GroupLayout(fileiraCercoAd);
        fileiraCercoAd.setLayout(fileiraCercoAdLayout);
        fileiraCercoAdLayout.setHorizontalGroup(
            fileiraCercoAdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        fileiraCercoAdLayout.setVerticalGroup(
            fileiraCercoAdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

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
        meh m = new meh();
        btPassar.addActionListener(m);
        btJogar.addActionListener(m);
        
        getContentPane().addMouseListener(new MouseGeral());
        pack();

    }// </editor-fold>

    protected void jMenuItemDesconectarActionPerformed(ActionEvent evt) {
//        atorJogador.desconectar();
    }

    protected void jMenuItemIniciarPartidaActionPerformed(ActionEvent evt) {
//        atorJogador.iniciarPartida();
    }

    protected void jMenuItemEncerrarPartidaActionPerformed(ActionEvent evt) {
//        atorJogador.encerrarPartida();
    }

    protected void jMenuItemConectarActionPerformed(ActionEvent evt) {
//        this.conectar();
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
    private javax.swing.JMenu jMenu;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JMenuItem jMenuItemConectar;
    private javax.swing.JMenuItem jMenuItemDesconectar;
    private javax.swing.JMenuItem jMenuItemIniciarPartida;
    private javax.swing.JMenuItem jMenuItemEncerrarPartida;
    // End of variables declaration        
    
    
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
    
    private class meh implements ActionListener{

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
									
				}
				
				cartaAdicionada.addMouseListener(new MouseCarta());
				ctrlMesa.processarCarta(cartaAdicionada);
				cartaSelecionada = null;
				trocaCartaParaDummy(); 
				jogadorDaVez = false;
				
			}
			else if(s.equals(btPassar.getActionCommand())){
				if(JOptionPane.showConfirmDialog(null, 
						"Tem certeza que deseja passar seu turno?",
						"Passar Turno", JOptionPane.YES_NO_OPTION)
						== JOptionPane.YES_OPTION){
					btJogar.setEnabled(false);
					btPassar.setEnabled(false);
					
					//trata do caso de passar turno com uma carta selecionada
					if(cartaSelecionada != null){
						cartaSelecionada.setBorder(null);
						cartaSelecionada = null;
						trocaCartaParaDummy();
					}
					passouTurno = true;
					jogadorDaVez = false;
				}				
			}
		}    	
    }
}
