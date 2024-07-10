package app.main.GameBot.repositories;

import app.main.GameBot.models.Player;
import app.main.GameBot.models.Talent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TalentRepository extends JpaRepository<Talent, Integer> {

    Talent findTalentByPlayerAndName(Player player, String name);

}
