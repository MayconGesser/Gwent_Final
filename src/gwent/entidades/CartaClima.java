package gwent.entidades;

import javax.swing.ImageIcon;

import br.ufsc.inf.leobr.cliente.Jogada;

public class CartaClima extends CartaHabilidade implements Jogada{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final TipoCartaClima tipo;
	
	public CartaClima(String nome, ImageIcon img, Habilidade habilidade,TipoCartaClima tipo) {
		super(nome, img, habilidade);
		this.tipo = tipo;
		// TODO Auto-generated constructor stub
	}
	
	public CartaClima(String nome, Habilidade habilidade, TipoCartaClima tipo){
		super(nome,habilidade); 
		this.tipo = tipo;
	}
	
	@Override
	public Habilidade getHabilidade(){
		return this.habilidade;
	}
	
	@Override
	public void ativarHabilidade(Fileira fileira){
		if(this.tipo.equals(TipoCartaClima.TEMPO_LIMPO)){
			fileira.anularEfeitoClima();
		}
		else{
			fileira.sofrerEfeitoClima();
		}		
	}
	
	public TipoCartaClima getTipo(){
		return this.tipo;
	}	
}
