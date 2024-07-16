package app.main.GameBot.talent;

import app.main.GameBot.models.Enemy;
import app.main.GameBot.models.Player;
import lombok.Getter;

@Getter
public class MagicShot extends Talent {

    private final String nameRu = "Стихийный удар";

    private final String nameEn = "Elemental Strike";

    private final String type = "attack";

    private final Integer active_time = 1;

    private final Integer unlocked_way_level = 1;

    public app.main.GameBot.models.Enemy action_attack(Enemy enemy, Player player, app.main.GameBot.models.Talent talent){
        var enemyDefense = enemy.getDefense();
        var level = talent.getLevel();
        var damage = 2 + (level * 1);
        var attack = player.getAttack() + damage - enemyDefense;
        enemy.setHealth(enemy.getHealth() - attack);
        return enemy;
    }

    public Integer action_defense(Integer damage, Integer level){
        return damage ;
    }
    public String descriptionRu(app.main.GameBot.models.Talent talent){
        var level = talent.getLevel();
        var damage = 2 + (level * 1);
        var energy = 5;
        if(level >=2 && level % 2 == 0) {
            energy = energy + level /2;
        }else {
            energy = energy + level/2;
        }
        return "Стихийный удар: атака +"+ damage + ", расход " + energy + " энергии⚡\uFE0F";
    }
    public String descriptionEn(app.main.GameBot.models.Talent talent){
        var level = talent.getLevel();
        var damage = level * 2;
        var energy = 5 * level;
        return "Elemental strike: attack +\"+ damage + \", consumption\" + energy + \"energy\";";
    }
    public Boolean check_resources(Player player, app.main.GameBot.models.Talent talent){

        return true;
    }
}
