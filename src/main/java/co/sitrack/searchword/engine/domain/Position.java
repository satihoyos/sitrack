package co.sitrack.searchword.engine.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder( toBuilder = true )
public class Position {
    private int sr;
    private int sc;
    private int fr;
    private int fc;
}




