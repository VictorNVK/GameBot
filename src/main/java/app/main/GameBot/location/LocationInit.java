package app.main.GameBot.location;

import app.main.GameBot.models.Item;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Getter
public class LocationInit {

    private Location clearing;
    private Location suburb;
    private final List<Location> locations = new ArrayList<>();

    @PostConstruct
    private void init_locations(){
        /*Поляна*/
        Clearing clearing = new Clearing();
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

        clearing.getItems().add(grass);
        clearing.getItems().add(branch);
        clearing.getItems().add(leaves);
        locations.add(clearing);
        this.clearing = clearing;

        /*Пригород*/
        Suburb suburb = new Suburb();
        var stone = new Item();
        stone.setCount(2);
        stone.setItemNameRu("Маленький камень\uD83E\uDEA8");
        stone.setItemNameEn("Little stone\uD83E\uDEA8");

        var flask = new Item();
        flask.setCount(1);
        flask.setItemNameRu("Колба\uD83E\uDED9");
        flask.setItemNameEn("Flask\uD83E\uDED9");

        var core = new Item();
        core.setCount(1);
        core.setItemNameRu("Малое ядро");
        core.setItemNameEn("Small core");

        suburb.getItems().add(stone);
        suburb.getItems().add(flask);
        suburb.getItems().add(core);

        this.suburb = suburb;
        locations.add(suburb);
    }
}
