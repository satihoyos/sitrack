package co.sitrack.searchword.shared.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@Builder(toBuilder = true )
public class Word {
    private Position position;
    private String content;
    private int length;
    private Type type;
}
