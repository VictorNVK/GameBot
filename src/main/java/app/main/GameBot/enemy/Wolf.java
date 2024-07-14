package app.main.GameBot.enemy;

import app.main.GameBot.models.Player;
import app.main.GameBot.repositories.EnemyRepository;
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
        player.setHealthNow(player.getHealth() - attack);
        return player;
    }

    public Player attack_talent(Player player, Integer counter, app.main.GameBot.models.Enemy enemy){
        if(counter >=2 && counter % 2 == 0) {
            player.setHealthNow(player.getHealth() - attack *2);
        }else {
            return attack(player);
        }
        return player;
    }
    public app.main.GameBot.models.Enemy talent_price(app.main.GameBot.models.Enemy enemy){
        enemy.setEnergy(enemy.getEnergy() -5);
        return enemy;
    }

    public app.main.GameBot.models.Enemy toModel(Enemy enemy, app.main.GameBot.models.Enemy enemy_model){
        enemy_model.setAttack(this.attack);
        enemy_model.setEnergy(this.energy);
        enemy_model.setHealth(this.health);
        enemy_model.setDefense(this.defense);
        return enemy_model;
    }

}
