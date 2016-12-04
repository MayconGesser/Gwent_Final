package gwent.entidades;

import br.ufsc.inf.leobr.cliente.Jogada;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Jogador implements Jogada {

    protected Integer idJogador;
    protected String nome;
    protected List<Carta> cartasMao;
    protected Integer pontuacao;
    protected StatusJogador statusJogador;
    protected Deck deck;

    public Jogador(Integer idJogador, String nome) {
        this.idJogador = idJogador;
        this.nome = nome;
        this.cartasMao = new ArrayList<>();
        this.pontuacao = 0;
        this.statusJogador = StatusJogador.ATIVO;
    }

    public Jogador(Integer idJogador, String nome, Deck deck) {
        this.idJogador = idJogador;
        this.nome = nome;
        this.pontuacao = 0;
        this.deck = deck;
        this.preencheCartasMao();
        this.statusJogador = StatusJogador.ATIVO;
    }

    public Carta sacarCarta() {
        Carta carta = this.cartasMao.get(0);
        this.cartasMao.remove(carta);
        return carta;
    }

    public void preencheCartasMao() {
        if (this.cartasMao == null)
            this.cartasMao = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            this.cartasMao.add(this.deck.sacarCarta());
        }
    }

    public Integer getIdJogador() {
        return idJogador;
    }

    public void setIdJogador(Integer idJogador) {
        this.idJogador = idJogador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Collection<Carta> getCartasMao() {
        return cartasMao;
    }

    public void setCartasMao(List<Carta> cartasMao) {
        this.cartasMao = cartasMao;
    }

    public Integer getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(Integer pontuacao) {
        this.pontuacao = pontuacao;
    }

    public StatusJogador getStatusJogador() {
        return statusJogador;
    }

    public void setStatusJogador(StatusJogador statusJogador) {
        this.statusJogador = statusJogador;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }
}
