package gwent.entidades;

import br.ufsc.inf.leobr.cliente.Jogada;

import java.util.Collection;
import java.util.List;

public class Mesa implements Jogada {

    protected Jogador jogadorUm;
    protected Jogador jogadorDois;
    protected Jogador jogadorDaVez;
    protected Collection<Round> rounds;
    protected Collection<Fileira> fileiras;
    protected StatusMesa statusMesa;
    protected Round roundAtual;
    protected List<Jogador> jogadores;

    public void criarJogadores(Faccao faccao) {
        jogadorUm = jogadores.get(0);
        jogadorUm.setIdJogador(1);

        jogadorDois = jogadores.get(1);
        jogadorDois.setIdJogador(2);

        if (jogadorUm.getDeck() == null)
            jogadorUm.setDeck(BancoCartas.resgatarCartas(faccao));
        else if (jogadorDois.getDeck() == null)
            jogadorDois.setDeck(BancoCartas.resgatarCartas(faccao));

    }

    public void embaralhar() {
        if (this.jogadorUm.getDeck() != null)
            this.jogadorUm.getDeck().embaralhar();
        if (this.jogadorDois.getDeck() != null)
            this.jogadorDois.getDeck().embaralhar();
    }

    public void iniciarRound(Jogador jogadorDaVez) {
        this.setRoundAtual(new Round());
        this.setJogadorDaVez(jogadorDaVez);
    }

    public void addLance(Lance lance) {
        this.roundAtual.addLance(lance);
    }

    //Get e Set

    public List<Jogador> getJogadores() {
        return jogadores;
    }

    public void setJogadores(List<Jogador> jogadores) {
        this.jogadores = jogadores;
    }

    public Jogador getJogadorUm() {
        return jogadorUm;
    }

    public void setJogadorUm(Jogador jogadorUm) {
        this.jogadorUm = jogadorUm;
    }

    public Jogador getJogadorDois() {
        return jogadorDois;
    }

    public void setJogadorDois(Jogador jogadorDois) {
        this.jogadorDois = jogadorDois;
    }

    public Jogador getJogadorDaVez() {
        return jogadorDaVez;
    }

    public void setJogadorDaVez(Jogador jogadorDaVez) {
        this.jogadorDaVez = jogadorDaVez;
    }

    public Collection<Round> getRounds() {
        return rounds;
    }

    public void setRounds(Collection<Round> rounds) {
        this.rounds = rounds;
    }

    public Collection<Fileira> getFileiras() {
        return fileiras;
    }

    public void setFileiras(Collection<Fileira> fileiras) {
        this.fileiras = fileiras;
    }

    public Round getRoundAtual() {
        return roundAtual;
    }

    public void setRoundAtual(Round roundAtual) {
        this.roundAtual = roundAtual;
    }

    public StatusMesa getStatusMesa() {
        return statusMesa;
    }

    public void setStatusMesa(StatusMesa statusMesa) {
        this.statusMesa = statusMesa;
    }
}