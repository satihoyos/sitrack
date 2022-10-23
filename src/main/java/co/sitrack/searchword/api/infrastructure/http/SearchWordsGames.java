package co.sitrack.searchword.api.infrastructure.http;

import co.sitrack.searchword.api.application.game.create.CreateSearchWordGame;
import co.sitrack.searchword.shared.domain.SearchWordGame;
import co.sitrack.searchword.shared.domain.SearchWordSetting;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    public SearchWordsGames (CreateSearchWordGame createSearchWordGame){
        this.createSearchWordGame =  createSearchWordGame;
    }

    @PostMapping ()
    public ResponseEntity<?> create (@RequestBody SearchWordSetting settings){
        UUID id = this.createSearchWordGame.build (settings);
        return ResponseEntity.ok (String.format ("{id:\"%s\"}",id));
    }

    @GetMapping ("/view/{id}")
    public  ResponseEntity<?> visualize () {
        return ResponseEntity.ok (null);
    }

    @GetMapping ("/list/{id}")
    public  ResponseEntity<?> visualizeWords () {
        return ResponseEntity.ok (null);
    }

    @PutMapping ("/{id}")
    public  ResponseEntity<?> findWord () {
        return ResponseEntity.ok (null);
    }
}
