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
	private ControladorMesa chamador;
	
	public Habilidade(TipoHabilidade tipoHabilidade){
		this.tipoHabilidade = tipoHabilidade;
	}
	
	public void ativarHabilidadeFileira(Fileira fileiraAtingida){
		switch(this.tipoHabilidade){			
				
			case AGILIDADE:
				break;
				
			case MEDICO:
				CartaUnidade cartaSacada = (CartaUnidade) referenciaDeck.sacarCarta();
				chamador.processarCarta(cartaSacada);
				break;
			
			case ELEVAR_MORAL:
				ArrayList<Carta> cartasFileira = fileiraAtingida.getCartas();
				for(Carta c : cartasFileira){
					if(c instanceof CartaUnidade){						
						CartaUnidade u = (CartaUnidade)c;
						if(u.getHabilidade() != null){		//para ter certeza...
							if(u == fileiraAtingida.getUltimaCartaInclusa()){
								continue;	//eh a carta q ativou a habilidade; nao deve aumentar poder
							}
						}
						u.setPoder(u.getPoder()+1);
					}
				}
				fileiraAtingida.atualizaPoderTotal();
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

	@Override 
	public String toString(){
		return this.tipoHabilidade.toString();
	}
}
