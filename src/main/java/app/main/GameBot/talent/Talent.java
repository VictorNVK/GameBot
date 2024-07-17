package app.main.GameBot.talent;

import app.main.GameBot.models.Enemy;
import app.main.GameBot.models.Player;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Talent {

    private String nameRu;

    private String nameEn;

    private String type;

    private Integer active_time;

    private Integer unlocked_way_level;


    public app.main.GameBot.models.Enemy action_attack(Enemy enemy, Player player, app.main.GameBot.models.Talent talent) {
        return enemy;
    }

    public Player action_price(Player player, app.main.GameBot.models.Talent talent) {

        return player;
    }

    public Integer action_defense(Integer damage, Integer talentLevel) {
        return damage;
    }

    public String descriptionRu(app.main.GameBot.models.Talent talent) {
        return "";
    }

    public String descriptionEn(app.main.GameBot.models.Talent talent) {
        return "";
    }

    public Boolean check_resources(Player player, Integer level) {
        return true;
    }
}
