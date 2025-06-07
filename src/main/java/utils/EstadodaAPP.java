package utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class EstadodaAPP {

    public static <T> T carregarObjeto(String caminho, T valorPorDefeito) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(caminho))) {
            return (T) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Aviso: não foi possível carregar " + caminho + ". A usar valor por defeito.");
            return valorPorDefeito;
        }
    }

    public static void guardarObjeto(String caminho, Object objeto) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(caminho))) {
            oos.writeObject(objeto);
        } catch (IOException e) {
            System.err.println("Erro ao guardar objeto: " + e.getMessage());
        }
    }
}
