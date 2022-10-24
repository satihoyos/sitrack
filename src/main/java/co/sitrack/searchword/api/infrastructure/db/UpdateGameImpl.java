package co.sitrack.searchword.api.infrastructure.db;

import co.sitrack.searchword.api.application.words.find.domain.UpdateGameRepo;
import co.sitrack.searchword.shared.domain.SearchWordGame;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class UpdateGameImpl implements UpdateGameRepo {
    private MongoTemplate mongo;

    public UpdateGameImpl (MongoTemplate mongo) {
        this.mongo = mongo;
    }

    @Override
    public void updateScrambleWords (List<String> scrambleWords, UUID id) {
        Query q = new Query (Criteria.where ("_id").is (id));
        Update update = Update.update ("scrumbleWords", scrambleWords);
        this.mongo.updateFirst (q, update, SearchWordGame.class);
    }
}
