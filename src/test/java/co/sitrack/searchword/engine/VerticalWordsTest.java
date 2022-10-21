package co.sitrack.searchword.engine;

import co.sitrack.searchword.engine.domain.Position;
import co.sitrack.searchword.engine.domain.Type;
import co.sitrack.searchword.engine.domain.Word;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VerticalWordsTest {

    private static final int ROWS = 15;
    private static final int COLUMNS = 15;
    private static final String[] WORDS = new String[]{"america", "colombia"};

    @Test
    public void add_vertical_word_test () {

        Container container = new Container (ROWS, COLUMNS);
        Positioner p = new Positioner (container);
        List<Word> searchWordsList = new ArrayList<> ();

        for (String word : WORDS) {
            p.populateVerticalWord (word, searchWordsList, Type.VERTICAL);
        }

        assertEquals (searchWordsList.size (), WORDS.length);

        String[][] data = container.getData ();
        searchWordsList.forEach (w -> {
            String literal = w.getContent ();
            Position pos = w.getPosition ();
            assertEquals (data[pos.getSr ()][pos.getSc ()], Character.toString (literal.charAt (0)));
            assertEquals (data[pos.getFr ()][pos.getFc ()], Character.toString (literal.charAt (literal.length () - 1)));
        });

        container.fillData ();
        container.print ();
    }

    @Test
    public void add_vertical_reverted_word_test (){
        String[] words = new String[]{"america","colombia"};

        Container container = new Container (ROWS, COLUMNS);
        Positioner p = new Positioner (container);
        List<Word>listPalabras = new ArrayList<> ();

        for (String word : words) {
            p.populateVerticalWord (word, listPalabras, Type.VERTICAL_REVERTED);
        }

        String[][] data = container.getData ();
        listPalabras.forEach (w -> {
            String literal = w.getContent ();
            Position pos  = w.getPosition ();
            assertEquals (data[pos.getFr ()][pos.getFc ()],Character.toString (literal.charAt (0)));
            assertEquals (data[pos.getSr ()][pos.getSc ()],Character.toString (literal.charAt (literal.length ()-1)));
        });

        container.fillData ();
        container.print ();
    }
}
