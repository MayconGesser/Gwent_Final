package src.gwent.entidades;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public abstract class Carta extends JLabel {

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
	
	public abstract String getNomeCarta();	
	public abstract ImageIcon getEstampa();
}
