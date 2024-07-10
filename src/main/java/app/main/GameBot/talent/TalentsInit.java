package app.main.GameBot.talent;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Getter
public class TalentsInit {

    private final List<Talent> talents = new ArrayList<>();

    @PostConstruct
    private void init(){
        Slayer slayer = new Slayer();

        talents.add(slayer);
    }
}
