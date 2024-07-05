package app.main.GameBot.repositories;

import app.main.GameBot.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByChatId(Long chat_id);
}