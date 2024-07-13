package app.main.GameBot.repositories;

import app.main.GameBot.models.Player;
import app.main.GameBot.models.Way;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WayRepository extends JpaRepository<Way, Integer> {

    Way findWayByPlayerAndName(Player player, String name);

}
