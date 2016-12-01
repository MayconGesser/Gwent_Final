package entidades;
import java.util.ArrayList;

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
	private final TipoUnidade tipo;
	private final ExibidorPoderFileira exibidorPoder;
	private Group glHorizontal;
	private Group glVertical;
	private GroupLayout fileiraLayout;
	
	
	public Fileira(TipoUnidade tipo, ExibidorPoderFileira exibidorPoder){
		this.tipo = tipo;
		this.exibidorPoder = exibidorPoder;
		this.cartas = new ArrayList<>();
		this.fileiraLayout = new GroupLayout(this);
		this.setLayout(fileiraLayout);
		this.glHorizontal = fileiraLayout.createSequentialGroup();
		this.glVertical = fileiraLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING
				);
		this.fileiraLayout.setHorizontalGroup(glHorizontal);
		this.fileiraLayout.setVerticalGroup(glVertical);
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
		}
		this.glHorizontal.addComponent(carta);
		this.glVertical.addComponent(carta);
		this.fileiraLayout.setHorizontalGroup(glHorizontal);
		this.fileiraLayout.setVerticalGroup(glVertical);
		//this.exibidorPoder.alterarPoder(this.poderTotal);
		System.out.println(this.cartas.size());
	}
	
	public void sofrerEfeitoClima(){
		this.sobEfeitoClima = true;
		setPoderTotal(this.cartas.size());
	}
	
	public void anularEfeitoClima(){
		this.sobEfeitoClima = false;
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
