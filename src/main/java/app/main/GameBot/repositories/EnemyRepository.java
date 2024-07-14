package app.main.GameBot.repositories;

import app.main.GameBot.models.Enemy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnemyRepository extends JpaRepository<Enemy, Integer> {

}
