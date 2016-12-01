import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;

import entidades.Carta;
import entidades.CartaUnidade;
import entidades.TipoUnidade;

public class blah {
	
	private static Carta criarCarta(String nome, char modo){
		
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
		
		int poder = Integer.parseInt((nome.substring(
				nome.indexOf('_')+1, nome.indexOf('_')+3)));
		
		char tipoUnidade = 
				nome.substring(nome.lastIndexOf('_')+1).charAt(0);
		
		System.out.println(nome);
		System.out.println(poder);
		System.out.println(tipoUnidade);
		
		TipoUnidade tipo = null;
		
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
		
		ImageIcon img = new ImageIcon(
					new ImageIcon("/home/viwjcq/Desktop/BancoCartas/ReinosNorte/" + nome)
					.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH));
		
		CartaUnidade carta = new CartaUnidade(nome,img,poder,null,tipo);
		return carta;
	}
	
	public static void persisteRN(){
		
		File[] imgs = new File("/home/viwjcq/Desktop/BancoCartas/ReinosNorte").listFiles();
		File f = new File("/home/viwjcq/Desktop/BancoCartas/ReinosNorte/cartas.bin");
		ArrayList<Carta> deck = new ArrayList<>();
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
				System.out.println(nome);
				continue;
			}
			
			Carta carta = criarCarta(nome,'d');
			deck.add(carta);
			carta = criarCarta(nome,'e');
			cartasExibicao.put(nome,carta);
			carta = criarCarta(nome,'f');
			cartasFileira.put(nome, carta);
		}
		Deck reinosNorte = new Deck(Faccao.REINOS_DO_NORTE,deck);
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
	
}
