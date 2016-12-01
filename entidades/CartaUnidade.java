package entidades;

import javax.swing.ImageIcon;

public class CartaUnidade extends Carta {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int poder; 
	private final Habilidade habilidade;
	private final TipoUnidade tipo;
	
	public CartaUnidade(String nome, ImageIcon img, int poder, Habilidade hab,
			TipoUnidade tipo){
		super(nome,img);
		this.poder = poder; 
		this.habilidade = hab;
		this.tipo = tipo;
	}

	public int getPoder() {
		return poder;
	}

	public void setPoder(int poder) {
		this.poder = poder;
	}

	public Habilidade getHabilidade() {
		return habilidade;
	}
	
	public TipoUnidade getTipo(){
		return this.tipo;
	}
}
