package app.main.GameBot.talent;

import app.main.GameBot.enemy.Enemy;
import app.main.GameBot.models.Player;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Dodger extends Talent{
    private String nameRu = "Уклонение";

    private String nameEn = "Dodger";

    private void action_attack(Enemy enemy, Player player, app.main.GameBot.models.Talent talent){

    }

    private void action_defense(){

    }

    public String descriptionRu(app.main.GameBot.models.Talent talent){
        var level = talent.getLevel();
        var miss = 2 * level;
        return "Шанс уворота: " + miss + "%\uD83D\uDCA8";
    }
    public String descriptionEn(app.main.GameBot.models.Talent talent){
        var level = talent.getLevel();
        var miss = 2 * level;
        return "Evade chance: " + miss + "%\uD83D\uDCA8";
    }
}
