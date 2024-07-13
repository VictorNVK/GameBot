package app.main.GameBot.enemy;

import app.main.GameBot.models.Player;
import lombok.Getter;

@Getter
public class Wolf extends Enemy{

    private Integer health = 10;

    private Integer attack = 1;

    private Integer energy = 10;

    private Integer defense = 0;

    private String nameEn = "Wolf";

    private String nameRu = "Волк";



    public void scale(Player player){
        health = health * player.getLevel();
        attack = 1 + player.getLevel() * attack;
        energy = energy * player.getLevel();
    }

    public Player attack(Player player){
        attack = attack - player.getDefense();
        if(attack < 0){
            attack = 0;
        }
        player.setHealth(player.getHealth() - attack);
        return player;
    }

    public Player attack_talent(Player player, Integer counter){
        if(counter >=2 && counter % 2 == 0) {
            player.setHealth(player.getHealth() - attack *2);
            energy = energy - 5;
        }
        return player;
    }

}
