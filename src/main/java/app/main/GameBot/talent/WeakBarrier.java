package app.main.GameBot.talent;

import app.main.GameBot.enemy.Enemy;
import app.main.GameBot.models.Player;
import lombok.Getter;

@Getter
public class WeakBarrier extends Talent{

    private final String nameRu = "Слабый барьер";

    private final String nameEn = "Weak barrier";

    private final String type = "defense";

    private final Integer active_time = 2;

    private final Integer unlocked_way_level = 1;

    public void action_attack(Enemy enemy, Player player, app.main.GameBot.models.Talent talent){

    }

    public Integer action_defense(Integer damage, Integer level){
        var block = 5 + level * 3;
        damage = damage - block;
        if(damage < 0){
            return 0;
        }
        return damage;
    }

    public String descriptionRu(app.main.GameBot.models.Talent talent){
        var level = talent.getLevel();
        var block = 5 + level * 3;
        var energy = 10 + level *3;
        return "Блок урона - " + block + "|" + "Расход энергии - " + energy;

    }
    public String descriptionEn(app.main.GameBot.models.Talent talent){
        var level = talent.getLevel();
        var block = 5 + level * 3;
        var energy = 10 + level *3;
        return "Damage block - " + block + "|" + "Energy consumption - " + energy;
    }
    public Boolean check_resources(Player player, Integer level){
        var energy = 10 + level *3;
        if(player.getEnergyNow() >= energy){
            return true;
        }
        return false;
    }
    public Player action_price(Player player, app.main.GameBot.models.Talent talent){
        return player;
    }
}
