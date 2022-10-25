package co.sitrack.searchword.api.infrastructure.http;

import co.sitrack.searchword.shared.exceptions.SearchWordException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(SearchWordException.class)
    public ResponseEntity<?> SearchWordException(SearchWordException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .header ("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .body(String.format ("{\"mensaje\":\"%s\"}", ex.getMessage ()));
    }
}
