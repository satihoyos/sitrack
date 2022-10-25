package co.sitrack.searchword.api.infrastructure.http;

import co.sitrack.searchword.api.application.game.create.CreateSearchWordGame;
import co.sitrack.searchword.api.application.game.visualize.VisualizeSearchWordGame;
import co.sitrack.searchword.api.application.words.find.CheckWordInGame;
import co.sitrack.searchword.api.application.words.visualize.VisualizeSearchWordList;
import co.sitrack.searchword.shared.domain.Position;
import co.sitrack.searchword.shared.domain.SearchWordSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value="/alphabetSoup")
public class SearchWordsGames {

    private CreateSearchWordGame createSearchWordGame;
    private VisualizeSearchWordGame visualizeSearchWordGame;
    private VisualizeSearchWordList visualizeSearchWordList;
    private CheckWordInGame checkWordInGame;

    @Autowired
    public SearchWordsGames (
            CreateSearchWordGame createSearchWordGame,
            VisualizeSearchWordGame visualizeSearchWordGame,
            VisualizeSearchWordList visualizeSearchWordList,
            CheckWordInGame checkWordInGame) {
        this.createSearchWordGame = createSearchWordGame;
        this.visualizeSearchWordGame = visualizeSearchWordGame;
        this.visualizeSearchWordList = visualizeSearchWordList;
        this.checkWordInGame = checkWordInGame;
    }

    public SearchWordsGames (CreateSearchWordGame createSearchWordGame){
        this.createSearchWordGame =  createSearchWordGame;
    }

    @PostMapping (produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<?> create (@RequestBody SearchWordSetting settings){
        UUID id = this.createSearchWordGame.build (settings);
        return ResponseEntity.ok (String.format ("{id:\"%s\"}",id));
    }

    @GetMapping (value = "/view/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
    public  String visualize (@PathVariable UUID id) {
        return this.visualizeSearchWordGame.get (id);
    }

    @GetMapping ("/list/{id}")
    public  ResponseEntity<?> visualizeWords (@PathVariable UUID id) {
        return ResponseEntity.ok (this.visualizeSearchWordList.get (id));
    }

    @PutMapping (value ="/{id}", produces = MediaType.APPLICATION_JSON_VALUE )
    public  ResponseEntity<?> findWord (@RequestBody Position p, @PathVariable UUID id) {
        String respuesta = "{\"mensaje\":\"%s\"}";
        if (this.checkWordInGame.validateWord (p, id))
            respuesta =  String.format (respuesta,"Palabra encontrada");
        else
            respuesta =  String.format (respuesta,"Palabra no existe");

        return ResponseEntity.ok (respuesta);
    }
}
