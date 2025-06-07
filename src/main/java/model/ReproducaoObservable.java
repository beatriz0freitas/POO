package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementa o padrão Observer (Observável) para notificações de reprodução.
 * Classes que herdam ou utilizam {@code ReproducaoObservable} podem notificar
 * uma lista de {@link ReproducaoObserver} quando uma reprodução ocorre.
 */
public class ReproducaoObservable  { 

    
    private List<ReproducaoObserver> observers;

    /**
     * Construtor padrão.
     * Inicializa a lista de observadores como um {@link ArrayList} vazio.
     */
    public ReproducaoObservable(){
        this.observers = new ArrayList<>();
    }

    /**
     * Adiciona um observador à lista de observadores.
     * O observador será notificado sobre futuras reproduções.
     *
     * @param observador O {@link ReproducaoObserver} a ser adicionado.
     * Não são permitidos observadores nulos (a ArrayList pode lançar NullPointerException).
     */
    public void adicionaObserver(ReproducaoObserver observador){

            this.observers.add(observador);
        
    }

    /**
     * Remove um observador da lista de observadores.
     * O observador deixará de ser notificado sobre reproduções.
     *
     * @param observador O {@link ReproducaoObserver} a ser removido.
     * Se o observador não estiver na lista, nenhuma ação é tomada.
     */
    public void removerObserver(ReproducaoObserver observador){
        this.observers.remove(observador);
    }

    /**
     * Notifica todos os observadores registados sobre uma nova reprodução.
     * Chama o método {@code update} de cada observador, passando o objeto {@link Reproducao}.
     *
     * @param reproducao O objeto {@link Reproducao} que representa a música reproduzida
     * e o estado da reprodução. Assume-se que {@code reproducao} não é nula.
     */
     public void notificarObservers(Reproducao reproducao){
        this.observers.forEach(o -> o.update(reproducao));
    }
    
}