package app.main.GameBot.enemy;

import app.main.GameBot.models.Player;
import app.main.GameBot.talent.Talent;
import lombok.Getter;

@Getter
public class Wolf extends Enemy{

    private Integer health = 10;

    private Integer attack = 1;

    private Integer energy = 10;

    private Integer defense = 0;

    private String nameEn = "Wolf";

    private String nameRu = "Волк";


    public Player attack(Player player, app.main.GameBot.models.Enemy enemy, Talent talent, Integer talentLevel){
        var damage = enemy.getAttack() - player.getDefense();
        if(player.getBarrierNow() > 0) {
            damage = damage - player.getBarrierNow();
            player.setBarrierNow(player.getBarrierNow() - damage);
        }
        if(damage< 0){
            damage = 0;
        }
        if(talent != null) {
            damage = talent.action_defense(damage, talentLevel);
        }
        player.setHealthNow(player.getHealthNow() - damage);
        return player;
    }

    public Boolean talent_condition(Player player, Integer counter, app.main.GameBot.models.Enemy enemy){
        if(counter >=2 && counter % 2 == 0 && enemy.getEnergy() >= 5) {
            return true;
        }
        return false;
    }


    public Player attack_talent(Player player, Integer counter, app.main.GameBot.models.Enemy enemy, Talent talent, Integer talentLevel){
        attack = attack * player.getLevel();
        attack = attack *2;
        attack = talent.action_defense(attack, talentLevel);
            player.setHealthNow(player.getHealthNow() - attack);
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
}
