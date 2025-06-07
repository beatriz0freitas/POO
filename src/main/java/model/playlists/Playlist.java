/**
 * Pacote que contém as diferentes implementações de playlists.
 */
package model.playlists;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import exceptions.ColecaoDeMusicaVaziaException;
import exceptions.MusicaJaExisteException;
import exceptions.MusicaNaoExisteException;
import model.ColecaoDeMusicasReproduzivel;
import model.musica.Musica;

/**
 * Classe abstrata que representa uma playlist de músicas.
 * 
 * Esta classe implementa a interface ColecaoDeMusicasReproduzivel e fornece métodos
 * para manipular e obter informações sobre a playlist.
 */
public abstract class Playlist implements ColecaoDeMusicasReproduzivel, Serializable {

    private String nome;
    private final List<Musica> musicas;

    /**
     * Construtor padrão que inicializa a playlist com um nome vazio e uma lista vazia de músicas.
     */
    public Playlist() {
        this.nome = "";
        this.musicas = new ArrayList<>();
    }

    /**
     * Construtor que inicializa a playlist com um nome e uma lista de músicas.
     * 
     * @param nome Nome da playlist.
     * @param musicas Lista de músicas a serem adicionadas à playlist.
     */
    public Playlist(String nome, List<Musica> musicas) {
        this.nome = nome;
        this.musicas = new ArrayList<>(musicas);
    }

    /**
     * Construtor de cópia que cria uma nova playlist a partir de uma existente.
     * 
     * @param p Playlist a ser copiada.
     */
    public Playlist(Playlist p) {
        this.nome = p.getNome();
        this.musicas = new ArrayList<>(p.getMusicas()); // Utiliza getMusicas para obter clones das músicas
    }

    /**
     * Método que retorna o nome da playlist.
     * 
     * @return Nome da playlist.
     */
    public String getNome(){
        return this.nome;
    }

    /**
     * Método que define o nome da playlist.
     * 
     * @param nome Nome a ser definido para a playlist.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Método que retorna a lista original de músicas da playlist.
     * 
     * @return Lista de músicas.
     */
    public List<Musica> getMusicasOriginais() {
        return new ArrayList<>(musicas);
    }

    /**
     * Método que retorna uma cópia da lista de músicas da playlist.
     * 
     * @return Lista de músicas clonadas.
     */
    public List<Musica> getMusicas() {
        List<Musica> musicasClone = new ArrayList<>();
        for (Musica m : this.musicas) {
            musicasClone.add(m.clone());
        }
        return musicasClone;
    }

    public void adicionarMusica(Musica m) throws MusicaJaExisteException {
        if (this.contemMusica(m)) {
            throw new MusicaJaExisteException ("Não foi possível adicionar música");
        }
        musicas.add(m);
    }

     public void removerMusica(Musica m) throws MusicaNaoExisteException{
        if (!this.musicas.removeIf(existing -> existing.equals(m))){
            throw new MusicaNaoExisteException("Não foi possível remover música");
        }
    }

    /**
     * Método que verifica se a playlist está vazia.
     * 
     * @return true se a playlist estiver vazia, false caso contrário.
     */
    public boolean estaVazia() {
        return musicas.isEmpty();
    }

    /**
     * Método que retorna a duração total da playlist em segundos.
     * 
     * @return Duração total da playlist em segundos.
     */
    public int duracaoTotal() {
        return musicas.stream().mapToInt(Musica::getDuracaoEmSegundos).sum();
    }
    
    /**
     * Método que retorna o número de músicas na playlist.
     * 
     * @return Número de músicas na playlist.
     */
    @Override
    public int getTamanho() {
        return musicas.size();
    }

    /**
     * Método que retorna a música na posição especificada da playlist.
     * 
     * @param indiceAtual Índice da música a ser retornada.
     * @return Música na posição especificada.
     * @throws ColecaoDeMusicaVaziaException Se a playlist estiver vazia.
     */
    @Override
    public Musica getMusicaPorIndice(int indiceAtual) throws ColecaoDeMusicaVaziaException {
        if (musicas.isEmpty())
            throw new ColecaoDeMusicaVaziaException("A playlist está vazia.");
        return musicas.get(indiceAtual); // Retorna a referência original, pois a reprodução não deve alterar a música
    }

    /**
     * Método que retorna o tipo de coleção da playlist.
     * 
     * @return Tipo de coleção da playlist.
     */
    @Override
    public String getTipoDeColecao() {
        return this.getClass().getSimpleName();
    }

    /**
     * Método que retorna uma representação em string da playlist.
     * 
     * @return Representação em string da playlist.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nome da Playlist: ").append(this.nome).append("\n");
        sb.append("Duração Total: ").append(this.duracaoTotal()).append(" segundos\n");
        sb.append("Total de Músicas: ").append(this.getTamanho()).append("\n");
        sb.append("Músicas:").append("\n");
        for (int i = 0; i < musicas.size(); i++) {
            sb.append("INDICE:").append(i + 1).append(". ").append(musicas.get(i).getNome()).append("\n");
        }
        return sb.toString();
    }

    /**
     * Método que verifica se a playlist já contém uma música específica.
     * 
     * @param musica Música a ser verificada.
     * @return true se a música estiver na playlist, false caso contrário.
     */
    public boolean contemMusica(Musica musica) {
        return musicas.contains(musica);
    }

    /**
     * Método que clona a playlist.
     * 
     * @return Uma nova instância da playlist.
     */
    public abstract Playlist clone();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Playlist playlist = (Playlist) o;
        return Objects.equals(nome, playlist.nome) &&
               Objects.equals(musicas, playlist.musicas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, musicas);
    }

    public int compareTo(Playlist outra) {
        return this.nome.compareTo(outra.nome);
    }
}
