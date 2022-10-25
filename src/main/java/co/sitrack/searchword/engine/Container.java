package co.sitrack.searchword.engine;

import co.sitrack.searchword.shared.domain.Position;
import co.sitrack.searchword.shared.domain.Type;
import co.sitrack.searchword.shared.domain.Word;
import lombok.Getter;
import org.apache.commons.text.RandomStringGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class Container {
    private String[][] data;
    private int rows;
    private int columns;

    private static RandomStringGenerator strGen =new RandomStringGenerator
                    .Builder ()
                    .withinRange (new char[][]{{'a', 'z'}}).build ();
    public Container (int rows, int columns) {
        //todo take care of low and hight limits in rows and columns
        this.rows = rows;
        this.columns = columns;
        this.data = new String[rows][columns];
    }

    public void addHorizontalWord (Word word){
        String content = word.getContent ();
        if(Type.HORIZONTAL_REVERTED.equals (word.getType ())) {
            content = this.revertWord (content);
        }

        int row = word.getPosition ().getSr ();
        int column = word.getPosition ().getSc ();

        if (word.getPosition ().getFc () > (this.columns - 1) ||
                row > (this.rows - 1) ||
                row != word.getPosition ().getFr ()){
            return;
        }

        String[] rowS = this.data[row];
        AtomicInteger counter = new AtomicInteger(column);
        content.chars ().forEach (vowel ->
                rowS [counter.getAndIncrement ()] = Character.toString (vowel)
        );
    }

    public void addVerticalWord (Word word){
        String literal = word.getContent ();
        if(Type.VERTICAL_REVERTED.equals (word.getType ())) {
            literal = this.revertWord (literal);
        }

        Position position = word.getPosition ();
        AtomicInteger counter = new AtomicInteger();
        for (int i = position.getSr (); i <= position.getFr (); i++) {
            String[] rowS = data[i];
            if (counter.get () < literal.length ()) {
                rowS[position.getSc ()] = Character.toString (literal.charAt (counter.getAndIncrement ()));
            }
        }
    }

    public void addDiagonalWord (Word w){
        String content = w.getContent ();
        if(Type.DIAGONAL_REVERTED.equals (w.getType ())) {
            content = this.revertWord (content);
        }

        Position position = w.getPosition ();
        int sc = position.getSc ();
        int sr = position.getSr ();
        int contador=0;
        while (sc <= position.getFc () && sr <= position.getFr () ){
            data[sr++][sc++]=Character.toString (content.charAt (contador++));
        }
    }

    public List<String> getScrumbleSearchWords (){
        List<String> scrumbleWords = new ArrayList<> ();
        for (String[] rows : this.data) {
            StringBuffer strB = new StringBuffer ();
            for (int i=0; i< rows.length; i++){
                if (rows[i] == null)
                    rows[i] = strGen.generate (1);
                strB.append (rows[i]);
            }
            scrumbleWords.add (strB.toString ());
        }
        return scrumbleWords;
    }

    //todo: change accessor to private
    public void fillData (){
        for (String[] rows : this.data) {
            for (int i=0; i< rows.length; i++){
                //rows[i] = str_gen.generate (1); //todo create ramdom
                if (rows[i] == null)
                    rows[i] = "*";
            }
        }
    }

    /**
     * This method is just to make tests
     */
    public void print () {
        for (String[] row : this.data) {
            for (String column : row) {
                System.out.print (" "+column);
            }
            System.out.println ();
        }
    }

    private String revertWord (String word){
        return new StringBuilder(word).reverse().toString();
    }
}
