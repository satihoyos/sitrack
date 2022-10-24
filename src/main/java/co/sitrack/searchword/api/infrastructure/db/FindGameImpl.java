package co.sitrack.searchword.api.infrastructure.db;

import co.sitrack.searchword.api.application.words.find.domain.FindGameRepo;
import co.sitrack.searchword.shared.domain.SearchWordGame;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class FindGameImpl implements FindGameRepo {
    private MongoTemplate mongo;

    public FindGameImpl (MongoTemplate mongo) {
        this.mongo = mongo;
    }

    @Override
    public SearchWordGame get (UUID id) {
        Query q = new Query (Criteria.where ("_id").is (id));
        return this.mongo.findOne (q, SearchWordGame.class);
    }
}
