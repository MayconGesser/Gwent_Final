package gwent.util;


import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;

import gwent.entidades.Carta;
import gwent.entidades.CartaClima;
import gwent.entidades.CartaUnidade;
import gwent.entidades.Deck;
import gwent.entidades.Faccao;
import gwent.entidades.Habilidade;
import gwent.entidades.TipoCartaClima;
import gwent.entidades.TipoHabilidade;
import gwent.entidades.TipoUnidade;

public class ScriptPersistencia {
	
	private static boolean ehNumerico(String s){
		try{
			int i = Integer.parseInt(s);
		}catch(NumberFormatException e){
			return false;
		}
		return true;
	}
	
	private static Carta criarCarta(String nome, char modo, String faccao){
		
		int w = 0, h = 0; 
		
		switch(modo){
			case 'e':		//exibicao
				w = 169;
				h = 234;
				break;
				
			case 'f':		//fileira
				w = 60;
				h = 100;
				break;
				
			case 'd':		//deck
				w = 30;
				h = 70;
				break;
		}
		
		Carta carta = null;
		int poder = 0;
		TipoUnidade tipo = null;
		Habilidade habilidadeCarta = null;
		String s = (nome.substring(
					nome.indexOf('_')+1, nome.indexOf('_')+3));
		if(ehNumerico(s)){				//eh uma carta de unidade
			
			poder = Integer.parseInt(s);
			
			char tipoUnidade = 
					nome.substring(nome.indexOf('_',nome.indexOf('_')+1)+1).charAt(0);
			
			String habilidade = nome.substring(nome.lastIndexOf('_')+1,nome.length()-4);		
			
			System.out.println(nome);
			System.out.println(poder);
			System.out.println(tipoUnidade);
			System.out.println(habilidade);
			
			switch (tipoUnidade){
				case 'i':
					tipo = TipoUnidade.INFANTARIA;
					break;
					
				case 'c':
					tipo = TipoUnidade.CERCO;
					break;
					
				case 'L':
					tipo = TipoUnidade.LONGA_DISTANCIA;
					break;
			}
			
			switch(habilidade){
			
				case "sh":
					break;
	
				case "em":
					habilidadeCarta = new Habilidade(TipoHabilidade.ELEVAR_MORAL);
					break;
	
//				case "m":
//					habilidadeCarta = new Habilidade(TipoHabilidade.MEDICO);
//					break;
				
				case "ag":
					habilidadeCarta = new Habilidade(TipoHabilidade.AGRUPAR);
					break;
					
				case "es":
					habilidadeCarta = new Habilidade(TipoHabilidade.ESPIAO);
					break;
			}
			
			carta = new CartaUnidade(nome,habilidadeCarta,poder,tipo);
		}					
		
		else{		//eh uma carta de habilidade
			String habilidade = nome.substring(
					nome.indexOf('_')+1, nome.indexOf('_')+3); //mesma posicao do poder em cartas de unidade
				switch(habilidade){
				case "gm":					
					System.out.println(nome);
					System.out.println(TipoCartaClima.GEADA_MORDAZ.toString());
					carta = new CartaClima(nome,habilidadeCarta,TipoCartaClima.GEADA_MORDAZ);
					break;
					
				case "ni":
					System.out.println(nome);
					System.out.println(TipoCartaClima.NEBLINA_IMPENETRAVEL.toString());
					carta = new CartaClima(nome,habilidadeCarta,TipoCartaClima.NEBLINA_IMPENETRAVEL);
					break;
					
				case "ct":
					System.out.println(nome);
					System.out.println(TipoCartaClima.CHUVA_TORRENCIAL.toString());
					carta = new CartaClima(nome,habilidadeCarta,TipoCartaClima.CHUVA_TORRENCIAL);
					break;
			}
		}		
		
		ImageIcon img = new ImageIcon(
					new ImageIcon("BancoCartas/" + faccao + "/" + nome)
					.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH));
		
		carta.setEstampa(img);		
		return carta;
	}

	public static void persisteRN(){
		
		File[] imgs = new File("BancoCartas/ReinosNorte").listFiles();
		File f = new File("BancoCartas/ReinosNorte/cartas.bin");

		ArrayList<Carta> representacaoDeck = new ArrayList<>();
		HashMap<String,Carta> cartasExibicao = new HashMap<>();
		HashMap<String,Carta> cartasFileira = new HashMap<>();
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream(f);
			oos = new ObjectOutputStream(fos);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}
		catch(IOException e){
			e.printStackTrace();			
		}
		for(int i = 0; i<imgs.length; i++){
			String nome = imgs[i].getName();
			if(!nome.substring(nome.length()-3, nome.length()).equals("jpg")){
				System.out.println("Não entrou: " + nome);
				continue;
			}
			
			Carta carta = criarCarta(nome,'d', "ReinosNorte");
			representacaoDeck.add(carta);
			carta = criarCarta(nome,'e', "ReinosNorte");
			cartasExibicao.put(nome,carta);
			carta = criarCarta(nome,'f', "ReinosNorte");
			cartasFileira.put(nome, carta);
		}
		Deck reinosNorte = new Deck(Faccao.REINOS_DO_NORTE,representacaoDeck);
		try {
			oos.writeObject(reinosNorte);
			oos.writeObject(cartasExibicao);
			oos.writeObject(cartasFileira);
			oos.flush();
			oos.close();
			oos = null;
			System.out.println("tudo certo");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void persisteMonstro(){
		File[] imgs = new File("BancoCartas/Monstros").listFiles();
		File f = new File("BancoCartas/Monstros/cartas.bin");

		ArrayList<Carta> representacaoDeck = new ArrayList<>();
		HashMap<String,Carta> cartasExibicao = new HashMap<>();
		HashMap<String,Carta> cartasFileira = new HashMap<>();
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream(f);
			oos = new ObjectOutputStream(fos);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}
		catch(IOException e){
			e.printStackTrace();			
		}
		for(int i = 0; i<imgs.length; i++){
			String nome = imgs[i].getName();
			if(!nome.substring(nome.length()-3, nome.length()).equals("jpg")){
				System.out.println("Não entrou: " + nome);
				continue;
			}
			
			Carta carta = criarCarta(nome,'d', "Monstros");
			representacaoDeck.add(carta);
			carta = criarCarta(nome,'e', "Monstros");
			cartasExibicao.put(nome,carta);
			carta = criarCarta(nome,'f', "Monstros");
			cartasFileira.put(nome, carta);
		}
		Deck monstros = new Deck(Faccao.MONSTROS,representacaoDeck);
		try {
			oos.writeObject(monstros);
			oos.writeObject(cartasExibicao);
			oos.writeObject(cartasFileira);
			oos.flush();
			oos.close();
			oos = null;
			System.out.println("tudo certo");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
