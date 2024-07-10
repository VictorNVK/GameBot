package app.main.GameBot.location;

import app.main.GameBot.enemy.Enemy;
import app.main.GameBot.models.Item;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;


import java.util.ArrayList;

@Getter
@Setter
public abstract class Location {

    private String nameRu;

    private String nameEn;

    private ArrayList<Item> items = new ArrayList<>();

    private Integer rooms;

    private ArrayList<Enemy> enemy = new ArrayList<>();
}
