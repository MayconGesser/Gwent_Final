package gwent.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Deck implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final ArrayList<Carta> cartas;
	private final Faccao faccao;
	
	
	public Deck(Faccao faccao, ArrayList<Carta> cartas){
		this.faccao = faccao;
		this.cartas = cartas;		
	}
	
	//construtor usado pelo cemiterio
	public Deck(Faccao faccao){
		this.faccao = faccao;
		this.cartas = new ArrayList<>();		
	}
	
	public void embaralhar(){
		Collections.shuffle(this.cartas);
	}
	
	public void addCarta(Carta carta){
		this.cartas.add(carta);
	}
	
	public Carta sacarCarta(){		
		Carta carta = this.cartas.get(0);
		this.cartas.remove(carta);
		return carta;
	}
	
	public ArrayList<Carta> getCartas(){
		return this.cartas;
	}
	
	public Faccao getFaccao(){
		return this.faccao;
	}
}
