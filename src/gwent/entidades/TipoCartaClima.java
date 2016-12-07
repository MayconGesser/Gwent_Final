package gwent.entidades;

import br.ufsc.inf.leobr.cliente.Jogada;

public enum TipoCartaClima implements Jogada{
	
	
	GEADA_MORDAZ(TipoUnidade.INFANTARIA),
	NEBLINA_IMPENETRAVEL(TipoUnidade.LONGA_DISTANCIA),
	CHUVA_TORRENCIAL(TipoUnidade.CERCO),
	TEMPO_LIMPO(TipoUnidade.INFANTARIA,TipoUnidade.CERCO,TipoUnidade.LONGA_DISTANCIA);
	
	private TipoUnidade tipoFileiraAtingida;
	private TipoUnidade tipoFileiraAtingida2;
	private TipoUnidade tipoFileiraAtingida3;
	
	public TipoUnidade getFileiraAtingida(){
		return tipoFileiraAtingida;
	}
	
	//invocado pela carta TEMPO LIMPO
	public TipoUnidade[] getFileirasAtingidas(){
		TipoUnidade[] retorno = {tipoFileiraAtingida,tipoFileiraAtingida2,tipoFileiraAtingida3};
		return retorno;
	}
	
	private TipoCartaClima(TipoUnidade tipoFileira){
		tipoFileiraAtingida = tipoFileira;
	}
	
	private TipoCartaClima(TipoUnidade tiposFileira, TipoUnidade tipoFileira2, TipoUnidade tipoFileira3){
		tipoFileiraAtingida = tiposFileira;
		tipoFileiraAtingida2 = tipoFileira2;
		tipoFileiraAtingida3 = tipoFileira3;
	}
}
