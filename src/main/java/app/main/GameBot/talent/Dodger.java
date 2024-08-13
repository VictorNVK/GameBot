package app.main.GameBot.talent;

import app.main.GameBot.models.Enemy;
import app.main.GameBot.models.Player;
import lombok.Getter;
import lombok.Setter;

import java.util.Random;

@Getter
@Setter
public class Dodger extends Talent {

    private String nameRu = "Уклонение";

    private String nameEn = "Dodger";

    private final String type = "defense";

    private final Integer active_time = 99999;

    private final Integer unlocked_way_level = 1;


    public app.main.GameBot.models.Enemy action_attack(Enemy enemy, Player player, app.main.GameBot.models.Talent talent) {
        return enemy;
    }

    public Integer action_defense(Integer damage, Integer level) {
        var miss = 2 * level;
        Random random = new Random();
        int randomNumber = random.nextInt(100) + 1;
        if(randomNumber <= miss){
            damage = 0;
        }
        return damage;
    }

    public String descriptionRu(app.main.GameBot.models.Talent talent) {
        var level = talent.getLevel();
        var miss = 2 * level;
        var energy = 2;
        if (level >= 2 && level % 2 == 0) {
            energy = (energy + level-1) / 2;
        } else {
            energy = (energy + level-1) / 2;
        }
        return "Шанс уворота: " + miss + "%\uD83D\uDCA8" + " " + " Расход энергии " + energy + "⚡\uFE0F" +
                "\n + Нельзя выключить до конца боя или пока не кончится энергия!";

    }

    public String descriptionEn(app.main.GameBot.models.Talent talent) {
        var level = talent.getLevel();
        var miss = 2 * level;
        var energy = 2;
        if (level >= 2 && level % 2 == 0) {
            energy = (energy + level-1) / 2;
        } else {
            energy = (energy + level-1) / 2;
        }
        return "Dodge chance: " + miss + "%\uD83D\uDCA8" + " " + " Energy consumption " + energy + "⚡\uFE0F" +
                "\n + Cannot be turned off until the end of the battle or until the energy runs out!";
    }

    public Boolean check_resources(Player player, Integer level) {
        var energy = 2;
        if (level >= 2 && level % 2 == 0) {
            energy = (energy + level-1) / 2;
        } else {
            energy = (energy + level-1) / 2;
        }
        if (player.getEnergyNow() >= energy) {
            return true;
        }
        return false;
    }
    public Player action_price(Player player, app.main.GameBot.models.Talent talent) {
        var level = talent.getLevel();
        var energy = 2;
        if (level >= 2 && level % 2 == 0) {
            energy = (energy + level-1) / 2;
        } else {
            energy = (energy + level-1) / 2;
        }
        player.setEnergyNow(player.getEnergyNow() - energy);
        return player;
    }
}
