package gwent.entidades;

public class Habilidade {
	
	private final TipoHabilidade tipoHabilidade;
	private final GatilhoHabilidade gatilhoHabilidade;
	
	public Habilidade(TipoHabilidade tipoHabilidade){
		this.tipoHabilidade = tipoHabilidade;
		this.gatilhoHabilidade = defineHabilidade(tipoHabilidade);
	}
	
	
	private GatilhoHabilidade defineHabilidade(TipoHabilidade tipoHabilidade){
		GatilhoHabilidade retorno = null;
		
		switch(tipoHabilidade){
			case AGILIDADE:
				//
				break;
				
			case MEDICO:
				//
		}
		return retorno;
	}
	
	public void ativarHabilidade(){
		
	}
	
	public TipoHabilidade getTipoHabilidade(){
		return this.tipoHabilidade;
	}
}
