package gwent.entidades;
import br.ufsc.inf.leobr.cliente.Jogada;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Group;
import javax.swing.JPanel;

public class Fileira extends JPanel implements Jogada {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final ArrayList<Carta> cartas;
	private int poderTotal;
	private boolean sobEfeitoClima;
	private Carta ultimaCartaInclusa;
	private static final Color corNormal = new Color(156,73,0);
	private final TipoUnidade tipo;
	//private final ExibidorPoderFileira exibidorPoder;	
	private Group glHorizontal;
	private Group glVertical;
	private GroupLayout fileiraLayout;
	
	
	public Fileira(TipoUnidade tipo, ExibidorPoderFileira exibidorPoder){
		this.tipo = tipo;		
		//this.exibidorPoder = exibidorPoder;
		this.cartas = new ArrayList<>();
		this.fileiraLayout = new GroupLayout(this);
		this.setLayout(fileiraLayout);
		this.glHorizontal = fileiraLayout.createSequentialGroup();
		this.glVertical = fileiraLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING
				);
		this.fileiraLayout.setHorizontalGroup(glHorizontal);
		this.fileiraLayout.setVerticalGroup(glVertical);
		setBorder(BorderFactory.createEmptyBorder(3,3,3,3));
	}
	
	public void incluirCarta(Carta carta){
		this.cartas.add(carta);
		this.ultimaCartaInclusa = carta;
		if(carta instanceof CartaUnidade){
			CartaUnidade c = (CartaUnidade) carta;
			if(sobEfeitoClima){
				this.poderTotal += 1;
			}else{
				this.poderTotal += c.getPoder();
			}
			if(c.getHabilidade() != null && c.getHabilidade().getTipoHabilidade().equals(TipoHabilidade.ELEVAR_MORAL)){
				c.getHabilidade().setReferenciaFileira(this);
			}
			c.ativarHabilidade();
		}
		this.glHorizontal.addComponent(carta);
		this.glVertical.addComponent(carta);
		this.fileiraLayout.setHorizontalGroup(glHorizontal);
		this.fileiraLayout.setVerticalGroup(glVertical);
		//this.exibidorPoder.alterarPoder(this.poderTotal);
		System.out.println("Poder da fileira " + this.getTipo().toString() + ": " + this.poderTotal);
		revalidate();
		repaint();
	}
	
	public void sofrerEfeitoClima(){
		this.sobEfeitoClima = true;
		this.poderTotal = this.cartas.size();
		Color corEfeitoClima = null;
		if(this.tipo.equals(TipoUnidade.INFANTARIA)){
			corEfeitoClima = Color.BLUE;		//afetada por geada mordaz
		}
		else if(this.tipo.equals(TipoUnidade.LONGA_DISTANCIA)){		//afetada por neblina impenetravel
			corEfeitoClima = Color.WHITE;
		}
		else if(this.tipo.equals(TipoUnidade.CERCO)){		//afetada por chuva torrencial
			corEfeitoClima = Color.GRAY;
		}
		setBackground(corEfeitoClima);
		revalidate();
		repaint();
		System.out.println("Fileira " + this.tipo.toString() + " está sob efeito de clima");
		System.out.println("Novo poder total: " + this.poderTotal);
	}
	
	public void anularEfeitoClima(){
		this.sobEfeitoClima = false;
		setBackground(corNormal);
		revalidate();
		repaint();		
		System.out.println("Fileira " + this.tipo.toString() + "não está sob efeito de clima");
		atualizaPoderTotal();
	}
	
	public ArrayList<Carta> getCartas(){
		return this.cartas;
	}
	
	public int getPoderTotal(){
		return this.poderTotal;
	}
	
	public void setPoderTotal(int poderTotal){
		this.poderTotal = poderTotal;
	}
	
	public void atualizaPoderTotal(){
		int novoPoderTotal = 0;
		if(this.sobEfeitoClima){
			novoPoderTotal = this.cartas.size();
		}
		else{
			for(Carta c : this.cartas){
				if(c instanceof CartaUnidade){
					CartaUnidade unidade = (CartaUnidade) c;
					novoPoderTotal += unidade.getPoder();
				}
			}
		}		
		this.poderTotal = novoPoderTotal;
		System.out.println("Novo poder total da fileira " + this.tipo.toString() + ": " + this.poderTotal);
	}
	
	public TipoUnidade getTipo(){
		return this.tipo;
	}

	public Carta getUltimaCartaInclusa() {
		return ultimaCartaInclusa;
	}

	public void setUltimaCartaInclusa(Carta ultimaCartaInclusa) {
		this.ultimaCartaInclusa = ultimaCartaInclusa;
	}
}
