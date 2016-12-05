package gwent.entidades;
import br.ufsc.inf.leobr.cliente.Jogada;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public abstract class Carta extends JLabel implements Jogada {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected final String nome;
	protected ImageIcon estampa;
	protected final Habilidade habilidade;
	
	public Carta(String nome, ImageIcon img, Habilidade habilidade){
		this.estampa = img;
		this.setIcon(estampa);
		this.nome = nome; 
		this.habilidade = habilidade;
	}
	
	public Carta(String nome, Habilidade habilidade){
		this.nome = nome;
		this.habilidade = habilidade;
	}
	
	public abstract String getNomeCarta();	
	public abstract ImageIcon getEstampa();
	public abstract void setEstampa(ImageIcon estampa);
	public abstract Habilidade getHabilidade();
	
	@Override
	public boolean equals(Object o){
		if(!(o instanceof Carta)){
			return false;
		}
		Carta c = (Carta) o;
		return (o == this || this.getNomeCarta().equals(c.getNomeCarta()));
	}
	
	@Override
	public String toString(){
		String detalhesCarta = "";
		detalhesCarta += "\nNome da Carta : " + this.nome;
		detalhesCarta += "\nHabilidade da Carta : " + (this.habilidade != null ? this.habilidade.toString() : "");
		if(this instanceof CartaUnidade){
			CartaUnidade u = (CartaUnidade) this;
			detalhesCarta += "\nPoder da carta : " + u.getPoder();
			detalhesCarta += "\nTipo de unidade : " + u.getTipo();
		}
		else if(this instanceof CartaClima){
			CartaClima cc = (CartaClima) this;
			detalhesCarta += "\nTipo carta clima : " + cc.getTipo();
		}
		return detalhesCarta;
	}
}
