package src.gwent.entidades;

import javax.swing.ImageIcon;

public class CartaHabilidade extends Carta {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Habilidade habilidade;

	public CartaHabilidade(String nome, ImageIcon img, Habilidade habilidade){
		super(nome, img);
		this.habilidade = habilidade;
	}
	
	public void ativarHabilidade(){
		
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
