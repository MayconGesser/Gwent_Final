package gwent.entidades;

import gwent.controladores.ControladorMesa;

public class Habilidade {
	
	private final TipoHabilidade tipoHabilidade;
	private final GatilhoHabilidade gatilhoHabilidade;
	private final Object referencia;
	private final ControladorMesa chamador;
	
	public Habilidade(TipoHabilidade tipoHabilidade, Object referencia, ControladorMesa chamador){
		this.tipoHabilidade = tipoHabilidade;
		this.referencia = referencia;
		this.gatilhoHabilidade = defineHabilidade(tipoHabilidade);
		this.chamador = chamador;
	} 
	
	
	private GatilhoHabilidade defineHabilidade(TipoHabilidade tipoHabilidade){
		GatilhoHabilidade retorno = null;
		
		switch(tipoHabilidade){
			case AGILIDADE:
				//
				break;
				
			case MEDICO:
				retorno = new GatilhoHabilidade(){
				@Override
				public void ativarHabilidade(){
					Deck cemiterio = (Deck) referencia;
					int i = new java.util.Random().nextInt(cemiterio.getCartas().size());
					CartaUnidade carta = (CartaUnidade) cemiterio.sacarCarta(i);
					chamador.processarCarta(carta);
				}
					
			};
			break;
		}
		return retorno;
	}
	
	public void ativarHabilidade(){
		
	}
	
	public TipoHabilidade getTipoHabilidade(){
		return this.tipoHabilidade;
	}
}
