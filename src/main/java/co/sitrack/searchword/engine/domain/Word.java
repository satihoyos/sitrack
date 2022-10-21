package co.sitrack.searchword.engine.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder( toBuilder = true )
public class Word {
    private Position position;
    private String content;
    private int length;
    private Type type;
}
