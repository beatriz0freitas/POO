package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import exceptions.ColecaoNaoExisteNaBibliotecaException;
import exceptions.MusicaJaExisteException;
import exceptions.MusicaNaoExisteException;
import model.musica.Musica;
import model.playlists.Playlist;
import model.playlists.PlaylistListaDeFavoritos;

/**
 * Classe que representa uma biblioteca de músicas, composta por álbuns e playlists.
 * 
 * Esta classe permite adicionar, remover e gerir coleções de músicas, além de
 * manter uma lista de músicas favoritas.
 */
public class Biblioteca implements Serializable {

    private Map <String, List<ColecaoDeMusicasReproduzivel>> biblioteca;
    private PlaylistListaDeFavoritos musicasFavoritas;

    /**
     * Construtor padrão que inicializa a biblioteca com listas vazias de álbuns e playlists.
     */
    public Biblioteca(){
        this.biblioteca = new HashMap<>();
        this.biblioteca.put("playlist", new ArrayList<>());
        this.biblioteca.put("album", new ArrayList<>());
        this.musicasFavoritas = new PlaylistListaDeFavoritos("Favoritos", new ArrayList<>());
    }

    /**
     *Construtor de cópia que cria uma nova biblioteca a partir de uma biblioteca existente.
     */
    public Biblioteca(Biblioteca biblioteca){
        this.biblioteca = biblioteca.getBiblioteca();
        this.musicasFavoritas = biblioteca.getPlaylistDeFavoritos();
    }
/**
 * Método que retorna a biblioteca de músicas.
 * 
 * @return Mapa contendo listas de álbuns e playlists.
 */
    public Map<String, List<ColecaoDeMusicasReproduzivel>> getBiblioteca() {
    return new HashMap<>(this.biblioteca);
}

    /**
     * Método que define a biblioteca de músicas.
     * 
     * @param biblioteca Mapa contendo listas de álbuns e playlists.
     */
    public void setBiblioteca(Map<String, List<ColecaoDeMusicasReproduzivel>> biblioteca) {
        this.biblioteca = new HashMap<>();
        biblioteca.forEach((key, value) -> this.biblioteca.put(key, new ArrayList<>(value)));
    }

    /**
     * Método que retorna uma coleção de músicas a partir do nome da coleção.
     * 
     * @param nomeDaColecao Nome da coleção de músicas.
     * @return Coleção de músicas correspondente ao nome fornecido.
     * @throws ColecaoNaoExisteNaBibliotecaException Se a coleção não existir na biblioteca.
     */
    //TODO: rever este throw
    public ColecaoDeMusicasReproduzivel getColecao(String nomeDaColecao) throws ColecaoNaoExisteNaBibliotecaException {
        for (List<ColecaoDeMusicasReproduzivel> colecoes : biblioteca.values()) {
            for (ColecaoDeMusicasReproduzivel colecao : colecoes) {
                if (colecao.getNome().equals(nomeDaColecao)) {
                    return colecao.clone();
                }
            }
        }
        throw new ColecaoNaoExisteNaBibliotecaException("Coleção com o nome '" + nomeDaColecao + "' não encontrada na biblioteca.");
    }

    /**
     * Método que adiciona uma playlist à biblioteca.
     * 
     * @param nomePlaylist Playlist a ser adicionada.
     */
    public void adicionarPlaylistABiblioteca(Playlist nomePlayList) {
        this.biblioteca.get("playlist").add(nomePlayList.clone());
    }

    /**
     * Método que adiciona um álbum à biblioteca.
     * 
     * @param album Álbum a ser adicionado.
     */
    public void adicionarAlbumABiblioteca(Album album) {
        this.biblioteca.get("album").add(album.clone());
    }

    /**
     * Método que remove uma playlist da biblioteca.
     * 
     * @param albumNome Nome da playlist a ser removida.
     */
    public void removerAlbum(String albumNome) {
        List<ColecaoDeMusicasReproduzivel> albuns = this.biblioteca.get("album");
        if (albuns != null) {
            albuns.removeIf(colecao -> colecao.getNome().equals(albumNome));
        }
    }

    /**
     * Método que remove uma música de um álbum específico.
     * 
     * @param albumNome Nome do álbum de onde a música será removida.
     * @param musica Música a ser removida.
     */
    public void removerMusicaDeAlbum(String albumNome, Musica musica) {
        List<ColecaoDeMusicasReproduzivel> albuns = this.biblioteca.get("album");
        if (albuns != null) {
            albuns.stream()
                .filter(colecao -> colecao.getNome().equals(albumNome) && colecao instanceof Album)
                .forEach(colecao -> {
                    Album album = (Album) colecao;
                    try {
                        album.removerMusica(musica);
                    } catch (MusicaNaoExisteException e) {
                        
                    }
                });
        }
    }

    /**
     * Método que remove uma música de todas as playlists da biblioteca.
     * 
     * @param musica Música a ser removida.
     */
    public void removerMusicaDePlaylist(Musica musica) {
        List<ColecaoDeMusicasReproduzivel> playlists = this.biblioteca.get("playlist");
        if (playlists != null) {
            playlists.stream()
                .filter(colecao -> colecao instanceof Playlist)
                .forEach(colecao -> {
                    Playlist playlist = (Playlist) colecao;
                    try {
                        playlist.removerMusica(musica);
                    } catch (MusicaNaoExisteException e) {

                    }
                });
        }
    }

    /**
     * Método que retorna a lista de álbuns da biblioteca.
     * 
     * @return Lista de álbuns.
     */     
    public List<Album> getAlbuns() {
        return this.biblioteca.getOrDefault("album", new ArrayList<>()).stream()
                              .map(colecao -> (Album) colecao.clone())
                              .collect(Collectors.toList());
    }

    /**
     * Método que retorna a lista de playlists da biblioteca.
     * 
     * @return Lista de playlists.
     */
    public List<Playlist> getPlaylists() {
        return this.biblioteca.getOrDefault("playlist", new ArrayList<>()).stream()
            .map(colecao -> (Playlist) colecao.clone())
            .collect(Collectors.toList());
    }

    /**
     * Método que clona a biblioteca.
     * 
     * @return Uma nova instância da biblioteca.
     */
    public Biblioteca clone(){
        return new Biblioteca(this);

    }

    /**
     * Método que retorna o número de playlists na biblioteca.
     * 
     * @return Número de playlists.
     */
    public int numeroDePlaylist() {
        return this.biblioteca.getOrDefault("playlist", new ArrayList<>()).size();
    }
    
    /**
     * Método que retorna todas as coleções de músicas da biblioteca, incluindo as músicas favoritas.
     * 
     * @return Lista de Playlist, albuns e lista de músicas favoritas.
     */
    public List<ColecaoDeMusicasReproduzivel> getConteudo() {
        List<ColecaoDeMusicasReproduzivel> conteudo = new ArrayList<>();
    
        for (List<ColecaoDeMusicasReproduzivel> colecoes : biblioteca.values()) {
            for (ColecaoDeMusicasReproduzivel colecao : colecoes) {
                conteudo.add(colecao.clone());
            }
        }
        if (!musicasFavoritas.estaVazia()) {
            conteudo.add(musicasFavoritas.clone());
        }
    
        return conteudo;
    }

    /**
     * Método que adiciona uma música à lista de favoritas.
     * 
     * @param musica Música a ser adicionada.
     * @throws MusicaJaExisteException Se a música já existir na lista de favoritas.
     */
    public void adicionarMusicaAosFavoritos(Musica musica) throws MusicaJaExisteException  {
        this.musicasFavoritas.adicionarMusica(musica);
    }

    /**
     * Método que remove uma música da lista de favoritas.
     * 
     * @param musica Música a ser removida.
     * @throws MusicaNaoExisteException Se a música não existir na lista de favoritas.
     */
    public void removerMusicaDosFavoritos(Musica musica) throws MusicaNaoExisteException {
        this.musicasFavoritas.removerMusica(musica);
    }
    
    /**
     * Método que verifica se uma música está na lista de favoritas.
     * 
     * @param musica Música a ser verificada.
     * @return true se a música estiver na lista de favoritas, false caso contrário.
     */
    public boolean isFavorita(Musica musica) {
        return this.musicasFavoritas.contemMusica(musica);
    }
    
    /**
     * Método que retorna a lista de músicas favoritas.
     * 
     * @return Playlist de músicas favoritas.
     */
    public PlaylistListaDeFavoritos getPlaylistDeFavoritos() {
        return new PlaylistListaDeFavoritos(this.musicasFavoritas);
    }

    /**
     * Método que testa a igualdade entre duas instâncias de Biblioteca.
     * 
     * @param o Objeto a ser comparado.
     * @return true se as instâncias forem iguais, false caso contrário.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Biblioteca that = (Biblioteca) o;
        return Objects.equals(biblioteca, that.biblioteca) &&
               Objects.equals(musicasFavoritas, that.musicasFavoritas);
    }

    /**
     * Método que retorna o hash code da biblioteca.
     * 
     * @return Hash code da biblioteca.
     */
    @Override
    public int hashCode() {
        return Objects.hash(biblioteca, musicasFavoritas);
    }

    public void removerPlaylist(String nomePlaylist) {
        List<ColecaoDeMusicasReproduzivel> playlists = this.biblioteca.get("playlist");
        if (playlists != null) {
            playlists.removeIf(c -> c instanceof Playlist && c.getNome().equals(nomePlaylist));
        }
    }
    
    

}




