package co.sitrack.searchword.api.application.game.visualize;

import co.sitrack.searchword.api.application.game.visualize.domain.FindScrumbleWordsRepo;
import co.sitrack.searchword.shared.domain.SearchWordGame;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VisualizeSearchWordGame {

    private FindScrumbleWordsRepo findScrumbleWords;

    public VisualizeSearchWordGame (FindScrumbleWordsRepo findScrumbleWords) {
        this.findScrumbleWords = findScrumbleWords;
    }

    public String get (UUID id) {
        List<String> scrumbleWords= findScrumbleWords.findById (id);
        StringBuffer strB = new StringBuffer ();
        scrumbleWords.forEach (word ->{
            word.chars ().forEach (letter ->
                    strB.append ("|").append (Character.toString (letter)));
            strB.append ("|").append ("\n");
        });
        String s = strB.toString ();
        return s;
    }
}
