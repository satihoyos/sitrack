package co.sitrack.searchword.api.application.game.create.domain;

import co.sitrack.searchword.shared.domain.SearchWordGame;

public interface CreateSearchWordGameRepo {
    SearchWordGame save (SearchWordGame game);
}
