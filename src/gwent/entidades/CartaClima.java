package gwent.entidades;

import javax.swing.ImageIcon;

public class CartaClima extends CartaHabilidade {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Fileira fileiraAtingida;
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
	public void ativarHabilidade(){
		fileiraAtingida.sofrerEfeitoClima();
	}
	
	public TipoCartaClima getTipo(){
		return this.tipo;
	}
	
	public void setFileiraAtingida(Fileira fileira){
		this.fileiraAtingida = fileira;
	}
}
