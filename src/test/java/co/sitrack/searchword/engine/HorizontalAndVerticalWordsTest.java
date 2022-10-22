package co.sitrack.searchword.engine;

import co.sitrack.searchword.shared.domain.Position;
import co.sitrack.searchword.shared.domain.Type;
import co.sitrack.searchword.shared.domain.Word;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static co.sitrack.searchword.shared.domain.Type.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HorizontalAndVerticalWordsTest {
    private static final int ROWS = 15;
    private static final int COLUMNS = 15;

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

    @Test
    public void add_vertical_and_vertical_reverted_and_horizontal_horizontal_reverted_word_test (){
        //get near the 30% of letters in words to the search word
        int maxLetters= ROWS * COLUMNS * 3/10;
        List<String> wordsFromPool = getWordsFromPool (wordsDB, maxLetters);
        Container container = new Container (ROWS, COLUMNS);
        Positioner p = new Positioner (container);
        Type[] tipos = new Type[]{HORIZONTAL, HORIZONTAL_REVERTED, VERTICAL, VERTICAL_REVERTED};
        AtomicInteger counter = new AtomicInteger ();
        List<Word>searchWordsList = new ArrayList<> ();
        wordsFromPool.forEach (word ->{
            Type tipo = tipos[counter.getAndIncrement ()];
            if (counter.get ()==tipos.length) counter.set (0);
            if(HORIZONTAL.equals (tipo) || HORIZONTAL_REVERTED.equals (tipo))
                p.populateHorizontalWord (word, searchWordsList, tipo);
            else
                p.populateVerticalWord (word, searchWordsList, tipo);
        });

        String[][] data = container.getData ();
        searchWordsList.forEach (w ->{
            Position pos = w.getPosition ();
            String content = w.getContent ();
            switch (w.getType ()){
                case HORIZONTAL:
                case VERTICAL:
                    assertEquals (data[pos.getSr ()][pos.getSc ()], Character.toString (content.charAt (0)));
                    assertEquals (data[pos.getFr ()][pos.getFc ()], Character.toString (content.charAt (w.getLength () - 1)));
                    break;
                case HORIZONTAL_REVERTED:
                case VERTICAL_REVERTED:
                    assertEquals (data[pos.getFr ()][pos.getFc ()], Character.toString (content.charAt (0)));
                    assertEquals (data[pos.getSr ()][pos.getSc ()], Character.toString (content.charAt (w.getLength () - 1)));
                    break;
            }
        });

        container.fillData ();
        container.print ();
    }

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
