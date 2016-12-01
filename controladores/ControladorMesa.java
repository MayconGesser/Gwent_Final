package controladores;

import java.util.HashMap;

import entidades.Carta;
import entidades.CartaUnidade;
import entidades.Fileira;
import entidades.TipoUnidade;

public class ControladorMesa {
	
	private HashMap<TipoUnidade,Fileira> fileiras;
	
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
}
