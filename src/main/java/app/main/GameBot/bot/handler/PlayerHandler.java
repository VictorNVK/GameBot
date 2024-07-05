package app.main.GameBot.bot.handler;

import app.main.GameBot.models.Player;
import app.main.GameBot.models.User;
import org.springframework.stereotype.Component;

@Component
public class PlayerHandler {

    public Player create_player(String name, User user){
        Player player = new Player();
        player.setOwner(user);
        player.setNickname(name);
        player.setLevel(1);
        player.setHealth(20);
        player.setEnergy(10);
        player.setBlood(5);
        player.setAttack(1);
        player.setDefense(0);
        player.setHealthRegeneration(1);
        player.setEnergyRegeneration(1);
        player.setBloodRegeneration(1);

        return player;
    }
}
