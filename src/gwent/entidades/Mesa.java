package gwent.entidades;

import br.ufsc.inf.leobr.cliente.Jogada;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class Mesa implements Jogada {

    protected Jogador jogadorUm;
    protected Jogador jogadorDois;
    protected Jogador jogadorDaVez;
    protected Collection<Round> rounds;
    protected Collection<Fileira> fileiras;
    protected StatusMesa statusMesa;
    protected Round roundAtual;
    protected List<Jogador> jogadores;

    public Mesa() {
        this.roundAtual = new Round();
        this.rounds = new ArrayList<>();
    }

    public void criarJogadores() {
        jogadorUm = jogadores.get(0);
        jogadorUm.setIdJogador(1);

        jogadorDois = jogadores.get(1);
        jogadorDois.setIdJogador(2);
    }

    public void embaralhar() {
        if (this.jogadorUm.getDeck() != null) {
            this.jogadorUm.getDeck().embaralhar();
            this.jogadorUm.preencheCartasMao();
        }
        if (this.jogadorDois.getDeck() != null) {
            this.jogadorDois.getDeck().embaralhar();
            this.jogadorDois.preencheCartasMao();
        }
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

    public Jogador getJogadorNaoAtual(Jogador jogadorAtual) {
        return jogadorAtual.getNome().equals(this.jogadorUm.getNome()) ? this.jogadorDois : this.jogadorUm;
    }

    public void inativarJogador(Jogador jogadorAtual) {
        Jogador jogador = jogadorAtual.getNome().equals(this.jogadorUm.getNome()) ? this.jogadorUm : this.jogadorDois;
        jogador.setStatusJogador(StatusJogador.INATIVO);
    }

    public void removeCartaMaoJogador(Lance lance) {
        if (lance.getJogador().getNome().equals(this.getJogadorUm().getNome())) {
            this.jogadorUm.removeCartaMao(lance.getCarta());
        } else {
            this.jogadorDois.removeCartaMao(lance.getCarta());
        }
    }

    public boolean verificaFimDoRound() {
        return this.jogadorUm.getStatusJogador().equals(StatusJogador.INATIVO) &&
                this.jogadorDois.getStatusJogador().equals(StatusJogador.INATIVO);
    }

    public Jogador verificaVencedorRound() {
        if (this.jogadorUm.getPontuacao() > this.jogadorDois.getPontuacao())
            return this.jogadorUm;
        else if (this.jogadorUm.getPontuacao() < this.jogadorDois.getPontuacao())
            return this.jogadorDois;
        else {
            int idJogadorAleatorio = new Random().nextInt(1);
            return this.jogadores.get(idJogadorAleatorio);
        }
    }

    public void atualizaRoundAtual(Jogador jogadorVencedor) {
        this.roundAtual.setVencedor(jogadorVencedor);
        this.roundAtual.setNumeroRound(this.rounds.size() + 1);
    }

    public Jogador getJogador(Jogador jogador) {
        return this.jogadorUm.getIdJogador().equals(jogador.getIdJogador()) ? this.jogadorUm : this.jogadorDois;
    }
}
