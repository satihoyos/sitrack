package co.sitrack.searchword.api.infrastructure.http;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchWordsGames {

    @PostMapping ("/alphabetSoup/")
    public ResponseEntity<?> create (){
        return ResponseEntity.ok (null);
    }

    @GetMapping ("/alphabetSoup/view/{id}")
    public  ResponseEntity<?> visualize () {
        return ResponseEntity.ok (null);
    }

    @GetMapping ("/alphabetSoup/list/{id}")
    public  ResponseEntity<?> visualizeWords () {
        return ResponseEntity.ok (null);
    }

    @PutMapping ("/alphabetSoup/{id}")
    public  ResponseEntity<?> findWord () {
        return ResponseEntity.ok (null);
    }
}
