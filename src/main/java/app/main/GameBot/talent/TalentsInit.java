package app.main.GameBot.talent;

import app.main.GameBot.way.Way;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Getter
public class TalentsInit {

    private final List<Way> waysList = new ArrayList<>();

    private final List<Talent> sword_way = new ArrayList<>();

    private final List<Talent> magic_way = new ArrayList<>();

    private final List<Talent> word_way = new ArrayList<>();

    @PostConstruct
    private void init(){
        Way way = new Way();
        way.setNameEn("Sword way");
        way.setNameRu("Путь меча");
        Slayer slayer = new Slayer();
        Dodger dodger = new Dodger();
        sword_way.add(slayer);
        sword_way.add(dodger);

        way.setTalents(sword_way);


        waysList.add(way);
    }
}
