package co.sitrack.searchword.engine;

import co.sitrack.searchword.shared.domain.Position;
import co.sitrack.searchword.shared.domain.Type;
import co.sitrack.searchword.shared.domain.Word;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@RequiredArgsConstructor
public class Positioner {
    @NonNull private Container container;

    public void populateVerticalWord (String newWord, List<Word> words, Type typeWord){

        String[][]data =  this.container.getData ();
        int maxColumn = this.container.getColumns ();
        int maxRow = this.container.getRows ();

        if (!Type.VERTICAL.equals (typeWord) && !Type.VERTICAL_REVERTED.equals (typeWord) ||
                newWord.length () > maxColumn)
            return;

        boolean[]columns= new boolean[maxColumn];
        AtomicInteger columnCounter = new AtomicInteger ();
        words.forEach (w -> {
            if (Type.VERTICAL.equals (w.getType ()) || Type.VERTICAL_REVERTED.equals (w.getType ())){
                columns[w.getPosition ().getSc ()]= true;
                columnCounter.incrementAndGet ();
            }
        });

        Random rand = new Random();

        while (columnCounter.get () < maxColumn) {
            int rColumn = rand.nextInt (maxColumn);
            if (columns[rColumn]){
                continue;
            }

            short sr=0, lettersCounter=0, rowCounter=0;
            while (rowCounter<maxRow){

                if (data[rowCounter][rColumn]==null){
                    lettersCounter++;
                    rowCounter++;
                    if (lettersCounter == newWord.length ()){

                        Position pW = Position.builder ()
                                .sc (rColumn)
                                .sr (sr)
                                .fc (rColumn)
                                .fr (sr+newWord.length () - 1)
                                .build ();

                        Word vWord = Word.builder ()
                                .type (typeWord)
                                .content (newWord)
                                .position (pW)
                                .length (newWord.length ()).build ();

                        words.add (vWord);
                        this.container.addVerticalWord (vWord);
                        break;
                    }
                }else {
                    sr = ++rowCounter;
                    lettersCounter=0;
                }
            }

            columns [columnCounter.getAndIncrement ()]=true;
            if (lettersCounter == newWord.length ()) break;
        }
    }

    public void populateHorizontalWord(String newWord, List<Word> words, Type typeWord){
        String[][]data =  this.container.getData ();
        int maxColumn = this.container.getColumns ();
        int maxRow = this.container.getRows ();

        if (!typeWord.equals (Type.HORIZONTAL) && !typeWord.equals (Type.HORIZONTAL_REVERTED)
                || newWord.length () > maxRow)
            return;

        boolean[]rows = new boolean[maxRow];
        AtomicInteger rowCounter = new AtomicInteger ();
        words.forEach (w -> {
            if (Type.HORIZONTAL.equals (w.getType ()) || Type.HORIZONTAL_REVERTED.equals (w.getType ())){
                rows[w.getPosition ().getSr ()]= true;
                rowCounter.incrementAndGet ();
            }
        });

        Random rand = new Random();

        while (rowCounter.get () < maxRow) {
            int randRow = rand.nextInt (maxRow);
            if (rows[randRow]){
                continue;
            }

            short sc=0, lettersCounter=0, columnCounter=0;
            while (columnCounter<maxColumn){

                if (data[randRow][columnCounter]==null){
                    lettersCounter++;
                    columnCounter++;
                    if (lettersCounter == newWord.length ()){

                        Position pW = Position.builder ()
                                .sc (sc)
                                .sr (randRow)
                                .fc (sc+newWord.length () - 1)
                                .fr (randRow)
                                .build ();

                        Word vWord = Word.builder ()
                                .type (typeWord)
                                .content (newWord)
                                .position (pW)
                                .length (newWord.length ()).build ();

                        words.add (vWord);
                        this.container.addHorizontalWord (vWord);
                        break;
                    }
                }else {
                    sc = ++columnCounter;
                    lettersCounter=0;
                }
            }

            rows [rowCounter.getAndIncrement ()]=true;
            if (lettersCounter == newWord.length ()) break;
        }

    }

    public void populateDiagonalWord (String newWord, List<Word> words, Type typeWord){
        String[][]data =  this.container.getData ();
        int maxColumn = this.container.getColumns ();
        int maxRow = this.container.getRows ();

        if (!Type.DIAGONAL.equals (typeWord) && !Type.DIAGONAL_REVERTED.equals (typeWord) ||
                newWord.length () > maxRow) {
            return;
        }

        int rowAux = 0;
        int columnAux=0;
        int wordPositionSize = newWord.length () - 1;
        for(;;) {
            int finalRow = rowAux + wordPositionSize;
            int finalColumn = columnAux + wordPositionSize;
            int contador = 0;
            boolean isValidWord = true;
            if (finalRow < maxRow && finalColumn < maxColumn) {
                while (isValidWord && rowAux + contador <= finalRow && columnAux <= finalColumn) {
                    isValidWord = data[rowAux + contador][columnAux + contador] == null;
                    contador++;
                }
            }else {
                //finish search because word is out of range the word,
                //or there is no space.
                break;
            }

            if (isValidWord) {
                Position pW = Position.builder ()
                        .sc (columnAux)
                        .sr (rowAux)
                        .fc (finalColumn)
                        .fr (finalRow)
                        .build ();

                Word vWord = Word.builder ()
                        .type (typeWord)
                        .content (newWord)
                        .position (pW)
                        .length (newWord.length ()).build ();

                words.add (vWord);
                this.container.addDiagonalWord (vWord);
                break;
            }

            if (!(++columnAux < maxColumn)){
                rowAux++;
                columnAux = 0;
            }
        }
    }
}
