package gwent.entidades;

import java.util.ArrayList;

import br.ufsc.inf.leobr.cliente.Jogada;
import gwent.controladores.ControladorMesa;

public class Habilidade implements Jogada {
	
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private final TipoHabilidade tipoHabilidade;
	private Deck referenciaDeck;
	private Fileira referenciaFileira;
	private ControladorMesa chamador;
	
	public Habilidade(TipoHabilidade tipoHabilidade){
		this.tipoHabilidade = tipoHabilidade;
	}
	
	public void ativarHabilidade(){
		switch(this.tipoHabilidade){			
				
			case AGILIDADE:
				break;
				
			case MEDICO:
				CartaUnidade cartaSacada = (CartaUnidade) referenciaDeck.sacarCarta();
				chamador.processarCarta(cartaSacada);
				break;
			
			case ELEVAR_MORAL:
				ArrayList<Carta> cartasFileira = referenciaFileira.getCartas();
				for(Carta c : cartasFileira){
					if(c instanceof CartaUnidade){
						CartaUnidade u = (CartaUnidade)c;
						u.setPoder(u.getPoder()+1);
					}
				}
				//TODO: tratar alteracao de poder da carta q ativa a habilidade
				referenciaFileira.atualizaPoderTotal();
				break;
				
			default:
				break;
		}

		return;
	}
	


	public void setReferenciaDeck(Deck referencia) {		
		this.referenciaDeck = referencia;
	}

	public void setChamador(ControladorMesa chamador) {
		this.chamador = chamador;
	}

	public Deck getReferenciaDeck() {
		return referenciaDeck;
	}

	public ControladorMesa getChamador() {
		return chamador;
	}
	
	public TipoHabilidade getTipoHabilidade(){
		return this.tipoHabilidade;
	}

	public Fileira getReferenciaFileira() {
		return referenciaFileira;
	}

	public void setReferenciaFileira(Fileira referenciaFileira) {
		this.referenciaFileira = referenciaFileira;
	}
}
