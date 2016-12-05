package gwent.entidades;

import javax.swing.ImageIcon;

import br.ufsc.inf.leobr.cliente.Jogada;

public class CartaHabilidade extends Carta implements Jogada{
	
	
	
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
	
	public void ativarHabilidade(Fileira fileira){
		//
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
