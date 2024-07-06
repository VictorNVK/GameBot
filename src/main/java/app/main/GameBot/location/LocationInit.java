package app.main.GameBot.location;

import app.main.GameBot.models.Item;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
@Getter
public class LocationInit {

    private Location clearing;
    private Location suburb;

    @PostConstruct
    private void create_clearing(){
        var grass = new Item();
        grass.setCount(2);
        grass.setItemNameRu("Простые травы");
        grass.setItemNameEn("Simple herbs");

        var branch = new Item();
        branch.setCount(2);
        branch.setItemNameRu("Ветка");
        branch.setItemNameEn("Branch");

        var leaves = new Item();
        leaves.setCount(2);
        leaves.setItemNameRu("Листья");
        leaves.setItemNameEn("Leaves");


        ArrayList<Item> items = new ArrayList<>();
        items.add(grass);
        items.add(branch);
        items.add(leaves);

        this.clearing = new Location("Поляна","Glade", items, 3);
    }
    @PostConstruct
    private void create_suburb(){
        var stone = new Item();
        stone.setCount(2);
        stone.setItemNameRu("Маленький камень");
        stone.setItemNameEn("Little stone");

        var flask = new Item();
        flask.setCount(1);
        flask.setItemNameRu("Колба");
        flask.setItemNameEn("Flask");

        var core = new Item();
        flask.setCount(1);
        core.setItemNameRu("Малое ядро");
        core.setItemNameEn("Small core");

        ArrayList<Item> items = new ArrayList<>();
        items.add(stone);
        items.add(flask);
        items.add(core);

        this.suburb = new Location("Пригород","Suburb", items, 3);
    }
}
