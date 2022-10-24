package co.sitrack.searchword.api.application.words.find.domain;

import co.sitrack.searchword.shared.domain.SearchWordGame;

import java.util.UUID;

public interface FindGameRepo {
    SearchWordGame get (UUID id);
}
