package co.sitrack.searchword.api.application.game.create;

import co.sitrack.searchword.api.application.game.create.domain.CreateSearchWordGameRepo;
import co.sitrack.searchword.api.infrastructure.db.CreateSearchWordGameImpl;
import co.sitrack.searchword.engine.Container;
import co.sitrack.searchword.engine.Positioner;
import co.sitrack.searchword.shared.domain.SearchWordGame;
import co.sitrack.searchword.shared.domain.SearchWordSetting;
import co.sitrack.searchword.shared.domain.Type;
import co.sitrack.searchword.shared.domain.Word;
import co.sitrack.searchword.shared.exceptions.SearchWordException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static co.sitrack.searchword.shared.domain.Type.DIAGONAL;
import static co.sitrack.searchword.shared.domain.Type.DIAGONAL_REVERTED;
import static co.sitrack.searchword.shared.domain.Type.HORIZONTAL;
import static co.sitrack.searchword.shared.domain.Type.HORIZONTAL_REVERTED;
import static co.sitrack.searchword.shared.domain.Type.VERTICAL;
import static co.sitrack.searchword.shared.domain.Type.VERTICAL_REVERTED;

@Service
public class CreateSearchWordGame {

    private CreateSearchWordGameRepo repo;

    public CreateSearchWordGame (CreateSearchWordGameImpl repo) {
        this.repo = repo;
    }

    private static final int MAX_SIZE = 80;
    private static final int MIN_SIZE = 15;

    private static final String SIZE_MSG_ERROR =
            "Error al crear tamaño de la sopa de letras. tamaño minimo de" +
            "filas y columas %s, tamaño maximo filas y columnas %s. valores" +
            " dados: filas: %s, columnas: %s";

    private static String[] wordsDB = {
            "casa", "benja", "lucas", "queso", "aliens", "television", "leidy",
            "baño", "colegio", "universidad", "desempleo", "chile", "colombia",
            "mama", "papa", "almuerzo", "corredor", "colegio", "pirata", "jardin",
            "telefono", "concurso", "telenovela", "importante", "juguetes", "perro",
            "gato", "raton", "helicoptero", "tierra", "marte", "julio", "diciembre",
            "septiembre", "empleado", "rustico", "tren", "matematicas", "informatico",
            "leon", "jirafa", "tigre", "bebe", "joven", "adulto", "hospital", "metro",
            "cuchillo", "tenedor", "refrigerador", "caminio", "medico", "medias", "zapatos",
            "celular", "mar", "oceano", "asia", "america", "california", "torneo",
            "peliculas", "animacion", "pez", "loro", "cuadrado", "triangulo", "rectangulo",
            "momia", "egipto", "ingeniero", "artista", "navegador", "barco", "te"
    };

    /**
     * if there is no h or w parameter. it will build a search word game with
     * size 15 x 15 .
     *
     * if h or w are less than 15 it will throw SearchWordException
     *
     * if h or w are bigger than 80 it will throw SearchWordException
     *
     * A search word game will be build with words where its letters will fill
     * approximate the 30% of its capacity
     *
     * @param settings
     */
    public UUID build (SearchWordSetting settings){
        short rows = settings.getW () != null ?
                settings.getH ().shortValue () : MIN_SIZE;
        short columns = settings.getH () != null ?
                settings.getH ().shortValue () : MIN_SIZE;

        if (rows < MIN_SIZE || rows > MAX_SIZE ||
                columns < MIN_SIZE || columns > MAX_SIZE)
            throw new SearchWordException (String
                    .format (SIZE_MSG_ERROR, MAX_SIZE, MIN_SIZE, rows, columns));

        int maxLetters= rows * columns * 3/10;
        List<String> wordsList = this.getWordsFromPool (wordsDB, maxLetters);
        Container container = new Container (rows, columns);
        Positioner p = new Positioner (container);
        List<Type> typeList =  new ArrayList<> ();

        if (settings.isLtr ())  typeList.add (HORIZONTAL);
        if (settings.isRtl ())  typeList.add (HORIZONTAL_REVERTED);
        if (settings.isTtb ())  typeList.add (VERTICAL);
        if (settings.isBtt ())  typeList.add (VERTICAL_REVERTED);
        if (settings.isD ())  {
            typeList.add (DIAGONAL);
            typeList.add (DIAGONAL_REVERTED);
        }

        List<Word> searchWordsList = new ArrayList<> ();
        AtomicInteger conterTypes = new AtomicInteger ();
        wordsList.forEach (word -> {
            if (conterTypes.get () >= typeList.size ()) conterTypes.set (0);
            final Type tipo = typeList.get (conterTypes.getAndIncrement ());
            switch (tipo){
                case HORIZONTAL:
                case HORIZONTAL_REVERTED:
                    p.populateHorizontalWord (word, searchWordsList, tipo);
                    break;
                case VERTICAL:
                case VERTICAL_REVERTED:
                    p.populateVerticalWord (word, searchWordsList, tipo);
                    break;
                case DIAGONAL:
                case DIAGONAL_REVERTED:
                    p.populateDiagonalWord (word, searchWordsList, tipo);
            }
        });

        List<String> scrumbleWords = container.getScrumbleSearchWords ();
        UUID id = UUID.randomUUID ();
        SearchWordGame game = SearchWordGame.builder ()
                .solutionWords (searchWordsList)
                .id (id)
                .scrumbleWords (scrumbleWords).build ();

        repo.save (game);
        scrumbleWords.forEach (sword -> System.out.println (sword));
        return  id;
    }

    /**
     * return a random list from wordsPool where the sum of its letters
     * approximate to maxLetter
     *
     * @param wordsPool array that contains a list of words
     * @param maxLetter minimum letter
     * @return random word list from wordsPool
     */
    private List<String> getWordsFromPool (String[] wordsPool, int maxLetter) {
        List<String> words = new ArrayList<> ();
        Random rand = new Random ();
        String[] wordsAux = Arrays.copyOf (wordsPool, wordsPool.length);

        while (maxLetter > 0) {
            int n = rand.nextInt (wordsPool.length);
            if (wordsAux[n] == null) {
                continue;
            }
            words.add (wordsAux[n]);
            maxLetter -= wordsAux[n].length ();
            wordsAux[n] = null;
        }

        return words;
    }

}
