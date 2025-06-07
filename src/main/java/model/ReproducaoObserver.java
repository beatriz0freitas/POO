package model;

import java.io.Serializable; // Importa a interface Serializable

/**
 * Define a interface para um observador de reproduções.
 * Classes que implementam esta interface podem ser registadas com um
 * {@link ReproducaoObservable} para serem notificadas quando uma nova
 * {@link Reproducao} ocorre.
 *
 * Esta interface é parte do padrão de design Observer, onde ela representa
 * o "Observer".
 */
public interface ReproducaoObserver extends Serializable { // Estende Serializable se os observadores precisarem ser persistidos

    /**
     * Método chamado pelo {@link ReproducaoObservable} quando uma nova reprodução ocorre.
     * Os implementadores desta interface devem definir a lógica a ser executada
     * em resposta à notificação de uma reprodução.
     *
     * @param reproducao O objeto {@link Reproducao} contendo informações sobre a
     * música reproduzida e o estado da reprodução.
     */
    void update(Reproducao reproducao);
}