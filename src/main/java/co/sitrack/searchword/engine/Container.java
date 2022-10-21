package co.sitrack.searchword.engine;

import co.sitrack.searchword.engine.domain.Position;
import co.sitrack.searchword.engine.domain.Type;
import co.sitrack.searchword.engine.domain.Word;
import lombok.Getter;

import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class Container {
    private String[][] data;
    private int rows;
    private int columns;

    public Container (int rows, int columns) {
        //todo take care of low and hight limits in rows and columns
        this.rows = rows;
        this.columns = columns;
        this.data = new String[rows][columns];
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

    public void fillData (){
        for (String[] rows : this.data) {
            for (int i=0; i< rows.length; i++){
                //rows[i] = str_gen.generate (1); //todo create ramdom
                if (rows[i] == null)
                    rows[i] = "*";
            }
        }
    }

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
