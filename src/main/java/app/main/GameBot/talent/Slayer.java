package app.main.GameBot.talent;

import app.main.GameBot.enemy.Enemy;
import app.main.GameBot.models.Player;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Slayer extends Talent {

    private String nameRu = "Разрез";

    private String nameEn = "Slayer";

    private void action_attack(Enemy enemy, Player player, app.main.GameBot.models.Talent talent){

    }

    private void action_defense(){

    }
    private void scale(app.main.GameBot.models.Talent talent){

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
        return "Разрез: атака +"+ damage + ", расход " + energy + " энергии";
    }
    public String descriptionEn(app.main.GameBot.models.Talent talent){
        var level = talent.getLevel();
        var damage = level * 2;
        var energy = 5 * level;
        return "\"Slay: attack +\"+ damage + \", consumption\" + energy + \"energy\";";
    }
}
