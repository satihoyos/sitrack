package co.sitrack.searchword.shared.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.UUID;

@Getter
@ToString
@EqualsAndHashCode
@Builder( toBuilder = true )
public class SearchWordGame {
    @Id
    private UUID id;
    private List<Word> solutionWords;
    private List<String> scrumbleWords;
}
