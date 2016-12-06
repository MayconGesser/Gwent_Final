package gwent.entidades;

import br.ufsc.inf.leobr.cliente.Jogada;

public enum TipoCartaClima implements Jogada{
	GEADA_MORDAZ(TipoUnidade.INFANTARIA),
	NEBLINA_IMPENETRAVEL(TipoUnidade.LONGA_DISTANCIA),
	CHUVA_TORRENCIAL(TipoUnidade.CERCO);
	
	private TipoUnidade tipoFileiraAtingida;
	
	public TipoUnidade getFileiraAtingida(){
		return tipoFileiraAtingida;
	}
	
	private TipoCartaClima(TipoUnidade tipoFileira){
		tipoFileiraAtingida = tipoFileira;
	}
}
