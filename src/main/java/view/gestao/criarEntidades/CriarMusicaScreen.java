package view.gestao.criarEntidades;

import model.Album;
import model.musica.Musica;
import view.bases.CampoEntidade;
import view.bases.CriarEntidadeBaseScreen;

import java.util.Arrays;
import java.util.List;

public class CriarMusicaScreen extends CriarEntidadeBaseScreen<Musica, Album> {

    public CriarMusicaScreen(Album album) {
        super("Criar Música", album);
    }

    @Override
    protected Musica novaEntidade() {
        return new Musica();
    }

    @Override
    protected List<CampoEntidade<Musica>> obterCampos() {
        return Arrays.asList(
            new CampoEntidade<>("Nome", input -> input, (m, val) -> m.setNome(val)),
            new CampoEntidade<>("Intérprete", input -> input, (m, val) -> m.setInterprete(val)),
            new CampoEntidade<>("Editora", input -> input, (m, val) -> m.setEditora(val)),
            new CampoEntidade<>("Letra", input -> input, (m, val) -> m.setLetra(val)),
            new CampoEntidade<>("Notas Musicais", input -> input, (m, val) -> m.setNotasMusicais(val)),
            new CampoEntidade<>("Género", input -> input, (m, val) -> m.setGenero(val)),
            new CampoEntidade<>("Duração (segundos)", 
                input -> Integer.parseInt(input.trim()), 
                (m, val) -> m.setDuracaoEmSegundos(val))
        );
    }

    @Override
    protected void processarEntidade(Musica entidade, Album album) {
        
        System.out.println(controller.adicionarMusica(entidade, album));
        this.esperarEnter();
        this.setNextScreen(getPrevious());

    }
}
