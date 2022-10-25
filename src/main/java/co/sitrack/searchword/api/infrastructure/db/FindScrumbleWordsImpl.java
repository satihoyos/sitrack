package co.sitrack.searchword.api.infrastructure.db;

import co.sitrack.searchword.api.application.game.visualize.domain.FindScrumbleWordsRepo;
import co.sitrack.searchword.shared.domain.SearchWordGame;
import co.sitrack.searchword.shared.exceptions.SearchWordException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class FindScrumbleWordsImpl implements FindScrumbleWordsRepo {

    private MongoTemplate mongo;

    public FindScrumbleWordsImpl (MongoTemplate mongo) {
        this.mongo = mongo;
    }

    @Override
    public List<String> findById (UUID id) {
        Query q = new Query (Criteria.where ("_id").is (id));
        q.fields ().exclude ("solutionWords");
        SearchWordGame game = this.mongo.findOne (q, SearchWordGame.class);
        if (game ==  null)
            throw new SearchWordException (String.format ("Not Found id: %s", id));
        return game.getScrumbleWords ();
    }
}
