package app.main.GameBot.talent;

import app.main.GameBot.enemy.Enemy;
import app.main.GameBot.models.Player;
import lombok.Getter;

@Getter
public class Suppression extends Talent{

    private final String nameRu = "Подавление";

    private final String nameEn = "Suppression";

    private final String type = "defense";

    private final Integer active_time = 2;

    private final Integer unlocked_way_level = 1;

    public void action_attack(Enemy enemy, Player player, app.main.GameBot.models.Talent talent){

    }

    public Integer action_defense(Integer damage, Integer level){
        return damage ;
    }
    public String descriptionRu(app.main.GameBot.models.Talent talent){
        var level = talent.getLevel();
        var block = 20 + level * 5;
        var energy =  level * 5;

        return "Поглощение урона - " + block + "%" + "|" + "Расход энергии - " + energy;
    }
    public String descriptionEn(app.main.GameBot.models.Talent talent){
        var level = talent.getLevel();
        var block = 20 + level * 5;
        var energy =  level * 5;
        return "Suppression damage - " + block + "%" + "|" + "Energy consumption - " + energy;
    }
    public Boolean check_resources(Player player, app.main.GameBot.models.Talent talent){
        return true;
    }
}
