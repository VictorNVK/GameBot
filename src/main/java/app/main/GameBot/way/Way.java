package app.main.GameBot.way;

import app.main.GameBot.talent.Talent;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Way {

    private List<Talent> talents = new ArrayList<>();

    private String nameRu;

    private String nameEn;
}
