package src.gwent.entidades;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Carta extends JLabel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected final String nome;
	protected final ImageIcon estampa;
	
	public Carta(String nome, ImageIcon img){
		this.estampa = img;
		this.setIcon(estampa);
		this.nome = nome; 
	}
	
	public String getNomeCarta(){
		return this.nome;
	}
	
	public ImageIcon getEstampa(){
		return this.estampa;
	}
}
