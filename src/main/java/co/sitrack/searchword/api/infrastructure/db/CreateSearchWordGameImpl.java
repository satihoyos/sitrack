package co.sitrack.searchword.api.infrastructure.db;


import co.sitrack.searchword.api.application.game.create.domain.CreateSearchWordGameRepo;
import co.sitrack.searchword.shared.domain.SearchWordGame;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CreateSearchWordGameImpl implements CreateSearchWordGameRepo {

    private MongoTemplate mongo;

    public CreateSearchWordGameImpl (MongoTemplate mongo) {
        this.mongo = mongo;
    }

    @Override
    public SearchWordGame save (SearchWordGame game) {
        return this.mongo.save (game);
    }
}
