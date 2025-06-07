package model.playlists;

import java.util.List;
import model.musica.Musica;

/**
 * Classe que representa uma playlist personalizada.
 * 
 * Esta classe estende a classe Playlist e fornece métodos específicos para manipular
 * e obter informações sobre a playlist personalizada.
 */
public class PlaylistPersonalizada extends Playlist {

    /**
     * Construtor vazio que inicializa a playlist personalizada com um nome vazio e uma lista vazia de músicas.
     */
    public PlaylistPersonalizada() {
        super();
    }

    /**
     * Construtor que inicializa a playlist personalizada com um nome e uma lista de músicas.
     * 
     * @param nome Nome da playlist.
     * @param musicas Lista de músicas a serem adicionadas à playlist.
     */
    public PlaylistPersonalizada(String nome, List<Musica> musicas) {
        super(nome, musicas);
    }

    /**
     * Construtor de cópia que cria uma nova playlist personalizada a partir de uma existente.
     * 
     * @param p Playlist a ser copiada.
     */
    public PlaylistPersonalizada(PlaylistPersonalizada p) {
        super(p);
    }

    /**
     * Método que retorna uma representação em string da playlist personalizada.
     * 
     * @return String representando a playlist personalizada.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Playlist Personalizada:\n");
        sb.append(super.toString());
        return sb.toString();
    }
    
    /**
     * Método que clona a playlist personalizada.
     * 
     * @return Uma nova instância da playlist personalizada.
     */
    @Override
    public Playlist clone() {
        return new PlaylistPersonalizada(this);
    }
}