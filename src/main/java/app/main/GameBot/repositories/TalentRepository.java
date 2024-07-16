package app.main.GameBot.repositories;

import app.main.GameBot.models.Player;
import app.main.GameBot.models.Talent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TalentRepository extends JpaRepository<Talent, Integer> {

    Talent findTalentByPlayerAndName(Player player, String name);

    List<Talent> findTalentsByPlayer(Player player);

    List<Talent> findTalentsByPlayerAndActive(Player player, Boolean active);

}
