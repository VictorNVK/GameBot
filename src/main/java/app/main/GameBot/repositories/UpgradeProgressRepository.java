package app.main.GameBot.repositories;

import app.main.GameBot.models.Player;
import app.main.GameBot.models.UpgradeProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpgradeProgressRepository extends JpaRepository<UpgradeProgress, Integer> {

    UpgradeProgress findUpgradeProgressByPlayer(Player player);
}
