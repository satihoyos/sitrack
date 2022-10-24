package co.sitrack.searchword.api.application.words.find.domain;

import java.util.List;
import java.util.UUID;

public interface UpdateGameRepo {
    void updateScrambleWords (List<String> scrambleWords, UUID id);
}
