package app.main.GameBot.enemy;

import app.main.GameBot.models.Player;
import app.main.GameBot.talent.Talent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Enemy {

    private Integer health;

    private Integer attack;

    private Integer energy;

    private Integer defense;

    private String nameEn;

    private String nameRu;


    public Player attack(Player player, app.main.GameBot.models.Enemy enemy, Talent talent
    ,Integer talentLevel){
        return null;
    }

    public Player attack_talent(Player player, Integer counter, app.main.GameBot.models.Enemy enemy, Talent talent
    ,Integer talentLevel){
        return player;
    }
    public Boolean talent_condition(Player player, Integer counter, app.main.GameBot.models.Enemy enemy){
        return true;
    }

    public Enemy scale(Enemy enemy, Player player){
        return enemy ;
    }

    public app.main.GameBot.models.Enemy talent_price(app.main.GameBot.models.Enemy enemy){
        return enemy;
    }
}
