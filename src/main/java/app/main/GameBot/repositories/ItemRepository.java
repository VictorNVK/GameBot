package app.main.GameBot.repositories;

import app.main.GameBot.models.Item;
import app.main.GameBot.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

    List<Item> findItemsByPlayer(Player player);

    Item findItemByItemNameEnAndPlayer(String name, Player player);
}
