package gwent.entidades;

import javax.swing.ImageIcon;

public class CartaUnidade extends Carta {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int poder; 	
	private final TipoUnidade tipo;
	
	public CartaUnidade(String nome, ImageIcon img, Habilidade hab, int poder, 
			TipoUnidade tipo){
		super(nome,img,hab);
		this.poder = poder; 
		this.tipo = tipo;
	}
	
	public CartaUnidade(String nome, Habilidade hab, int poder, TipoUnidade tipo){
		super(nome,hab);
		this.poder = poder;
		this.tipo = tipo;
	}
	
	@Override
	public void ativarHabilidade(){
		if(this.habilidade == null){
			return;
		}		
		this.habilidade.ativarHabilidade();
	}
	
	public int getPoder() {
		return poder;
	}

	public void setPoder(int poder) {
		this.poder = poder;
	}
	
	@Override
	public Habilidade getHabilidade() {
		return this.habilidade;
	}
	
	public TipoUnidade getTipo(){
		return this.tipo;
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
