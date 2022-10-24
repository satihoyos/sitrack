package co.sitrack.searchword.api.application.words.visualize;

import co.sitrack.searchword.api.application.words.visualize.domain.FindWordsGameRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class VisualizeSearchWordList {
    private FindWordsGameRepo findWordsGameRepo;

    public VisualizeSearchWordList (FindWordsGameRepo findWordsGameRepo) {
        this.findWordsGameRepo = findWordsGameRepo;
    }

    public List<String> get (UUID id) {
        return this.findWordsGameRepo.findById (id).stream()
                .map (w -> w.getContent ()).collect(Collectors.toList());
    }
}

