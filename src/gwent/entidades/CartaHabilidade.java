package gwent.entidades;

import javax.swing.ImageIcon;

public class CartaHabilidade extends Carta {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nome;

	public CartaHabilidade(String nome, ImageIcon img){
		super(nome, img);
	}

	@Override
	public String getNomeCarta() {
		// TODO Auto-generated method stub
		return this.nome;
	}

	@Override
	public ImageIcon getEstampa() {
		// TODO Auto-generated method stub
		return this.estampa;
	}
}
