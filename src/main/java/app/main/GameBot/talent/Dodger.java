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
        var energy = 2;
        if(level >=2 && level % 2 == 0) {
            energy = energy + level /2;
        }else {
            energy = energy + level/2;
        }
        return "Шанс уворота: " + miss + "%\uD83D\uDCA8" + " " + " Расход энергии " + energy + "⚡\uFE0F" +
                "\n + Нельзя выключить до конца боя или пока не кончится энергия!";

    }
    public String descriptionEn(app.main.GameBot.models.Talent talent){
        var level = talent.getLevel();
        var miss = 2 * level;
        var energy = 2;
        if(level >=2 && level % 2 == 0) {
            energy = energy + level /2;
        }else {
            energy = energy + level/2;
        }
        return "Dodge chance: " + miss + "%\uD83D\uDCA8" + " " + " Energy consumption " + energy + "⚡\uFE0F" +
                "\n + Cannot be turned off until the end of the battle or until the energy runs out!";
    }
}
