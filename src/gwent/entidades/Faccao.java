package gwent.entidades;


import br.ufsc.inf.leobr.cliente.Jogada;

public enum Faccao implements Jogada {
	REINOS_DO_NORTE("Reinos do Norte", "BancoCartas/ReinosNorte/cartas.bin"),
	IMPERIO_DE_NILFGAARD("", ""),
	SCOIATAEL("", ""),
	MONSTROS("", "");

	private String nome;
	private String uri;

	Faccao(String nome, String uri) {
		this.nome = nome;
		this.uri = uri;
	}

	public String getNome() {
		return nome;
	}

	public String getUri() {
		return uri;
	}
}
