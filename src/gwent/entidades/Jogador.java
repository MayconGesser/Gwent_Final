package gwent.entidades;

import java.util.ArrayList;
import java.util.Collection;

public class Jogador {

    protected Integer idJogador;
    protected String nome;
    protected Collection<Carta> cartasMao;
    protected Integer pontuacao;
    protected StatusJogador statusJogador;

    public Jogador(Integer idJogador, String nome) {
        this.idJogador = idJogador;
        this.nome = nome;
        this.cartasMao = new ArrayList<>();
        this.pontuacao = 0;
        this.statusJogador = StatusJogador.ATIVO;
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

    public void setCartasMao(Collection<Carta> cartasMao) {
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
}
