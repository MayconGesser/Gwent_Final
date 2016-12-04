package gwent.entidades;

import br.ufsc.inf.leobr.cliente.Jogada;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck implements Jogada {

    protected static final long serialVersionUID = 1L;
    protected final List<Carta> cartas;
    protected final Faccao faccao;

    public Deck(Faccao faccao, ArrayList<Carta> cartas) {
        this.faccao = faccao;
        this.cartas = cartas;
    }

    //TODO fazer construtor usado pelo cemiterio
    public Deck(Faccao faccao) {
        this.faccao = faccao;
        this.cartas = new ArrayList<>();
//        this.cartas = BancoCartas.resgatarCartas(faccao);
    }

    public void embaralhar() {
        Collections.shuffle(this.cartas);
    }

    public void addCarta(Carta carta) {
        this.cartas.add(carta);
    }

    public Carta sacarCarta() {
        Carta carta = this.cartas.get(0);
        this.cartas.remove(carta);
        return carta;
    }

    public List<Carta> getCartas() {
        return this.cartas;
    }

    public Faccao getFaccao() {
        return this.faccao;
    }
}
