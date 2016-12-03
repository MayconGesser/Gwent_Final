package gwent.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Deck implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final ArrayList<Carta> cartas;
	private final Faccao faccao;
	private Random seletor; 
	
	
	public Deck(Faccao faccao, ArrayList<Carta> cartas){
		this.faccao = faccao;
		this.cartas = cartas;
		this.seletor = new Random();
	}
	
	public void addCarta(Carta carta){
		this.cartas.add(carta);
	}
	
	public Carta sacarCarta(int i){		
		Carta carta = this.cartas.get(i);
		this.cartas.remove(carta);
		return carta;
	}
	
	public ArrayList<Carta> getCartas(){
		return this.cartas;
	}
}
