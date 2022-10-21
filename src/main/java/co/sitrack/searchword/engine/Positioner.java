package co.sitrack.searchword.engine;

import co.sitrack.searchword.engine.domain.Position;
import co.sitrack.searchword.engine.domain.Type;
import co.sitrack.searchword.engine.domain.Word;
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

            short sr=0, cCounter=0, rowCounter=0;
            while (rowCounter<maxRow){

                if (data[rowCounter][rColumn]==null){

                    cCounter++;
                    rowCounter++;
                    if (cCounter == newWord.length ()){

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
                    cCounter=0;
                }
            }

            if (cCounter == newWord.length ()) break;

            columns [columnCounter.getAndIncrement ()]=true;
        }
    }


}
