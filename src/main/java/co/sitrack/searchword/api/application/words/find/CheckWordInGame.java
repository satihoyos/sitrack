package co.sitrack.searchword.api.application.words.find;

import co.sitrack.searchword.api.application.words.find.domain.FindGameRepo;
import co.sitrack.searchword.api.application.words.find.domain.UpdateGameRepo;
import co.sitrack.searchword.shared.domain.Position;
import co.sitrack.searchword.shared.domain.SearchWordGame;
import co.sitrack.searchword.shared.domain.Type;
import co.sitrack.searchword.shared.domain.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CheckWordInGame {
    private FindGameRepo findGameRepo;
    private UpdateGameRepo updateGameRepo;

    @Autowired
    public CheckWordInGame (FindGameRepo findGameRepo, UpdateGameRepo updateGameRepo) {
        this.findGameRepo = findGameRepo;
        this.updateGameRepo = updateGameRepo;
    }

    public boolean validateWord (Position p, UUID id) {
        SearchWordGame game = this.findGameRepo.get (id);
        List<Word> solutionWords = game.getSolutionWords ();
        String word = null;
        for (Word solutionWord : solutionWords) {
            if (p.equals (solutionWord.getPosition ())){
                word =  solutionWord.getContent ();
                Type tipo = solutionWord.getType ();
                if (tipo.equals (Type.HORIZONTAL) || tipo.equals (Type.HORIZONTAL_REVERTED))
                    this.horizontalUpcase (game, solutionWord);
                else
                    this.verticalUpcase (game, solutionWord);
                break;
            }
        }

        game.getScrumbleWords ().forEach (wf -> System.out.println (wf));
        this.updateGameRepo.updateScrambleWords (game.getScrumbleWords (), id);

        if (word == null) {
            return false;
        }

        return true;
    }

    public void horizontalUpcase (SearchWordGame game, Word w){
        Position position = w.getPosition ();
        String word = w.getContent ().toUpperCase ();
        String scrumble = game.getScrumbleWords ().get (position.getSr ());
        char[] chars = scrumble.toCharArray ();
        AtomicInteger counter =
                Type.HORIZONTAL.equals (w.getType ()) ?
                        new AtomicInteger () :
                        new AtomicInteger (word.length () - 1);

        for (int i = 0; i < chars.length; i++) {
            if (i>= position.getSc () && i<= position.getFc ()){
                chars[i]= Type.HORIZONTAL.equals (w.getType ()) ?
                        word.charAt (counter.getAndIncrement ()):
                        word.charAt (counter.getAndDecrement ());
            }
        }

        game.getScrumbleWords ().set (position.getSr (), new String (chars));
    }

    public void verticalUpcase (SearchWordGame game, Word w){
        List<String> scrumbleWords = game.getScrumbleWords ();
        Position p = w.getPosition ();
        String word = w.getContent ().toUpperCase ();
        AtomicInteger counter =
                Type.VERTICAL.equals (w.getType ()) ?
                        new AtomicInteger () :
                        new AtomicInteger (word.length () - 1);

        for (int i = p.getSr (); i <= p.getFr (); i++) {
            String rowWord = scrumbleWords.get (i);
            char[] chars = rowWord.toCharArray ();
            chars[p.getSc ()]= Type.VERTICAL.equals (w.getType ()) ?
                    word.charAt (counter.getAndIncrement ()):
                    word.charAt (counter.getAndDecrement ());
            scrumbleWords.set (i, new String (chars));
        }
    }


}
