package gwent.entidades;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public abstract class Carta extends JLabel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected final String nome;
	protected final ImageIcon estampa;
	protected final Habilidade habilidade;
	
	public Carta(String nome, ImageIcon img, Habilidade habilidade){
		this.estampa = img;
		this.setIcon(estampa);
		this.nome = nome; 
		this.habilidade = habilidade;
	}
	
	public abstract String getNomeCarta();	
	public abstract ImageIcon getEstampa();
	public abstract Habilidade getHabilidade();
	public abstract void ativarHabilidade();
	
	@Override
	public boolean equals(Object o){
		if(!(o instanceof Carta)){
			return false;
		}
		Carta c = (Carta) o;
		return (o == this || this.getNomeCarta().equals(c.getNomeCarta()));
	}
}
