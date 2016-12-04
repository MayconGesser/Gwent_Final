package gwent.controladores;

import java.io.Serializable;
import java.util.HashMap;

//import br.ufsc.inf.leobr.cliente.Jogada;
import gwent.entidades.*;

public class ControladorMesa implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected HashMap<TipoUnidade,Fileira> fileiras;
	protected Jogador jogadorAtual;
	private Deck cemiterio;

	public ControladorMesa(Fileira fileiraInfantaria,
			Fileira fileiraLongaDistancia,
			Fileira fileiraCerco){

		this.fileiras = new HashMap<>();
		this.fileiras.put(TipoUnidade.INFANTARIA, fileiraInfantaria);
		this.fileiras.put(TipoUnidade.LONGA_DISTANCIA, fileiraLongaDistancia);
		this.fileiras.put(TipoUnidade.CERCO, fileiraCerco);
	}

	public boolean processarCarta(Carta carta){
		boolean precisaSelecionar = false;
		if(carta instanceof CartaUnidade){
			CartaUnidade c = (CartaUnidade) carta;
			TipoUnidade t = c.getTipo();
			Fileira f = this.fileiras.get(t);	
			Habilidade habilidadeCarta = c.getHabilidade();
			if(habilidadeCarta != null){
				TipoHabilidade tipoHabilidadeCarta = habilidadeCarta.getTipoHabilidade();
				switch(tipoHabilidadeCarta){
				case MEDICO:
					habilidadeCarta.setChamador(this);
					habilidadeCarta.setReferencia(this.cemiterio);
					break;
				case AGILIDADE:
					precisaSelecionar = true;
					return precisaSelecionar;
				case AGRUPAR:
					break;
				case CORNETA_COMANDANTE:
					break;
				case ELEVAR_MORAL:
					break;
				case EPIDEMIA:
					break;
				case ESPIAO:
					break;
				case INCINERAR:
					break;
				case ISCA:
					break;
				case LACOS_FORTES:
					break;
				default:
					break;
				}
			}
						
		}
		return precisaSelecionar;
	}

	public void exibeMensagem(String mensagem) {

	}

	//    public void receberJogada(Jogada jogada) {
	//
	//    }

	public Jogador getJogadorAtual() {
		return jogadorAtual;
	}

	public void setJogadorAtual(Jogador jogadorAtual) {
		this.jogadorAtual = jogadorAtual;
	}

	public Deck getCemiterio() {
		return cemiterio;
	}

	public void setCemiterio(Deck cemiterio) {
		this.cemiterio = cemiterio;
	}
}
