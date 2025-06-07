package view.bases;

import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class CampoEntidade<T> {
    private final String pergunta;
    private final Function<String, ?> conversor;
    private final BiConsumer<T, ?> setter; //ver BiConsumer ou se dá para passar para function também

    public <V> CampoEntidade(String pergunta, Function<String, V> conversor, BiConsumer<T, V> setter) {
        this.pergunta = pergunta;
        this.conversor = conversor;
        this.setter = setter;
    }

    public void preencherCampo(T entidade, Scanner scanner) {
        System.out.print(pergunta + ": ");
        String input = scanner.nextLine();
        Object valor = conversor.apply(input);
        ((BiConsumer<T, Object>) setter).accept(entidade, valor);
    }
}
