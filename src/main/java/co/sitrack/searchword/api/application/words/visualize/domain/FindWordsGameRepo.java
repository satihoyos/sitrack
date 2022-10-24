package co.sitrack.searchword.api.application.words.visualize.domain;

import co.sitrack.searchword.shared.domain.Word;

import java.util.List;
import java.util.UUID;

public interface FindWordsGameRepo {
    List<Word> findById (UUID id);
}
