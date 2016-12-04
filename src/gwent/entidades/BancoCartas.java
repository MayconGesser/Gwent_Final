package gwent.entidades;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

public class BancoCartas {

    public static Deck resgatarCartas(Faccao faccao) {
        File bin = new File(faccao.getUri());
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        Deck deck = null;
        try {
            fis = new FileInputStream(bin);
            ois = new ObjectInputStream(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            deck = (Deck) (ois != null ? ois.readObject() : null);
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        return deck;
    }
}
