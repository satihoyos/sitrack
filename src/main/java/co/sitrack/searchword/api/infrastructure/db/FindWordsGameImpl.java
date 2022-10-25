package co.sitrack.searchword.api.infrastructure.db;

import co.sitrack.searchword.api.application.words.visualize.domain.FindWordsGameRepo;
import co.sitrack.searchword.shared.domain.SearchWordGame;
import co.sitrack.searchword.shared.domain.Word;
import co.sitrack.searchword.shared.exceptions.SearchWordException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class FindWordsGameImpl implements FindWordsGameRepo {
    private MongoTemplate mongo;

    public FindWordsGameImpl (MongoTemplate mongo) {
        this.mongo = mongo;
    }

    @Override
    public List<Word> findById (UUID id) {
        Query q = new Query (Criteria.where ("_id").is (id));
        q.fields ().exclude ("scrumbleWords");
        SearchWordGame game = this.mongo.findOne (q, SearchWordGame.class);
        if (game ==  null)
            throw new SearchWordException (String.format ("Not Found id: %s", id));
        return game.getSolutionWords ();
    }
}
