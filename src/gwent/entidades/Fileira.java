package gwent.entidades;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Group;
import javax.swing.JPanel;

public class Fileira extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final ArrayList<Carta> cartas;
	private int poderTotal;
	private boolean sobEfeitoClima;
	private static final Color corEfeitoClima = new Color(156,73,0);;
	private static final Color corNormal = Color.BLUE;
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
		if(carta instanceof CartaUnidade){
			CartaUnidade c = (CartaUnidade) carta;
			if(sobEfeitoClima){
				this.poderTotal += 1;
			}else{
				this.poderTotal += c.getPoder();
			}		
			c.ativarHabilidade();
		}
		this.glHorizontal.addComponent(carta);
		this.glVertical.addComponent(carta);
		this.fileiraLayout.setHorizontalGroup(glHorizontal);
		this.fileiraLayout.setVerticalGroup(glVertical);
		//this.exibidorPoder.alterarPoder(this.poderTotal);
		System.out.println(this.poderTotal);
	}
	
	public void sofrerEfeitoClima(){
		this.sobEfeitoClima = true;
		setPoderTotal(this.cartas.size());
		setBackground(corEfeitoClima);
	}
	
	public void anularEfeitoClima(){
		this.sobEfeitoClima = false;
		setBackground(corNormal);
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
	
	public TipoUnidade getTipo(){
		return this.tipo;
	}
}
