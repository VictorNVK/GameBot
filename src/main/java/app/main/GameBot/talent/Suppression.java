package app.main.GameBot.talent;

import app.main.GameBot.models.Enemy;
import app.main.GameBot.models.Player;
import lombok.Getter;

@Getter
public class Suppression extends Talent{

    private final String nameRu = "Подавление";

    private final String nameEn = "Suppression";

    private final String type = "defense";

    private final Integer active_time = 2;

    private final Integer unlocked_way_level = 1;

    public app.main.GameBot.models.Enemy action_attack(Enemy enemy, Player player, app.main.GameBot.models.Talent talent){
        return enemy;
    }

    public Integer action_defense(Integer damage, Integer level){
        var block = 20 + (level-1) * 5;
        damage =  damage - (block * (damage/100));
        return damage ;
    }
    public String descriptionRu(app.main.GameBot.models.Talent talent){
        var level = talent.getLevel();
        var block = 20 + (level-1) * 5;
        var energy =  (level) * 5;

        if(level == 0){
            block= 0;
            energy = 0;
        }
        return "Поглощение урона - " + block + "%" + "|" + "Расход энергии - " + energy;
    }
    public String descriptionEn(app.main.GameBot.models.Talent talent){
        var level = talent.getLevel();
        var block = 20 + (level-1) * 5;
        var energy =  (level) * 5;
        if(level == 0){
            block= 0;
            energy = 0;
        }
        return "Suppression damage - " + block + "%" + "|" + "Energy consumption - " + energy;
    }
    public Boolean check_resources(Player player, Integer level){
        var energy = (level) * 5;
        if(player.getEnergyNow() >= energy){
            return true;
        }
        return false;
    }
    public Player action_price(Player player, app.main.GameBot.models.Talent talent){
        var energy = (talent.getLevel() -1) * 5;
        player.setEnergyNow(player.getEnergyNow() - energy);
        return player;
    }
}
