package app.main.GameBot.location;

import app.main.GameBot.enemy.Enemy;
import app.main.GameBot.models.Item;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Getter
@Setter
@Component
public class Suburb extends Location{
    private String nameRu = "Пригород";

    private String nameEn = "Suburb";

    private ArrayList<Item> items = new ArrayList<>();

    private Integer rooms = 3;

    private ArrayList<Enemy> enemy = new ArrayList<>();
}
