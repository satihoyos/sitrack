package co.sitrack.searchword.shared.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@Builder( toBuilder = true )
public class Position {
    private int sr;
    private int sc;
    private int fr;
    private int fc;
}




