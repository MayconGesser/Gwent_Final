package gwent.entidades;

import javax.swing.ImageIcon;

import br.ufsc.inf.leobr.cliente.Jogada;

public class CartaClima extends CartaHabilidade implements Jogada{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private TipoUnidade tipoFileiraAtingida;	//aponta para a fileira; mudanca devido a problemas de serializacao
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
		fileira.sofrerEfeitoClima();
	}
	
	public TipoCartaClima getTipo(){
		return this.tipo;
	}	
	
	public TipoUnidade getTipoFileiraAtingida(){
		return this.tipoFileiraAtingida;
	}
}
