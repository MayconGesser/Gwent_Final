package gwent.controladores;

import java.util.HashMap;

import br.ufsc.inf.leobr.cliente.Jogada;
import gwent.entidades.*;

public class ControladorMesa {
	
	protected HashMap<TipoUnidade,Fileira> fileiras;
    protected Jogador jogadorAtual;
	
	public ControladorMesa(Fileira fileiraInfantaria,
							Fileira fileiraLongaDistancia,
							Fileira fileiraCerco){
		
		this.fileiras = new HashMap<>();
		this.fileiras.put(TipoUnidade.INFANTARIA, fileiraInfantaria);
		this.fileiras.put(TipoUnidade.LONGA_DISTANCIA, fileiraLongaDistancia);
		this.fileiras.put(TipoUnidade.CERCO, fileiraCerco);
	}
	
	public void processarCarta(Carta carta){
		if(carta instanceof CartaUnidade){
			CartaUnidade c = (CartaUnidade) carta;
			TipoUnidade t = c.getTipo();
			this.fileiras.get(t).incluirCarta(c);
			return;
		}
	}

	public void exibeMensagem(String mensagem) {

    }

    public void receberJogada(Jogada jogada) {

    }

    public Jogador getJogadorAtual() {
        return jogadorAtual;
    }

    public void setJogadorAtual(Jogador jogadorAtual) {
        this.jogadorAtual = jogadorAtual;
    }
}
