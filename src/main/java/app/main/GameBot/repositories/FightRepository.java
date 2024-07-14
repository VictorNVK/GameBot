package app.main.GameBot.repositories;

import app.main.GameBot.models.Fight;
import app.main.GameBot.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FightRepository extends JpaRepository<Fight, Integer> {

    Fight findByPlayer(Player player);
}
