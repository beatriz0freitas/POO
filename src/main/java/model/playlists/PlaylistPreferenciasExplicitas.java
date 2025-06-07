package model.playlists;

import java.util.ArrayList;
import java.util.List;
import model.musica.Musica;
import model.musica.MusicaExplicitaInterface; 

/**
 * Classe que representa uma playlist gerada a partir da lista de músicas favoritas do utilizador,
 * filtrando apenas as músicas explícitas.
 * 
 * Esta classe estende a classe PlaylistListaDeFavoritos e implementa métodos específicos para
 * filtrar músicas explícitas.
 */
public class PlaylistPreferenciasExplicitas extends PlaylistListaDeFavoritos {

    /**
     * Construtor vazio que inicializa a playlist com um nome padrão e uma lista vazia de músicas.
     */
    public PlaylistPreferenciasExplicitas() {
        super();
    }

    /**
     * Construtor que inicializa a playlist com um nome e uma lista de músicas favoritas explicitas.
     * 
     * @param favoritas Playlist de músicas favoritas.
     */
    public PlaylistPreferenciasExplicitas(PlaylistListaDeFavoritos favoritas) {
        super(favoritas.getNome(), getMusicasExplicitas(favoritas));
    }

    /**
     * Método que filtra as músicas explícitas da lista de favoritas.
     * 
     * @param favoritas Playlist de músicas favoritas.
     * @return Lista de músicas favoritas e explicitas
     */
     private static List<Musica> getMusicasExplicitas(PlaylistListaDeFavoritos favoritas) {
        List<Musica> explicitas = new ArrayList<>();
        // favoritas.getMusicas() já retorna clones, então clonamos novamente para a nova lista
        for (Musica m : favoritas.getMusicas()) {
            if (m instanceof MusicaExplicitaInterface) {
                explicitas.add(m.clone());
            }
        }
        return explicitas;
    }

    /**
     * Construtor de cópia que cria uma nova playlist a partir de uma existente.
     * 
     * @param original Playlist a ser copiada.
     */
    public PlaylistPreferenciasExplicitas(PlaylistPreferenciasExplicitas original) {
        super(original); // Chama o construtor de cópia de PlaylistListaDeFavoritos
    }

    /**
     * Método que clona a playlist de preferências explícitas.
     * @return Uma nova instância da playlist de preferências explícitas.
     */
    @Override
    public Playlist clone() {
        return new PlaylistPreferenciasExplicitas(this);
    }

    /**
     * Método que retorna uma representação em string da playlist de preferências explícitas.
     * 
     * @return String representando a playlist de preferências explícitas.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Playlist favoritas explicitas:\n");
        // Chama o toString() de PlaylistListaDeFavoritos
        sb.append(super.toString());
        return sb.toString();
    }
}