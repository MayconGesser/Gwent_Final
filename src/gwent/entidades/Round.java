package gwent.entidades;

import br.ufsc.inf.leobr.cliente.Jogada;

import java.util.Collection;

public class Round implements Jogada {

    protected Integer numeroRound;
    protected Jogador vencedor;
    protected Collection<Lance> lances;

    public Round() {
    }

    public Round(Integer numeroRound) {
        this.numeroRound = numeroRound;
    }

    public Integer getNumeroRound() {
        return numeroRound;
    }

    public void setNumeroRound(Integer numeroRound) {
        this.numeroRound = numeroRound;
    }

    public Jogador getVencedor() {
        return vencedor;
    }

    public void setVencedor(Jogador vencedor) {
        this.vencedor = vencedor;
    }

    public Collection<Lance> getLances() {
        return lances;
    }

    public void setLances(Collection<Lance> lances) {
        this.lances = lances;
    }

    public void addLance(Lance lance) {
        this.lances.add(lance);
    }
}
