package controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exceptions.*;
import model.*;
import model.musica.Musica;
import model.playlists.Playlist;
import model.playlists.PlaylistListaDeFavoritos;
import model.playlists.PlaylistPorGeneroETempo;
import model.playlists.PlaylistPreferenciasComTempoMaximo;
import utils.EstadodaAPP;

/**
 * Classe SpotifUMController que atua como controlador entre a interface do utilizador e o modelo.
 */
public class SpotifUMController{

    private SpotifUMModel model;

    private static final String UTILIZADORES_DAT_PATH = "data/utilizadores.dat";
    private static final String PLAYLISTS_DAT_PATH = "data/playlists.dat";
    private static final String ALBUNS_DAT_PATH = "data/albuns.dat";

    public SpotifUMController(SpotifUMModel model) {
        this.model = model;
    }

    /**
     * Método para adicionar um utilizador ao sistema.
     * 
     * @param utilizador O utilizador a ser adicionado.
     * @return Mensagem de sucesso ou erro.
     */
    public String adicionarUtilizador(Utilizador utilizador) { 
        try {
            model.adicionarUtilizador(utilizador);
            return "Utilizador adicionado com sucesso!";
        } catch (UtilizadorJaExisteException e) {
            return e.getMessage(); 
        }
    }

    /**
     * Método para remover um utilizador do sistema.
     * 
     * @param utilizador O utilizador a ser removido.
     * @return Mensagem de sucesso ou erro.
     */
    public String removerUtilizador(Utilizador utilizador) {
        try {
            model.removerUtilizador(utilizador);
            return "Utilizador removido com sucesso!";
        } catch (UtilizadorNaoExisteException e) {
            return e.getMessage(); 
        }
    }

    /**
     * Método para adicionar um álbum ao sistema.
     * 
     * @param album O álbum a ser adicionado.
     * @return Mensagem de sucesso ou erro.
     */
    public String adicionarAlbum(Album album){
        try{
            // Verifica se já existe um álbum com o mesmo nome
        List<Album> albunsExistentes = model.getAlbums();
        boolean existeAlbumComMesmoNome = albunsExistentes.stream()
            .anyMatch(a -> a.getNome().equalsIgnoreCase(album.getNome()));

        
            if (existeAlbumComMesmoNome) {
                return "Já existe um álbum com o nome '" + album.getNome() + "'. Escolha outro nome.";
            }
            model.adicionarAlbum(album);
            return "Album adicionado com sucesso!";
        } catch (AlbumJaExisteException e){
            return e.getMessage();
        }
    }

    /**
     * Método para remover um álbum do sistema.
     * 
     * @param album O álbum a ser removido.
     * @return Mensagem de sucesso ou erro.
     */
    public String removerAlbum(Album album){
        try{
            model.removerAlbum(album);
            return "Album removido com sucesso!";
        }catch (AlbumNaoExisteException e){
            return e.getMessage();
        }
    }

    /**
     * Método para adicionar uma música a um álbum.
     * 
     * @param musica A música a ser adicionada.
     * @param album O álbum ao qual a música será adicionada.
     * @return Mensagem de sucesso ou erro.
     */
    public String adicionarMusica (Musica musica, Album album){
        try{
            model.adicionarMusicaAoAlbum(album, musica);
            return "Música adicionada com sucesso!";
        } catch (AlbumNaoExisteException e){
            return e.getMessage();
        } catch (MusicaJaExisteException e){
            return e.getMessage();
        }
    }

    /**
     * Método para remover uma música de um álbum.
     * 
     * @param albumTitulo O título do álbum.
     * @param musicaNome O nome da música a ser removida.
     * @return Mensagem de sucesso ou erro.
     */
    public String removerMusica(String albumTitulo, String musicaNome){
        try{
            model.removerMusicaDoAlbum(albumTitulo, musicaNome);
            return "Música removida com sucesso!";
        } catch (AlbumNaoExisteException e){
            return e.getMessage();
        } catch (MusicaNaoExisteException e){
            return e.getMessage();
        }
    }


    public String mudarShuffleNoReprodutor(String emailUtilizador){

        try {
            model.mudarShuffleNoReprodutorDoUtilizador(emailUtilizador);
        } catch (UtilizadorNaoExisteException e) {
            return e.getMessage();
        }
        return "Modo shuffle alterado com sucesso!";
    }

    public String removerPlaylist(Utilizador utilizador, Playlist nomePlayList) {

        try{
            model.removerPlaylistAoUtilizador(utilizador, nomePlayList);
            return "Playlist removida com sucesso!";
        } catch (PlanoDeSubscricaoNaoPermiteException e) {
            return e.getMessage();
        } catch (PlaylistNaoExisteException e) {
            return e.getMessage();
        }
    }


    /**
     * Método para tornar uma playlist pública.
     * 
     * @param emailUtilizador O email do utilizador.
     * @param nomePlayList O nome da playlist a ser tornada pública.
     * @return Mensagem de sucesso ou erro.
     */
    public String tornarPlaylistPublica(String emailUtilizador, String nomePlayList) {
        try {
            model.tornarPlaylistPublica(emailUtilizador, nomePlayList);
            return "Playlist tornada pública com sucesso!";
        } catch (UtilizadorNaoExisteException e){
            return e.getMessage();
        } catch (PlanoDeSubscricaoNaoPermiteException e) {
            return e.getMessage();
        } catch (ColecaoNaoExisteNaBibliotecaException e) {
            return e.getMessage();
        }
    }


    public String gerarPlaylistPorTempoeGenero(String emailUtilizador, String nomePlayList, int tempo, String genero) {
        try {
            Utilizador utilizador = model.getUtilizadorPorEmail(emailUtilizador);
            PlaylistListaDeFavoritos favoritos = utilizador.getFavoritosDoUtilizador();
            PlaylistPorGeneroETempo novaPlaylist = new PlaylistPorGeneroETempo(nomePlayList, genero, tempo, favoritos.getMusicas());
            utilizador.adicionarPlaylistABiblioteca(novaPlaylist);
            return "Playlist criada com sucesso!";
        } catch (UtilizadorNaoExisteException e) {
            return e.getMessage();
        } catch (Exception e) {
            return "Erro ao criar a playlist: " + e.getMessage();
        }
    }

    public String gerarPlaylistPorPreferenciasETempo(String emailUtilizador, String nomePlayList, int tempoMaximo) {
        try {
            Utilizador utilizador = model.getUtilizadorPorEmail(emailUtilizador);
            PlaylistListaDeFavoritos favoritos = utilizador.getFavoritosDoUtilizador();
            PlaylistPreferenciasComTempoMaximo novaPlaylist = new PlaylistPreferenciasComTempoMaximo(nomePlayList, tempoMaximo, favoritos);
            utilizador.adicionarPlaylistABiblioteca(novaPlaylist);
            return "Playlist criada com sucesso!";
        } catch (UtilizadorNaoExisteException e) {
            return e.getMessage();
        } catch (Exception e) {
            return "Erro ao criar a playlist: " + e.getMessage();
        }
    }

    /**
     * Método para obter uma lista de músicas de um álbum.
     * 
     * @param album O álbum do qual se deseja obter as músicas.
     * @return Uma lista de músicas do álbum.
     */
    public List<Musica> getMusicasAlbum(Album album){
        return model.getMusicasAlbum(album);
    }

    /**
     * Método para obter um utilizador pelo seu email.
     * 
     * @param email O email do utilizador.
     * @return O utilizador correspondente ao email.
     * @throws UtilizadorNaoExisteException Se o utilizador não existir.
     */
    public Utilizador getUtilizador(String email) throws UtilizadorNaoExisteException  {
        return model.getUtilizadorPorEmail(email);
    }
    
    /**
     * Método para reproduzir uma música individual.
     * 
     * @param musicaNome O nome da música a ser reproduzida.
     * @param albumNome O nome do álbum ao qual a música pertence.
     * @return Mensagem de sucesso ou erro.
     */
    public String reproduzirMusicaIndividual (String musicaNome, String albumNome) {
        try {
            return model.reproduzirMusica(musicaNome, albumNome);
        } catch (MusicaNaoExisteException e) {
            return e.getMessage();
        } catch (AlbumNaoExisteException e) {
            return e.getMessage();
        }
    }

    /**
     * Método para obter uma lista de utilizadores.
     * 
     * @return Uma lista de utilizadores.
     */
    public List<Utilizador> getUtilizadores() {
        return model.getUtilizadores();
    }

    /**
     * Método para obter um estado guardado dos utilizadores
     * 
     * @return Um mapa de utilizadores com o estado guardado.
     */
    public Map<String, Utilizador> getUtilizadoresNovo() {
        return model.getUtilizadoresEstado();
    }

    /**
     * Método para obter uma lista de álbuns.
     * 
     * @return Uma lista de álbuns.
     */
    public List<Album> getAlbums() {
        return model.getAlbums();
    }

    /**
     * Método para obter uma lista de playlists públicas.
     * 
     * @return Uma lista de playlists públicas.
     */
    public List<Playlist> getPlaylitsPublicas() {
        return model.getPlaylistsPublicas();
    }

    /**
     * Método para obter um estado guardado das playlists.
     * 
     * @return Uma lista de playlists com o eatado guardado.
     */
    public Map<String, Playlist> getPlaylistsNovo() {
        return model.getPlaylistEstado();
    }

    /**
     * Método para obter um estado guardado dos álbuns.
     * 
     * @return Um mapa de álbuns com o estado guardado.
     */
    public Map<String, Album> getAlbum() {
        return model.getAlbumsEstado();
    }
       
    /**
     * Método para mudar o plano de subscrição de um utilizador.
     * 
     * @param utilizador O utilizador cujo plano será mudado.
     * @param nomeDoPlano O nome do novo plano de subscrição.
     * @return Mensagem de sucesso ou erro.
     */
    public String mudarPlano(Utilizador utilizador, String nomeDoPlano) {
        try {
            return model.mudarPlanoDoUtilizador(utilizador.getEmail(), nomeDoPlano);
        } catch (UtilizadorNaoExisteException e) {
            return e.getMessage();
        }
    }
    
    /**
     * Método para marcar uma música como favorita.
     * 
     * @param musica A música a ser marcada como favorita.
     * @param utilizador O utilizador que está a selecionar a música como favorita.
     * @return Mensagem de sucesso ou erro.
     */
    public String marcarComoFavorita(Musica musica, Utilizador utilizador) {
        try {
            model.adicionarMusicaAosFavoritos(utilizador, musica);
        } catch (MusicaJaExisteException e) {
            return e.getMessage();
        }
        return "Adicionado aos favoritos com sucesso";
    }

    /**
     * Método para remover uma música dos favoritos de um utilizador.
     * 
     * @param musica A música a ser removida dos favoritos.
     * @param utilizador O utilizador que está a remover a música dos favoritos.
     * @return Mensagem de sucesso ou erro.
     */
    public String removerDosFavoritos(Musica musica, Utilizador utilizador) {
        try {
            model.removerMusicaDosFavoritos(utilizador, musica);
        } catch (MusicaNaoExisteException e) {
            return e.getMessage();
        }
        return "Removido dos favoritos com sucesso";
    }

    /**
     * Método para obter a playlist de favoritos de um utilizador.
     * 
     * @param emailUtilizador O email do utilizador.
     * @return A playlist de favoritos do utilizador.
     */
    public PlaylistListaDeFavoritos getFavoritosDoUtilizador(Utilizador utilizador) {
        try {
            return model.getFavoritosDoUtilizador(utilizador.getEmail());
        } catch (UtilizadorNaoExisteException e) {
            throw new RuntimeException("Utilizador não encontrado: " + e.getMessage());
        }
    }

//------------------------------------------ Estatísticas--------------------------------------------------------
        
    /**
     * Método para obter a música mais reproduzida.
     * @return A música mais reproduzida.
     */
    public Musica musicaMaisReproduzida() {
        return model.musicaMaisReproduzida();
    }
    
    /**
     * Método para obter o artista mais ouvido.
     * @return O artista mais ouvido.
     */
    public String interpreteMaisOuvido() {
        return model.interpreteMaisOuvido();
    }
    
    /**
     * Método para obter o utilizador que mais ouviu músicas em um determinado período.
     * 
     * @param inicio O início do período.
     * @param fim O fim do período.
     * @return O utilizador que mais ouviu músicas no período especificado.
     */
    public Utilizador utilizadorMaisOuviuNumPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return model.utilizadorMaisOuviuNumPeriodo(model.getUtilizadores(), inicio, fim);
    }
    
    /**
     * Método para obter o utilizador que mais pontos acumulou.
     * 
     * @return O utilizador que mais pontos acumulou.
     */
    public Utilizador utilizadorComMaisPontos() {
        return model.utilizadorComMaisPontos();
    }
    
    /**
     * Método para obter o gênero mais reproduzido.
     * 
     * @return O gênero mais reproduzido.
     */
    public String generoMaisReproduzido() {
        return model.generoMaisReproduzido();
    }
    
    /**
     * Método para obter o número de playlists públicas existentes.
     * 
     * @return O número de playlists públicas.
     */
    public int quantasPlaylistsPublicasExistem() {
        return model.quantasPlaylistsPublicasExistem();
    }
    
    /**
     * Método para obter o utilizador que possui mais playlists.
     * 
     * @return O utilizador com mais playlists.
     */
    public Utilizador utilizadorComMaisPlaylists() {
        return model.utilizadorComMaisPlaylists(model.getUtilizadores());
    }

    //TODO: falta tratar exception de biblioteca vazia
    public List<ColecaoDeMusicasReproduzivel>  getConteudoBiblioteca(Utilizador utilizador) {
       return model.getConteudoBibliotecaDoUtilizador(utilizador);
    }

    //TODO: rever exception geral
    /**
     * Método para reproduzir a próxima música do reprodutor.
     * @param utilizador O utilizador que está a reproduzir a música.
     * @return A reprodução da próxima música.
     */
    public Reproducao avancarMusica(Utilizador utilizador) {
        try {
            return model.reproduzProximaMusicaDoReprodutor(utilizador.getEmail());
        } catch (UtilizadorNaoExisteException e){
            return new Reproducao("Impossível reproduzir");
        } catch (ColecaoDeMusicaVaziaException e) {
            return new Reproducao("Impossível reproduzir");
        } 
    }

    /**
     * Método para reproduzir a música anterior do reprodutor.
     * 
     * @param utilizador O utilizador que está a reproduzir a música.
     * @return A reprodução da música anterior.
     */
    public Reproducao retrocederMusica(Utilizador utilizador) {
        try {
            return model.reproduzMusicaAnteriorDoReprodutor(utilizador.getEmail());
        } catch (UtilizadorNaoExisteException e) {
            return new Reproducao("Impossível reproduzir");
        } catch (ColecaoDeMusicaVaziaException e) {
            return new Reproducao("Impossível reproduzir");
        }
    }

    /**
     * Método para reproduzir uma coleção de músicas.
     * 
     * @param utilizador O utilizador que está a reproduzir a coleção.
     * @param entidade A coleção de músicas a ser reproduzida.
     * @return A reprodução da coleção de músicas.
     */
    public Reproducao reproduzirColecaoMusica(Utilizador utilizador, ColecaoDeMusicasReproduzivel entidade) {
        try{
            return model.reproduzirColecaoMusica(utilizador.getEmail(), entidade);
        }catch (ColecaoNaoExisteNaBibliotecaException e){
            return new Reproducao("Impossível reproduzir");
        } catch (ColecaoDeMusicaVaziaException e) {
            return new Reproducao("Impossível reproduzir");
        }
    }

    /**
     * Metodo que adiciona um album à biblioteca do utilizador
     * 
     * @param entidade Album a ser adicionado.
     * @param utilizador Utilizador cuja biblioteca vai ser alterada
     * @return Mensagem de sucesso ou insucesso.
     */
    public String adicionarAlbumABiblioteca(Album entidade, Utilizador utilizador) {
         try {
            model.adicionarAlbumABiblioteca(entidade, utilizador.getEmail());
         } catch (PlanoDeSubscricaoNaoPermiteException e) {
            return e.getMessage();
         }
        return "Adicionado com sucesso";
    }

    /**
     * Método que adiciona uma playlist à biblioteca do utilizador
     * @param playlist Playlist a ser adicionada
     * @param utilizador Utilizador cuja biblioteca será alterada.
     * @return Mensagem de sucesso ou insucesso
     */
    public String adicionarPlaylistABiblioteca(Playlist playlist, Utilizador utilizador) {
        try {
            model.adicionarPlaylistAoUtilizador(utilizador.getEmail(), playlist);
            return "Playlist adicionada á biblioteca com sucesso!";
        } catch (UtilizadorNaoExisteException e) {
            return e.getMessage();
        } catch (PlanoDeSubscricaoNaoPermiteException e) {
            return e.getMessage();
        } 
    }

    /**
     * Método que permite ao utilizador guardar uma playlist na sua biblioteca
     * @param utilizador O utilizador que pretende guardar a playlist
     * @param nomePlayList Nome atribuido à playlist a adicionar
     * @param resposta Input do utilizador no terminal
     * @return Mensagem de sucesso ou insucesso.
     */
    public String adicionarPlaylist(Utilizador utilizador, Playlist nomePlayList, String resposta){
        try {
            if (resposta.equals("s")) {
                model.adicionarPlaylistAoUtilizador(utilizador.getEmail(), nomePlayList);
                this.tornarPlaylistPublica(utilizador.getEmail(), nomePlayList.getNome());
            } else {
                model.adicionarPlaylistAoUtilizador(utilizador.getEmail(), nomePlayList);
                
            }
        } catch (UtilizadorNaoExisteException e) {
            return e.getMessage();
        } catch (PlanoDeSubscricaoNaoPermiteException e) {
            return e.getMessage();
        }
        return "Playlist adicionada com sucesso!";
    }

    /**
     * Método que permite ao utilizador adicionar uma playlist aleatória à sua biblioteca
     * @param utilizador O utilizador que pretende adicionar a playlist aleatoria
     * @param nomePlayList Nome atribuido à playlist a adicionar
     * @param resposta Input do utilizador no terminal
     * @return Mensagem de sucesso ou insucesso.
     */
    public String adicionarPlaylistAleatoria(Utilizador utilizador, Playlist nomePlayList, String resposta){
        try {

            List<Playlist> playlistsExistentes = model.getPlaylistsUtilizador(utilizador);
            boolean existePlaylistComMesmoNome = playlistsExistentes.stream()
                .anyMatch(p -> p.getNome().equalsIgnoreCase(nomePlayList.getNome()));
    
            if (existePlaylistComMesmoNome) {
                return "Já existe uma playlist com o nome '" + nomePlayList.getNome() + "'. Escolha outro nome.";
            }
            if (resposta.equals("s")) {
                model.adicionarPlaylistAleatoriaAoUtilizador(utilizador.getEmail(), nomePlayList);
                this.tornarPlaylistPublica(utilizador.getEmail(), nomePlayList.getNome());
            } else {
                model.adicionarPlaylistAleatoriaAoUtilizador(utilizador.getEmail(), nomePlayList);
                
            }
        } catch (UtilizadorNaoExisteException e) {
            e.getMessage();
        } catch (PlanoDeSubscricaoNaoPermiteException e) {
            e.getMessage();
        }
        return "Playlist adicionada com sucesso!";
    }

    /**
     * Método que permite salvar os dados do estado atual da aplicação.
     */
    public void salvarDados() {

        EstadodaAPP.guardarObjeto(UTILIZADORES_DAT_PATH, model.getUtilizadoresEstado());
        EstadodaAPP.guardarObjeto(PLAYLISTS_DAT_PATH, model.getPlaylistEstado());
        EstadodaAPP.guardarObjeto(ALBUNS_DAT_PATH, model.getAlbumsEstado());
    }

    /**
     * Método que carrega os dados do estado atual da aplicação.
     */
    public void carregarDados() {
        Map<String, Utilizador> utilizadores = EstadodaAPP.carregarObjeto(UTILIZADORES_DAT_PATH, new HashMap<>());
        Map<String, Album> albuns = EstadodaAPP.carregarObjeto(ALBUNS_DAT_PATH, new HashMap<>());
        Map<String, Playlist> playlistsPublicas = EstadodaAPP.carregarObjeto(PLAYLISTS_DAT_PATH, new HashMap<>());
        model.setUtilizadores(utilizadores);
        model.setAlbuns(albuns);
        model.setPlaylistsPublicas(playlistsPublicas);
    }

    /**
     * Método que devolve todas as músicas de todas as horas.
     */

        public List<Musica> getTodasMusicasDosAlbuns() {
            return model.getTodasMusicasDosAlbuns();
        }
    
        /** 
         * Método que adicona uma Música a uma Playlist se ela existir em algum Album.
         */

        public String adicionarMusicaNaPlaylistSeEstiverEmAlgumAlbum(Musica musica, Playlist playlist) {
            try {
                model.adicionarMusicaPlaylist(musica, playlist);
            } catch (MusicaJaExisteException e) {
                return e.getMessage();
            }
            return "Música adicionada à playlist com sucesso!";
        }


}






