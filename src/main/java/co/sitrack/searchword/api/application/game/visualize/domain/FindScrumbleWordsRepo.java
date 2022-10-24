package co.sitrack.searchword.api.application.game.visualize.domain;

import co.sitrack.searchword.shared.domain.SearchWordGame;

import java.util.List;
import java.util.UUID;

public interface FindScrumbleWordsRepo {
    List<String> findById (UUID id);
}
