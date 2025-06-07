package model;

import java.io.Serializable;
import java.util.Random;

import exceptions.ColecaoDeMusicaVaziaException;
import model.musica.Musica;
import model.playlists.PlaylistAleatoria; 

/**
 * Controla a reprodução de músicas de uma coleção.
 * Esta classe é responsável por gerir a música atual, avançar para a próxima,
 * retroceder para a anterior, e suportar um modo de reprodução aleatória (shuffle).
 * Estende {@link ReproducaoObservable} para notificar observadores sobre
 * cada reprodução e implementa {@link Serializable} para persistência.
 */
public class ReprodutorDeMusica extends ReproducaoObservable implements Serializable {


    private final Random random = new Random();

    private ColecaoDeMusicasReproduzivel colecao; // A coleção de músicas a ser reproduzida
    private int indiceAtual;                      // O índice da música atualmente selecionada na coleção
    private boolean modoShuffle;                  // Flag para indicar se o modo aleatório está ativo

    /**
     * Construtor padrão.
     * Inicializa o reprodutor com uma {@link PlaylistAleatoria} vazia por defeito,
     * o índice atual como -1 (nenhuma música selecionada) e o modo shuffle desativado.
     */
    public ReprodutorDeMusica() {
        // Inicializa com uma coleção vazia por defeito para evitar NullPointerException
        this.colecao = new PlaylistAleatoria(); // Ou outra implementação padrão de ColecaoDeMusicasReproduzivel
        this.indiceAtual = -1; // Indica que nenhuma música foi reproduzida ou selecionada ainda
        this.modoShuffle = false;
    }

    /**
     * Insere uma nova coleção de músicas no reprodutor.
     * O índice atual é reiniciado para -1, indicando que a reprodução
     * começará do início (ou aleatoriamente) da nova coleção.
     *
     * @param colecao A {@link ColecaoDeMusicasReproduzivel} a ser carregada no reprodutor.
     * Assume-se que {@code colecao} não é nula.
     */
    public void insereColecao(ColecaoDeMusicasReproduzivel colecao) {
        this.colecao = colecao; // Idealmente, armazenaria um clone se ColecaoDeMusicasReproduzivel for mutável externamente
        this.indiceAtual = -1; // Reinicia o índice para a nova coleção
    }

    /**
     * Reproduz a próxima música da coleção atual.
     * Avança o índice (ou escolhe um aleatório se o modo shuffle estiver ativo),
     * obtém a música correspondente, cria um objeto {@link Reproducao} e
     * notifica os observadores.
     *
     * @return Um objeto {@link Reproducao} contendo a música reproduzida e o estado da reprodução.
     * @throws ColecaoDeMusicaVaziaException se a coleção atual estiver vazia.
     */
    public Reproducao reproduzProximaMusica() throws ColecaoDeMusicaVaziaException {
        if (this.colecao == null || this.colecao.getTamanho() == 0) {
            throw new ColecaoDeMusicaVaziaException("A coleção de músicas está vazia ou não foi definida.");
        }
        this.avançarIndiceAtual();
        Musica musica = colecao.getMusicaPorIndice(indiceAtual); // Pode lançar ColecaoDeMusicaVaziaException se o índice for inválido após avançar (improvável com a lógica atual)
        Reproducao reproducao = new Reproducao(musica);
        notificarObservers(reproducao);
        return reproducao;
    }

    /**
     * Reproduz a música anterior da coleção atual.
     * Retrocede o índice (ou escolhe um aleatório se o modo shuffle estiver ativo),
     * obtém a música correspondente, cria um objeto {@link Reproducao} e
     * notifica os observadores.
     *
     * @return Um objeto {@link Reproducao} contendo a música reproduzida e o estado da reprodução.
     * @throws ColecaoDeMusicaVaziaException se a coleção atual estiver vazia.
     */
    public Reproducao reproduzMusicaAnterior() throws ColecaoDeMusicaVaziaException {
        if (this.colecao == null || this.colecao.getTamanho() == 0) {
            throw new ColecaoDeMusicaVaziaException("A coleção de músicas está vazia ou não foi definida.");
        }
        this.retrocederIndiceAtual();
        Musica musica = colecao.getMusicaPorIndice(indiceAtual); // Pode lançar ColecaoDeMusicaVaziaException
        Reproducao reproducao = new Reproducao(musica);
        notificarObservers(reproducao);
        return reproducao;
    }

    /**
     * Atualiza o {@code indiceAtual} para a próxima música.
     * Se o modo shuffle estiver ativo, escolhe um índice aleatório dentro do tamanho da coleção.
     * Caso contrário, incrementa o índice atual.
     * Se o índice ultrapassar o final da coleção, volta para o início (índice 0).
     * Este método assume que {@code colecao.getTamanho()} é maior que 0.
     */
    public void avançarIndiceAtual(){
        if(this.modoShuffle){
            indiceAtual = random.nextInt(colecao.getTamanho());
        } else {
            indiceAtual++;
        }

        // Loop para o início se o índice atual exceder o tamanho da coleção.
        if(indiceAtual >= colecao.getTamanho()){
            indiceAtual = 0;
        }
    }

    /**
     * Atualiza o {@code indiceAtual} para a música anterior.
     * Se o modo shuffle estiver ativo, escolhe um índice aleatório dentro do tamanho da coleção.
     * Caso contrário, decrementa o índice atual.
     * Se o índice se tornar negativo, vai para o final da coleção.
     * Este método assume que {@code colecao.getTamanho()} é maior que 0.
     */
    public void retrocederIndiceAtual(){
        if(this.modoShuffle){
            indiceAtual = random.nextInt(colecao.getTamanho());
        } else {
            indiceAtual--;
        }

        // Loop para o fim se o índice atual for menor que zero.
        if (indiceAtual < 0){
            indiceAtual = colecao.getTamanho() - 1;
        } 
    }

    /**
     * Define o estado do modo shuffle (aleatório).
     *
     * @param shuffle {@code true} para ativar o modo shuffle, {@code false} para desativar.
     */
    public void setShuffle(boolean shuffle){
        this.modoShuffle = shuffle;
    }

    /**
     * Verifica se o modo shuffle está atualmente ativo.
     *
     * @return {@code true} se o modo shuffle estiver ativo, {@code false} caso contrário.
     */
    public boolean getModoShuffle(){
        return modoShuffle;
    }

    /**
     * Alterna o estado do modo shuffle.
     * Se estiver ativo, desativa-o. Se estiver desativo, ativa-o.
     */
    public void mudarShuffle(){
        setShuffle(!modoShuffle); // Inverte o valor booleano atual
    }

}