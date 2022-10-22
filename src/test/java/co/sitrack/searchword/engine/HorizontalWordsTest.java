package co.sitrack.searchword.engine;

import co.sitrack.searchword.engine.domain.Position;
import co.sitrack.searchword.engine.domain.Type;
import co.sitrack.searchword.engine.domain.Word;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HorizontalWordsTest {

    private static final int ROWS = 15;
    private static final int COLUMNS = 15;

    private static final String[] WORDS = new String[]{
            "america", "colombia", "tigre", "telefono", "momia", "ingeniero",
            "medico", "aliens", "bebe", "telenovela"
    };

    @Test
    public void add_horizontal_words_test () {
        Container container = new Container (ROWS, COLUMNS);
        Positioner p = new Positioner (container);
        List<Word> listPalabras = new ArrayList<> ();

        for (String word : WORDS) {
            p.populateHorizontalWord (word, listPalabras, Type.HORIZONTAL);
        }

        assertEquals (WORDS.length, listPalabras.size ());

        listPalabras.stream ().forEach (word -> {
            String[][] data = container.getData ();
            String literal = word.getContent ();
            Position pos = word.getPosition ();
            assertEquals (data[pos.getSr ()][pos.getSc ()], Character.toString (literal.charAt (0)));
            assertEquals (data[pos.getFr ()][pos.getFc ()], Character.toString (literal.charAt (word.getLength () - 1)));
        });

        container.fillData ();
        container.print ();
    }

    @Test
    public void add_horizontal_inverted_words_test () {
        Container container = new Container (ROWS, COLUMNS);
        Positioner p = new Positioner (container);
        List<Word> listPalabras = new ArrayList<> ();

        for (String word : WORDS) {
            p.populateHorizontalWord (word, listPalabras, Type.HORIZONTAL_REVERTED);
        }

        assertEquals (WORDS.length, listPalabras.size ());
        listPalabras.stream ().forEach (word -> {
            String[][] data = container.getData ();
            String literal = word.getContent ();
            Position pos = word.getPosition ();
            assertEquals (data[pos.getFr ()][pos.getFc ()], Character.toString (literal.charAt (0)));
            assertEquals (data[pos.getSr ()][pos.getSc ()], Character.toString (literal.charAt (word.getLength () - 1)));
        });

        container.fillData ();
        container.print ();
    }

    @Test
    public void add_horizonatal_words_and_horizontal_inverted_words_test () {
        int halfSize = WORDS.length / 2;
        Container container = new Container (ROWS, COLUMNS);
        Positioner p = new Positioner (container);
        List<Word> listPalabras = new ArrayList<> ();

        AtomicInteger counter = new AtomicInteger ();
        for (String word : WORDS) {
            Type t = Type.HORIZONTAL;
            if (counter.getAndIncrement () < halfSize) {
                t = Type.HORIZONTAL_REVERTED;
            }
            p.populateHorizontalWord (word, listPalabras, t);
        }

        assertEquals (WORDS.length, listPalabras.size ());
        listPalabras.stream ().forEach (word -> {
            String[][] data = container.getData ();
            String literal = word.getContent ();
            Position pos = word.getPosition ();
            if (Type.HORIZONTAL.equals (word.getType ())) {
                assertEquals (data[pos.getSr ()][pos.getSc ()], Character.toString (literal.charAt (0)));
                assertEquals (data[pos.getFr ()][pos.getFc ()], Character.toString (literal.charAt (word.getLength () - 1)));
            } else {
                assertEquals (data[pos.getFr ()][pos.getFc ()], Character.toString (literal.charAt (0)));
                assertEquals (data[pos.getSr ()][pos.getSc ()], Character.toString (literal.charAt (word.getLength () - 1)));
            }
        });

        container.fillData ();
        container.print ();
    }

}
