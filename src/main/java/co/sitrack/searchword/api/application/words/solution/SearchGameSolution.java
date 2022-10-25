package co.sitrack.searchword.api.application.words.solution;

import co.sitrack.searchword.api.application.words.visualize.domain.FindWordsGameRepo;
import co.sitrack.searchword.shared.domain.Word;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SearchGameSolution {

    private FindWordsGameRepo findWordsGameRepo;

    public SearchGameSolution (FindWordsGameRepo findWordsGameRepo) {
        this.findWordsGameRepo = findWordsGameRepo;
    }

    public List<Word> get(UUID id){
        return findWordsGameRepo.findById (id);
    }
}
