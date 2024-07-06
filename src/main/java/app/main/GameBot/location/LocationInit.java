package app.main.GameBot.location;

import app.main.GameBot.models.Item;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Getter
public class LocationInit {

    private Location clearing;
    private Location suburb;
    private List<Location> locations = new ArrayList<>();

    @PostConstruct
    private void create_clearing(){
        var grass = new Item();
        grass.setCount(2);
        grass.setItemNameRu("Простые травы\uD83C\uDF31");
        grass.setItemNameEn("Simple herbs\uD83C\uDF31");

        var branch = new Item();
        branch.setCount(2);
        branch.setItemNameRu("Ветка\uD83E\uDEB5");
        branch.setItemNameEn("Branch\uD83E\uDEB5");

        var leaves = new Item();
        leaves.setCount(2);
        leaves.setItemNameRu("Листья\uD83C\uDF3F");
        leaves.setItemNameEn("Leaves\uD83C\uDF3F");


        ArrayList<Item> items = new ArrayList<>();
        items.add(grass);
        items.add(branch);
        items.add(leaves);

        this.clearing = new Location("Поляна","Clearing", items, 3);
        locations.add(clearing);
    }
    @PostConstruct
    private void create_suburb(){
        var stone = new Item();
        stone.setCount(2);
        stone.setItemNameRu("Маленький камень\uD83E\uDEA8");
        stone.setItemNameEn("Little stone\uD83E\uDEA8");

        var flask = new Item();
        flask.setCount(1);
        flask.setItemNameRu("Колба\uD83E\uDED9");
        flask.setItemNameEn("Flask\uD83E\uDED9");

        var core = new Item();
        flask.setCount(1);
        core.setItemNameRu("Малое ядро");
        core.setItemNameEn("Small core");

        ArrayList<Item> items = new ArrayList<>();
        items.add(stone);
        items.add(flask);
        items.add(core);

        this.suburb = new Location("Пригород","Suburb", items, 3);
        locations.add(suburb);
    }
}
