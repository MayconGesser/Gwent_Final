package gwent.entidades;

import java.util.ArrayList;

import br.ufsc.inf.leobr.cliente.Jogada;

public class LanceCartaAgrupar extends Lance implements Jogada{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ListaCartasAgrupar<CartaUnidade> cartasAgrupar;
	
	public LanceCartaAgrupar(Carta carta, Jogador jogador) {
		super(carta, jogador);		
		this.cartasAgrupar = new ListaCartasAgrupar<>();
	}
	
	public void setCartaAgrupar(CartaUnidade carta){
		this.cartasAgrupar.add(carta);
	}
	
	public ListaCartasAgrupar<CartaUnidade> getCartasAgrupar(){
		return this.cartasAgrupar;
	}
}
