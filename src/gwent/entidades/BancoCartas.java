package gwent.entidades;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;

public class BancoCartas {

    public static Map<String, Object> resgatarDeck(Faccao faccao) {
        File bin = new File(faccao.getUri());
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        Map<String, Object> deck = new HashMap<>();
        try {
            fis = new FileInputStream(bin);
            ois = new ObjectInputStream(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            deck.put("deck", (Deck) (ois != null ? ois.readObject() : null));
            deck.put("exibicao", (Map<String, Carta>) (ois != null ? ois.readObject() : null));
            deck.put("fileiras", (Map<String, Carta>) (ois != null ? ois.readObject() : null));
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return deck;
    }
}
