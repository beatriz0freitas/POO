package  model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import exceptions.ColecaoDeMusicaVaziaException;
import exceptions.MusicaJaExisteException;
import exceptions.MusicaNaoExisteException;
import model.musica.Musica;

/**
 * Classe que representa um álbum de músicas.
 * 
 * Esta classe implementa a interface ColecaoDeMusicasReproduzivel e contém
 * métodos para gerenciar uma coleção de músicas, incluindo adição, remoção e
 * reprodução de músicas.
 */
public class Album implements ColecaoDeMusicasReproduzivel, Serializable {
    private String nome;
    private String artista;
    private List<Musica> musicas;

    /**
     * Construtor padrão que inicializa o álbum com um nome e artista vazios e uma lista vazia de músicas.
     */
    public Album () {
        this.nome = "";
        this.artista = "";
        this.musicas = new ArrayList<>();
    }   
    
    /**
     * Construtor que inicializa o álbum com um nome, artista e uma lista de músicas.
     * 
     * @param nome Nome do álbum
     * @param artista Artista do álbum
     * @param musicas Lista de músicas do álbum
     */
    public Album(String nome, String artista, List<Musica> musicas) {
        this.nome = nome;
        this.artista = artista;
        this.musicas = new ArrayList<>();
        for (Musica m : musicas) {
            this.musicas.add(m.clone());
        }
    }

    /**
     * Construtor de cópia que cria um novo álbum a partir de um álbum existente.
     * 
     * @param a Álbum a ser copiado
     */
    public Album(Album a) {
        this.nome = a.getNome();
        this.artista = a.getArtista();
        this.musicas = a.getMusicas();
    }

    /**
     * Método que retorna o nome do álbum.
     * 
     * @return Nome do álbum
     */
    public String getNome() {
        return nome;
    }

    /**
     * Método que retorna o artista do álbum.
     * 
     * @return Artista do álbum
     */
    public String getArtista() {
        return artista;
    }

    /**
     * Método que atribui um nome ao álbum.
     * 
     * @param nome Nome do álbum
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    /**
     * Método que atribui um artista ao álbum.
     * 
     * @param artista Artista do álbum
     */
    public void setArtista(String artista) {
        this.artista = artista;
    }

    /**
     * Método que retorna a lista de músicas do álbum.
     * 
     * @return Lista de músicas do álbum
     */
    public List<Musica> getMusicas() {
        List<Musica> musicasClone = new ArrayList<>();
        for (Musica m : this.musicas) {
            musicasClone.add(m.clone());
        }
        return musicasClone;
    }

    /**
     * Método que adiciona uma música ao álbum. 
     * 
     * @param m Música a ser adicionada
     * @throws MusicaJaExisteException Se a música já existir no álbum
     */
    public void adicionaMusica(Musica m) throws MusicaJaExisteException {
        if (this.musicas.stream().anyMatch(existing -> existing.equals(m))){
            throw new MusicaJaExisteException("Música já existe no álbum");
        }
        this.musicas.add(m.clone());
    }

    /**
     * Método que remove uma música do álbum.
     * 
     * @param m Música a ser removida
     * @throws MusicaNaoExisteException Se a música não existir no álbum
     */
    public void removerMusica(Musica m) throws MusicaNaoExisteException{
        if (!this.musicas.removeIf(existing -> existing.equals(m))){
            throw new MusicaNaoExisteException("A música não existe no Album");
        }
    } 

    /**
     * Método que retorna uma Música do álbum pelo nome.
     * 
     * @param nome Nome da música a ser retornada
     * @return Música correspondente ao nome fornecido ou null se não existir
     */
    public Musica getMusica(String nome) {
       return musicas.stream()
            .filter(m -> m.getNome().equals(nome))
            .findFirst()
            .orElse(null);
    }

    /**
     * Método que retorna uma Música do álbum pelo índice.
     * 
     * @param indice Índice da música a ser retornada
     * @return Música correspondente ao índice fornecido
     * @throws ColecaoDeMusicaVaziaException Se o álbum estiver vazio
     */
    @Override
    public Musica getMusicaPorIndice(int indice) throws ColecaoDeMusicaVaziaException{
        if (musicas.isEmpty())
            throw new ColecaoDeMusicaVaziaException("O Album está vazio.");
        return musicas.get(indice);
    }

    /**
     * Método que clona o álbum.
     * 
     * @return Uma nova instância do álbum
     */
    public Album clone(){
        return new Album(this);
    }

    /**
     * Método que retorna a música mais reproduzida do álbum.
     * 
     * @return Música mais reproduzida ou null se o álbum estiver vazio
     */
    public Musica musicaMaisReproduzida() {
        if (this.musicas.isEmpty()) {
            return null;
        }
        return this.musicas.stream()
            .max((m1, m2) -> Integer.compare(m1.getNumeroDeReproducoes(), m2.getNumeroDeReproducoes()))
            .get().clone();
    }

    /**
     * Método que devolve o tamanho do álbum.
     * 
     * @return Tamanho do álbum
     */
    @Override
    public int getTamanho() {
        return musicas.size();
    }

    /**
     * Método que retorna as musicas originais do álbum.
     * 
     * @return Lista de músicas originais do álbum
     */
    @Override
    public List<Musica> getMusicasOriginais() {
        return new ArrayList<>(musicas);
    }

    /**
     * Método que retorna o tipo de coleção do álbum.
     * 
     * @return Tipo de coleção do álbum
     */
    @Override
    public String getTipoDeColecao() {
        return this.getClass().getSimpleName();
    }

    /**
     * Incrementa o número de reproduções da música correspondente no álbum usando stream.
     *
     * @param musica Música cuja reprodução deve ser incrementada
     */
    public void incrementaReproducaoSeExistir(Musica musica) {
        this.musicas.stream()
            .filter(m -> m.equals(musica))
            .findFirst()
            .ifPresent(Musica::incrementaNumeroDeReproducoes);
    }

    /**
     * Método que testa a igualdade entre dois álbuns.
     * 
     * @param o Objeto a ser comparado
     * @return true se os álbuns forem iguais, false caso contrário
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Album album = (Album) o;
        return nome.equals(album.nome) &&
               artista.equals(album.artista) &&
               musicas.equals(album.musicas);
    }

    /**
     * Método que retorna o hash code do álbum.
     * 
     * @return Hash code do álbum
     */
    @Override
    public int hashCode() {
        return Objects.hash(nome, artista, musicas);
    }

    /**
     * Remove uma música da coleção pelo nome.
     *
     * @param nomeMusica Nome da música a ser removida
     * @throws MusicaNaoExisteException Se a música não existir na coleção
     */
    public void removerMusicaDaColecao(String nomeMusica) throws MusicaNaoExisteException {
        boolean removed = this.musicas.removeIf(musica -> musica.getNome().equals(nomeMusica));
        if (!removed) {
            throw new MusicaNaoExisteException("A música com o nome '" + nomeMusica + "' não existe na coleção de músicas.");
        }
    }

}

