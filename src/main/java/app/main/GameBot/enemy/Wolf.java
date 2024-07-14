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



    public Player attack(Player player, app.main.GameBot.models.Enemy enemy){
        var damage = enemy.getAttack() - player.getDefense();
        if(damage< 0){
            damage = 0;
        }
        player.setHealthNow(player.getHealth() - damage);
        return player;
    }

    public Boolean talent_condition(Player player, Integer counter, app.main.GameBot.models.Enemy enemy){
        if(counter >=2 && counter % 2 == 0 && enemy.getEnergy() >= 5) {
            return true;
        }
        return false;
    }



    public Player attack_talent(Player player, Integer counter, app.main.GameBot.models.Enemy enemy){
            player.setHealthNow(player.getHealth() - attack * 2);
        return player;
    }

    public Enemy scale(Enemy enemy, Player player){
        enemy.setHealth(enemy.getHealth() * player.getLevel());
        enemy.setHealth(enemy.getEnergy() * player.getLevel());
        enemy.setHealth(1 + player.getLevel() * enemy.getAttack());
        return enemy;
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
