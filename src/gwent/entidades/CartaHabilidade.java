package gwent.entidades;

import javax.swing.ImageIcon;

public class CartaHabilidade extends Carta {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CartaHabilidade(String nome, ImageIcon img, Habilidade habilidade){
		super(nome, img, habilidade);		
	}
	
	public CartaHabilidade(String nome, Habilidade habilidade){
		super(nome,habilidade);
	}
	
	@Override
	public Habilidade getHabilidade(){
		return this.habilidade;
	}
	
	@Override
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
	
	@Override
	public void setEstampa(ImageIcon estampa){
		this.estampa = estampa;
		this.setIcon(estampa);
	}
}
