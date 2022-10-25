package co.sitrack.searchword.shared.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchWordSetting {
    Short w;
    Short h;
    boolean ltr;
    boolean rtl;
    boolean ttb;
    boolean btt;
    boolean d;
}
