package model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import exceptions.ColecaoDeMusicaVaziaException;
import exceptions.ColecaoNaoExisteNaBibliotecaException;
import exceptions.MusicaJaExisteException;
import exceptions.MusicaNaoExisteException;
import model.musica.Musica;
import model.planosSubscricao.PlanoSubscricao;
import model.planosSubscricao.PlanoSubscricaoFree;
import model.playlists.Playlist;
import model.playlists.PlaylistListaDeFavoritos;

/**
 * Representa um utilizador do sistema SpotifUM.
 * Contém informações pessoais do utilizador, o seu plano de subscrição,
 * pontos, biblioteca de músicas, histórico de reproduções e o reprodutor de música.
 * Esta classe é um {@link ReproducaoObservable} (notifica sobre as reproduções que observa)
 * e também um {@link ReproducaoObserver} (observa o seu próprio reprodutor para atualizar pontos e histórico).
 */
public class Utilizador extends ReproducaoObservable implements ReproducaoObserver {
    private String nome;
    private String email;
    private String morada;
    private PlanoSubscricao plano;
    private int pontos;
    private ReprodutorDeMusica reprodutor;
    private Historico historico;
    private Biblioteca biblioteca;
    private LocalDateTime dataAdesao;

    /**
     * Construtor padrão.
     * Inicializa o utilizador com valores padrão (strings vazias para nome, email, morada),
     * data de adesão atual, plano {@link PlanoSubscricaoFree}, 0 pontos, e novas instâncias
     * de {@link Biblioteca}, {@link ReprodutorDeMusica} e {@link Historico}.
     * Regista o histórico e o próprio utilizador como observadores do reprodutor.
     */
    public Utilizador() {
        this.nome = " ";
        this.email = " "; 
        this.morada = " ";
        this.dataAdesao = LocalDateTime.now();

        this.plano = new PlanoSubscricaoFree();
        this.pontos = 0;
        this.biblioteca = new Biblioteca();
        this.reprodutor = new ReprodutorDeMusica();
        this.historico = new Historico(); 

        reprodutor.adicionaObserver(historico);
        reprodutor.adicionaObserver(this);
    }
    /**
     * Construtor que cria um utilizador com nome, morada e email especificados.
     * A data de adesão é definida para o momento atual, o plano é o {@link PlanoSubscricaoFree} por defeito,
     * os pontos são inicializados a 0, e são criadas novas instâncias para biblioteca, reprodutor e histórico.
     * Regista o histórico e o próprio utilizador como observadores do reprodutor.
     *
     * @param nome O nome do utilizador.
     * @param morada A morada do utilizador.
     * @param email O email do utilizador (usado como identificador).
     */
    public Utilizador(String nome, String morada, String email) {
        this.nome = nome;
        this.email = email;
        this.morada = morada;
        this.plano = new PlanoSubscricaoFree();
        this.pontos = 0;

        this.reprodutor = new ReprodutorDeMusica();
        this.dataAdesao = LocalDateTime.now(); 
        this.historico = new Historico();
        this.biblioteca = new Biblioteca();
        reprodutor.adicionaObserver(historico);
        reprodutor.adicionaObserver(this);
    }

    /**
     * Construtor de cópia (privado).
     * Cria uma nova instância de {@code Utilizador} como uma cópia profunda de um utilizador existente.
     * Todos os campos, incluindo objetos mutáveis como plano, biblioteca, reprodutor e histórico,
     * são clonados. Os observadores do reprodutor são reconfigurados na nova instância.
     *
     * @param novoUtilizador O objeto {@code Utilizador} a ser copiado.
     */
    private Utilizador(Utilizador novoUtilizador) {
        this.nome = novoUtilizador.getNome();
        this.email = novoUtilizador.getEmail();
        this.morada = novoUtilizador.getMorada();
        this.plano = novoUtilizador.getPlanoSubscricao(); 
        this.pontos = novoUtilizador.getPontosAtuais();
        this.biblioteca = novoUtilizador.getBiblioteca(); 
        this.reprodutor = novoUtilizador.getReprodutor();
        this.historico = novoUtilizador.getHistorico();
        this.dataAdesao = novoUtilizador.getDataAdesao();
            this.reprodutor.adicionaObserver(this.historico);
            this.reprodutor.adicionaObserver(this);
        
    }

    /**
     * Retorna o nome do utilizador.
     * @return O nome do utilizador.
     */
    public String getNome() {
        return this.nome;
    }

    /**
     * Retorna o email do utilizador.
     * @return O email do utilizador.
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Retorna a morada do utilizador.
     * @return A morada do utilizador.
     */
    public String getMorada() {
        return this.morada;
    }

    /**
     * Retorna uma cópia do plano de subscrição atual do utilizador.
     * @return Um clone do {@link PlanoSubscricao} do utilizador.
     */
    public PlanoSubscricao getPlanoSubscricao() {
        return this.plano.clone();
    }

    /**
     * Retorna a quantidade atual de pontos do utilizador.
     * @return Os pontos do utilizador.
     */
    public int getPontosAtuais() {
        return this.pontos;
    }

    /**
     * Retorna uma cópia da biblioteca de músicas do utilizador.
     * @return Um clone da {@link Biblioteca} do utilizador.
     */
    public Biblioteca getBiblioteca() {
        return this.biblioteca.clone();
    }

    /**
     * Retorna o reprodutor de música do utilizador. 
     * Este método é privado para controlar o acesso direto ao reprodutor.
     * Para clonagem, um método de acesso que clona seria mais seguro ou um construtor de cópia que lide com isso.
     * @return O {@link ReprodutorDeMusica} do utilizador.
     */
    private ReprodutorDeMusica getReprodutor() { 
        return this.reprodutor; 
    }

    /**
     * Retorna uma cópia do histórico de reproduções do utilizador.
     * @return Um clone do {@link Historico} do utilizador.
     */
    public Historico getHistorico() {
        return historico.clone();
    }

    /**
     * Retorna a data de adesão do utilizador. 
     * LocalDateTime é imutável, pelo que retornar a referência direta é seguro.
     * @return A {@link LocalDateTime} da adesão do utilizador.
     */
    private LocalDateTime getDataAdesao() {
        return this.dataAdesao;
    }

    /**
     * Define o nome do utilizador.
     * @param nome O novo nome do utilizador.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Define o email do utilizador.
     * @param email O novo email do utilizador.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Define a morada do utilizador.
     * @param morada A nova morada do utilizador.
     */
    public void setMorada(String morada) {
        this.morada = morada;
    }

    /**
     * Define o plano de subscrição do utilizador.
     * Armazena um clone do plano fornecido e adiciona quaisquer pontos de bónus de adesão associados ao novo plano.
     * @param plano O novo {@link PlanoSubscricao} para o utilizador.
     */
    public void setPlanoSubscricao(PlanoSubscricao plano) {
        this.plano = plano.clone();
        this.pontos += this.plano.bonusAdesao(); // Adiciona bónus de adesão do novo plano
    }

    /**
     * Define a quantidade de pontos do utilizador.
     * @param pontos A nova quantidade de pontos.
     */
    public void setPontosAtuais(int pontos) {
        // Considerar validações, como pontos não poderem ser negativos.
        this.pontos = pontos;
    }

    /**
     * Define o reprodutor de música para o utilizador.
     * Se um reprodutor antigo existir e tiver observadores, eles devem ser desregistados
     * e os observadores padrão (histórico, este utilizador) devem ser adicionados ao novo reprodutor.
     * @param reprodutor O novo {@link ReprodutorDeMusica}.
     */
    public void setReprodutor(ReprodutorDeMusica reprodutor) {
        
        if (this.reprodutor != null) {
            this.reprodutor.removerObserver(this.historico);
            this.reprodutor.removerObserver(this);
        }
        this.reprodutor = reprodutor; 
        if (this.reprodutor != null && this.historico != null) {
            this.reprodutor.adicionaObserver(this.historico);
            this.reprodutor.adicionaObserver(this);
        }
    }
    
    /**
     * Define a data de adesão do utilizador.
     * @param dataAdesao A nova data de adesão.
     */
    public void setDataAdesao(LocalDateTime dataAdesao) {
        this.dataAdesao = dataAdesao;
    }

    /**
     * Cria e retorna uma cópia profunda deste objeto {@code Utilizador}.
     * @return Um clone do objeto {@code Utilizador}.
     */
    @Override
    public Utilizador clone() {
        return new Utilizador(this);
    }

    /**
     * Compara este utilizador com outro com base no nome, para ordenação.
     * @param outro O {@code Utilizador} a ser comparado.
     * @return Um valor negativo, zero, ou positivo se o nome deste utilizador for
     * lexicograficamente menor, igual ou maior que o nome do outro utilizador.
     */
    public int compareTo(Utilizador outro) {
        return this.nome.compareTo(outro.getNome());
    }

    /**
     * Compara este objeto {@code Utilizador} com outro para verificar igualdade.
     * Dois utilizadores são considerados iguais se todos os seus campos principais
     * (nome, email, morada, plano e pontos) forem iguais.
     * A biblioteca, histórico, reprodutor e data de adesão não são incluídos nesta verificação
     * de {@code equals} por defeito, o que é uma decisão de design comum.
     *
     * @param o O objeto a ser comparado.
     * @return {@code true} se os objetos forem iguais, {@code false} caso contrário.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        Utilizador utilizador = (Utilizador) o;
        // getPlanoSubscricao() do outro utilizador já retorna um clone.
        // Para uma comparação correta, comparamos o plano interno ou garantimos que `getPlanoSubscricao()`
        // não é usado aqui se o `this.plano` for a referência a comparar.
        return this.pontos == utilizador.pontos &&
               Objects.equals(this.nome, utilizador.nome) &&
               Objects.equals(this.email, utilizador.email) &&
               Objects.equals(this.morada, utilizador.morada) &&
               Objects.equals(this.plano, utilizador.plano); // Compara os planos internos diretamente
    }

    /**
     * Retorna um valor de código hash para este objeto {@code Utilizador}.
     * O código hash é gerado com base nos campos nome, email, morada, plano e pontos.
     *
     * @return O valor do código hash.
     */
    @Override
    public int hashCode() {
        return Objects.hash(nome, email, morada, plano, pontos);
    }

    /**
     * Retorna uma representação em string do objeto {@code Utilizador}.
     * Inclui nome, email, morada, plano e pontos.
     *
     * @return Uma string formatada representando o utilizador.
     */
    @Override
    public String toString() {
        return "Utilizador: " +
               "nome " + nome + " " +
               "email " + email + " " +
               "morada " + morada + " " +
               "plano " + plano.toString() + " " + // Chama toString() do plano
               "pontos " + pontos;
    }

    /**
     * Insere uma coleção de músicas (pelo nome) da biblioteca do utilizador no seu reprodutor.
     *
     * @param nomeDaColecao O nome da coleção a ser inserida no reprodutor.
     * @throws ColecaoNaoExisteNaBibliotecaException se a coleção não for encontrada na biblioteca do utilizador.
     */
    public void insereColecaoNoReprodutor(String nomeDaColecao) throws ColecaoNaoExisteNaBibliotecaException {
        ColecaoDeMusicasReproduzivel colecao = this.biblioteca.getColecao(nomeDaColecao);
        this.reprodutor.insereColecao(colecao);
    }

    /**
     * Solicita ao reprodutor para tocar a próxima música da coleção atualmente carregada.
     *
     * @return Um objeto {@link Reproducao} representando o resultado da tentativa de reprodução.
     * @throws ColecaoDeMusicaVaziaException se a coleção no reprodutor estiver vazia.
     */
    public Reproducao reproduzProximaMusica() throws ColecaoDeMusicaVaziaException {
        Reproducao reproducao = reprodutor.reproduzProximaMusica();
        return reproducao;
    }

    /**
     * Solicita ao reprodutor para tocar a música anterior da coleção atualmente carregada.
     *
     * @return Um objeto {@link Reproducao} representando o resultado da tentativa de reprodução.
     * @throws ColecaoDeMusicaVaziaException se a coleção no reprodutor estiver vazia.
     */
    public Reproducao reproduzMusicaAnterior() throws ColecaoDeMusicaVaziaException {
        return reprodutor.reproduzMusicaAnterior();
    }

    /**
     * Obtém uma playlist específica da biblioteca do utilizador pelo nome.
     *
     * @param nomePlayList O nome da playlist a ser recuperada.
     * @return Um clone da {@link Playlist} encontrada.
     * @throws ColecaoNaoExisteNaBibliotecaException se nenhuma coleção com esse nome for encontrada,
     * ou se a coleção encontrada não for uma instância de {@code Playlist}.
     */
    public Playlist getPlaylist(String nomePlayList) throws ColecaoNaoExisteNaBibliotecaException {
        ColecaoDeMusicasReproduzivel colecao = this.biblioteca.getColecao(nomePlayList);
        if (colecao instanceof Playlist) {
            return (Playlist) colecao;
        }
        throw new ColecaoNaoExisteNaBibliotecaException(
                "Coleção com o nome '" + nomePlayList + "' não é uma playlist.");
    }

    /**
     * Adiciona uma playlist à biblioteca do utilizador.
     * A playlist é clonada antes de ser adicionada.
     * @param playlist A {@link Playlist} a ser adicionada.
     */
    public void adicionarPlaylistABiblioteca(Playlist playlist) {
        this.biblioteca.adicionarPlaylistABiblioteca(playlist);
    }

    /**
     * Adiciona uma música à lista de favoritos do utilizador na sua biblioteca.
     *
     * @param musica A {@link Musica} a ser adicionada aos favoritos.
     * @throws MusicaJaExisteException se a música já estiver na lista de favoritos.
     */
    public void adicionarMusicaAosFavoritos(Musica musica) throws MusicaJaExisteException {
        this.biblioteca.adicionarMusicaAosFavoritos(musica);
    }

    /**
     * Remove uma música da lista de favoritos do utilizador na sua biblioteca.
     *
     * @param musica A {@link Musica} a ser removida dos favoritos.
     * @throws MusicaNaoExisteException se a música não estiver na lista de favoritos.
     */
    public void removerMusicaDosFavoritos(Musica musica) throws MusicaNaoExisteException {
        this.biblioteca.removerMusicaDosFavoritos(musica);
    }

    /**
     * Método de callback do padrão Observer, chamado quando o {@link ReprodutorDeMusica}
     * (que este utilizador observa) notifica uma nova reprodução.
     * Atualiza os pontos do utilizador com base no seu plano de subscrição e na música reproduzida.
     * Em seguida, notifica os próprios observadores deste utilizador (se houver algum registado diretamente nele).
     *
     * @param reproducao O objeto {@link Reproducao} que ocorreu.
     */
    @Override
    public void update(Reproducao reproducao) {
        if (reproducao.isSucesso()) { 
            pontos += this.plano.pontosPorMusica(pontos);
        }
        notificarObservers(reproducao);
    }

    /**
     * Retorna o número de playlists na biblioteca do utilizador.
     * @return O número de playlists.
     */
    public int numeroDePlaylistDaBiblioteca() {
        return this.biblioteca.numeroDePlaylist();
    }

    /**
     * Adiciona um álbum à biblioteca do utilizador.
     * O álbum é clonado antes de ser adicionado.
     * @param album O {@link Album} a ser adicionado.
     */
    public void adicionarAlbumABiblioteca(Album album) {
        this.biblioteca.adicionarAlbumABiblioteca(album);
    }

    /**
     * Verifica se uma música específica está marcada como favorita na biblioteca do utilizador.
     * @param musica A {@link Musica} a ser verificada.
     * @return {@code true} se a música for favorita, {@code false} caso contrário.
     */
    public boolean eFavorita(Musica musica) {
        return biblioteca.isFavorita(musica);
    }

    /**
     * Remove uma música da biblioteca do utilizador.
     * Isto inclui remover a música de qualquer álbum (cópia local na biblioteca)
     * e de qualquer playlist (cópia local na biblioteca) que a contenha.
     *
     * @param nomeAlbum O nome do álbum de onde remover a música (se aplicável à lógica de remoção de álbuns).
     * @param musicaParaRemover A {@link Musica} a ser removida.
     */
    public void removerMusicaDaBiblioteca(String nomeAlbum, Musica musicaParaRemover) {
       
        biblioteca.removerMusicaDeAlbum(nomeAlbum, musicaParaRemover);
        biblioteca.removerMusicaDePlaylist(musicaParaRemover);
    }

    /**
     * Alterna o modo shuffle (aleatório) no reprodutor de música do utilizador.
     */
    public void mudarShuffleNoReprodutor() {
        reprodutor.mudarShuffle();
    }

    /**
     * Remove uma playlist da biblioteca do utilizador.
     * @param playlist A {@link Playlist} a ser removida.
     */
    public void removerPlaylistUtilizador(Playlist playlist) {
        this.biblioteca.removerPlaylist(playlist.getNome());
    }

    public List<Playlist> getPlaylistsUtilizador() {
        return biblioteca.getPlaylists();
    }

    /**
     * Retorna a playlist de favoritos do utilizador.
     * 
     * @return A playlist de favoritos do utilizador.
     */
    public PlaylistListaDeFavoritos getFavoritosDoUtilizador() {
        return this.biblioteca.getPlaylistDeFavoritos();
    }
    

}