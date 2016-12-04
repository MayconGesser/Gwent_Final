package gwent.entidades;

import java.io.Serializable;

import gwent.controladores.ControladorMesa;

public class Habilidade implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final TipoHabilidade tipoHabilidade;	
	private Deck referencia;
	private ControladorMesa chamador;
	
	public Habilidade(TipoHabilidade tipoHabilidade){
		this.tipoHabilidade = tipoHabilidade;		
	}
	
	public void ativarHabilidade(){
		switch(this.tipoHabilidade){			
				
			case AGILIDADE:
				break;
				
			case MEDICO:
				CartaUnidade cartaSacada = (CartaUnidade) referencia.sacarCarta();
				chamador.processarCarta(cartaSacada);
				break;
				
			default:
				break;
		}
		
		return;
	}
	
	
	
	public void setReferencia(Deck referencia) {
		this.referencia = referencia;
	}

	public void setChamador(ControladorMesa chamador) {
		this.chamador = chamador;
	}

	public Deck getReferencia() {
		return referencia;
	}

	public ControladorMesa getChamador() {
		return chamador;
	}
	
	public TipoHabilidade getTipoHabilidade(){
		return this.tipoHabilidade;
	}
}
