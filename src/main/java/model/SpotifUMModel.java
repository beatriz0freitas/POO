package model;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import exceptions.*;
import model.musica.Musica;
import model.planosSubscricao.PlanoSubscricao;
import model.planosSubscricao.PlanoSubscricaoFactory;
import model.playlists.Playlist;
import model.playlists.PlaylistListaDeFavoritos;

public class SpotifUMModel implements ReproducaoObserver{

    private Map<String, Utilizador> utilizadores;

    private Map<String, Album> albuns;

    private Map<String, Playlist> playlistsPublicas;


    public SpotifUMModel() {
        this.utilizadores = new HashMap<>();
        this.albuns = new HashMap<>();
        this.playlistsPublicas = new HashMap<>();
    }

    public void setUtilizadores(Map<String, Utilizador> utilizadores) {
        this.utilizadores = utilizadores.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().clone()));
    }

    public void setAlbuns(Map<String, Album> albuns) {
        this.albuns = albuns.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().clone()));
    }

    public void setPlaylistsPublicas(Map<String, Playlist> playlistsPublicas) {
        this.playlistsPublicas = playlistsPublicas.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().clone()));
    }

    //--------- gets ----------

    public List<Utilizador> getUtilizadores() {
        return utilizadores.values().stream().map(Utilizador::clone).collect(Collectors.toList());
    }

    public Map<String, Utilizador> getUtilizadoresEstado() {
        return utilizadores.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().clone()));
    }

    public List<Album> getAlbums() {
        return albuns.values().stream().map(Album::clone).collect(Collectors.toList());
    }

    public Map<String, Album> getAlbumsEstado() {
        return albuns.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().clone()));
    }

    public List<Playlist> getPlaylistsPublicas() {
        return playlistsPublicas.values().stream().map(Playlist::clone).collect(Collectors.toList());
    }

    public Map<String, Playlist> getPlaylistEstado() {
        return playlistsPublicas.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().clone()));
    }

    public Utilizador getUtilizadorPorEmail(String email) throws UtilizadorNaoExisteException {
        if (!utilizadores.containsKey(email)) {
            throw new UtilizadorNaoExisteException("Utilizador não existe");
        } else
            return utilizadores.get(email).clone();
    }


    public Album getAlbum(String nome) throws AlbumNaoExisteException {
        if (!albuns.containsKey(nome)) {
            throw new AlbumNaoExisteException("Album não existe");
        } else
            return albuns.get(nome).clone();
    }

    //--------- adicionar e remover entidades ---------

    public void adicionarUtilizador(Utilizador utilizador) throws UtilizadorJaExisteException {

        String email = utilizador.getEmail();
        if (utilizadores.containsKey(email)) {
            throw new UtilizadorJaExisteException("Utilizador já existe com o email: " + email);
        }
        utilizadores.put(email, utilizador.clone());

        utilizador.adicionaObserver(this);
    }

    public void removerUtilizador(Utilizador utilizador) throws UtilizadorNaoExisteException {
        if (!utilizadores.containsKey(utilizador.getEmail())) {
            throw new UtilizadorNaoExisteException("Utilizador não existe");
        } else
            utilizador.removerObserver(this);
            utilizadores.remove(utilizador.getEmail());
    }


    public void adicionarAlbum(Album album) throws AlbumJaExisteException {

        String titulo = album.getNome();

        if (albuns.containsKey(titulo)) {
            throw new AlbumJaExisteException("Album já existe com o título: " + album.getNome());
        } else
            albuns.put(titulo, album.clone());
    }

    public void removerAlbum(Album album) throws AlbumNaoExisteException {
        if (!albuns.containsKey(album.getNome())) {
            throw new AlbumNaoExisteException("Album não existe");
        }

        // Remove album from the system
        albuns.remove(album.getNome());

        // Remove album from all users' libraries
        utilizadores.values().forEach(utilizador -> {
            try {
                utilizador.getBiblioteca().removerAlbum(album.getNome());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void adicionarMusicaAoAlbum(Album album, Musica musica)
            throws AlbumNaoExisteException, MusicaJaExisteException {

        if (!albuns.containsKey(album.getNome())) {
            throw new AlbumNaoExisteException("Album não existe");
        }

        albuns.get(album.getNome()).adicionaMusica(musica);

    }

    public List<Musica> getMusicasAlbum(Album album) {
        return album.getMusicas();
    }

    public void removerMusicaDoAlbum(String nomeAlbum, String tituloMusica)
            throws AlbumNaoExisteException, MusicaNaoExisteException {
        if (!albuns.containsKey(nomeAlbum)) {
            throw new AlbumNaoExisteException("Album não existe");
        }

        Album album = albuns.get(nomeAlbum);
        Musica musica = album.getMusica(tituloMusica);

        if (musica == null) {
            throw new MusicaNaoExisteException("Música não existe no álbum");
        }

        album.removerMusica(musica);

        utilizadores.values().forEach(utilizador -> utilizador.removerMusicaDaBiblioteca(nomeAlbum, musica));
    }

    public Musica musicaMaisReproduzida() {
        return albuns.values().stream()
                .map(Album::musicaMaisReproduzida)
                .filter(Objects::nonNull)
                .max(Comparator.comparingInt(Musica::getNumeroDeReproducoes))
                .map(Musica::clone) 
                .orElse(null); 
    }

    public Utilizador utilizadorComMaisPontos() {
        return utilizadores.values().stream()
                .max((u1, u2) -> Integer.compare(u1.getPontosAtuais(), u2.getPontosAtuais()))
                .get().clone();
    }

    public int numeroUtilizadores() {
        return utilizadores.size();
    }

    public int quantasPlaylistsPublicasExistem() {
        return playlistsPublicas.size();
    }

    public String generoMaisReproduzido() {
        Map<String, Integer> contadorPorGenero = new HashMap<>();

        albuns.values().stream()
                .flatMap(album -> album.getMusicas().stream())
                .forEach(musica -> {
                    String genero = musica.getGenero();
                    int reproducoes = musica.getNumeroDeReproducoes();
                    contadorPorGenero.put(genero, contadorPorGenero.getOrDefault(genero, 0) + reproducoes);
                });

        return contadorPorGenero.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Nenhum género encontrado");
    }

    // reprodução

    public Reproducao reproduzProximaMusicaDoReprodutor(String emailUtilizador)
            throws UtilizadorNaoExisteException, ColecaoDeMusicaVaziaException {
        Utilizador utilizador = this.getUtilizadorPorEmail(emailUtilizador);
        return utilizador.reproduzProximaMusica();
    }

    public Reproducao reproduzMusicaAnteriorDoReprodutor(String emailUtilizador)
            throws UtilizadorNaoExisteException, ColecaoDeMusicaVaziaException {
        Utilizador utilizador = this.getUtilizadorPorEmail(emailUtilizador);
        return utilizador.reproduzMusicaAnterior();
    }

    public Reproducao reproduzirColecaoMusica(String EmailUtilizador, ColecaoDeMusicasReproduzivel entidade)
            throws ColecaoNaoExisteNaBibliotecaException, ColecaoDeMusicaVaziaException {
        Utilizador utilizador = utilizadores.get(EmailUtilizador);
        utilizador.insereColecaoNoReprodutor(entidade.getNome());
        return utilizador.reproduzProximaMusica();
    }

    public void tornarPlaylistPublica(String emailUtilizador, String nomePlayList) throws UtilizadorNaoExisteException,
            PlanoDeSubscricaoNaoPermiteException, ColecaoNaoExisteNaBibliotecaException {
        Utilizador utilizador = this.getUtilizadorPorEmail(emailUtilizador);
        Playlist playlistUtilizador = utilizador.getPlaylist(nomePlayList);
        utilizador.getPlanoSubscricao().executarTornarPlaylistPublica( () -> playlistsPublicas.put(playlistUtilizador.getNome(), playlistUtilizador.clone()));
    }
    

    public String reproduzirMusica(String musica, String nomeAlbum)
            throws MusicaNaoExisteException, AlbumNaoExisteException {

        if (!albuns.containsKey(nomeAlbum)) {
            throw new AlbumNaoExisteException("Álbum não existe");
        }
        Album album = albuns.get(nomeAlbum);
        Musica musicaParaReproduzir = album.getMusica(musica);

        if (musicaParaReproduzir == null) {
            throw new MusicaNaoExisteException("Música não existe no álbum");
        }

        return musicaParaReproduzir.reproduzir();

    }

    public void insereColecaoNoReprodutor(String emailUtilizador, String nomeDaColecao)
            throws UtilizadorNaoExisteException, ColecaoNaoExisteNaBibliotecaException {
        Utilizador utilizador = this.getUtilizadorPorEmail(emailUtilizador);
        utilizador.insereColecaoNoReprodutor(nomeDaColecao);
    }

    public void adicionarPlaylistAoUtilizador(String emailUtilizador, Playlist nomePlayList)
            throws UtilizadorNaoExisteException, PlanoDeSubscricaoNaoPermiteException {
            Utilizador utilizador = utilizadores.get(emailUtilizador);
            if (utilizador == null) throw new UtilizadorNaoExisteException("Utilizador não existe");
            utilizador.getPlanoSubscricao().executarAdicionarABiblioteca(() -> utilizador.adicionarPlaylistABiblioteca(nomePlayList));
    }

    public void removerPlaylistAoUtilizador(Utilizador utilizador, Playlist nomePlaylist)
        throws PlaylistNaoExisteException, PlanoDeSubscricaoNaoPermiteException {

            Utilizador utilizadorOriginal = utilizadores.get(utilizador.getEmail());
            utilizadorOriginal.getPlanoSubscricao().executarAdicionarABiblioteca(
        () -> utilizadorOriginal.removerPlaylistUtilizador(nomePlaylist)
    );
}

    public void adicionarPlaylistAleatoriaAoUtilizador(String emailUtilizador, Playlist nomePlayList)
    throws UtilizadorNaoExisteException, PlanoDeSubscricaoNaoPermiteException {
    Utilizador utilizador = utilizadores.get(emailUtilizador);
    utilizador.getPlanoSubscricao().executarAdicionarPlaylistAleatoria(() -> utilizador.adicionarPlaylistABiblioteca(nomePlayList));
}

    public String interpreteMaisOuvido() {
        Map<String, Integer> contador = new HashMap<>();

        albuns.values().stream()
                .flatMap(album -> album.getMusicas().stream())
                .forEach(musica -> {
                    String interprete = musica.getInterprete();
                    int reproducoes = musica.getNumeroDeReproducoes();
                    contador.put(interprete, contador.getOrDefault(interprete, 0) + reproducoes);
                });

        return contador.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Nenhum intérprete encontrado");
    }

    public Utilizador utilizadorMaisOuviuNumPeriodo(List<Utilizador> utilizadores, LocalDateTime inicio,
            LocalDateTime fim) {
        if (utilizadores == null || utilizadores.isEmpty()) {
            return null;
        }

        LocalDateTime now = LocalDateTime.now();
        if (inicio.isAfter(now) || fim.isAfter(now)) {
            return null; // Return null if the period is entirely in the future
        }

        return utilizadores.stream()
                .filter(utilizador -> utilizador.getHistorico() != null && !utilizador.getHistorico().getHistorico().isEmpty())
                .max(Comparator.comparingInt(utilizador -> (int) utilizador.getHistorico().getHistorico().stream()
                        .filter(r -> {
                            LocalDateTime data = r.getData();
                            return data != null && !data.isBefore(inicio) && !data.isAfter(fim);
                        })
                        .count()))
                .orElse(null);
    }

    public Utilizador utilizadorComMaisPlaylists(List<Utilizador> utilizadores) {
        return utilizadores.stream()
                .max(Comparator.comparingInt(utilizador -> utilizador.numeroDePlaylistDaBiblioteca()))
                .orElse(null);
    }

    public List<ColecaoDeMusicasReproduzivel> getConteudoBibliotecaDoUtilizador(Utilizador utilizador) {
        return utilizador.getBiblioteca().getConteudo();
    }

    public void adicionarAlbumABiblioteca(Album album, String emailUtilizador) throws PlanoDeSubscricaoNaoPermiteException {
        Utilizador utilizador = utilizadores.get(emailUtilizador);
        utilizador.getPlanoSubscricao().executarAdicionarABiblioteca(() -> utilizador.adicionarAlbumABiblioteca(album)); // meter pelo plano
    }

    public void adicionarMusicaAosFavoritos (Utilizador utilizador, Musica musica) throws MusicaJaExisteException{

        utilizadores.get(utilizador.getEmail()).adicionarMusicaAosFavoritos(musica);

    }
    public void removerMusicaDosFavoritos (Utilizador utilizador, Musica musica) throws MusicaNaoExisteException{

        utilizadores.get(utilizador.getEmail()).removerMusicaDosFavoritos(musica);

    }

    public String mudarPlanoDoUtilizador(String email, String nomeDoPlano) throws UtilizadorNaoExisteException {

        Utilizador utilizador = utilizadores.get(email);

    if (utilizador == null) {
        throw new UtilizadorNaoExisteException("Utilizador não existe.");
    }

    PlanoSubscricao novoPlano = PlanoSubscricaoFactory.criarPlano(nomeDoPlano);
    if (novoPlano == null) {
        return "Plano inválido.";
    }

    utilizador.setPlanoSubscricao(novoPlano);
    return "Plano alterado com sucesso para: " + novoPlano.getNomePlano();
}

    @Override
    public void update(Reproducao reproducao) {
        if (reproducao == null || reproducao.getMusica() == null) return;

    Musica musicaReproduzida = reproducao.getMusica();

    albuns.values().forEach(album -> album.incrementaReproducaoSeExistir(musicaReproduzida));

}

    public void mudarShuffleNoReprodutorDoUtilizador(String emailUtilizador) throws UtilizadorNaoExisteException {
        Utilizador utilizador = utilizadores.get(emailUtilizador);
        if (utilizador == null) throw new UtilizadorNaoExisteException("Utilizador não existe");

        utilizador.mudarShuffleNoReprodutor();
    }

    public List<Musica> getTodasMusicasDosAlbuns() {
        return albuns.values().stream()
                     .flatMap(album -> album.getMusicas().stream())
                     .map(Musica::clone) // se quiser proteger o estado interno
                     .collect(Collectors.toList());
    }
    
    public void adicionarMusicaPlaylist(Musica musica, Playlist playlist) throws MusicaJaExisteException {
 
    List<Musica> musicasDosAlbuns = this.getTodasMusicasDosAlbuns();

    boolean existeNoAlbum = musicasDosAlbuns.stream()
                                            .anyMatch(m -> m.equals(musica));

    if (existeNoAlbum) {
        playlist.adicionarMusica(musica);
    } 
}

    public List<Playlist> getPlaylistsUtilizador(Utilizador utilizador) {
        return utilizador.getPlaylistsUtilizador();
    }

    public PlaylistListaDeFavoritos getFavoritosDoUtilizador(String emailUtilizador) throws UtilizadorNaoExisteException {
        Utilizador utilizador = this.utilizadores.get(emailUtilizador);
        return utilizador.getFavoritosDoUtilizador();
    }

}