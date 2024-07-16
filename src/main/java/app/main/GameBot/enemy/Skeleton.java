package app.main.GameBot.enemy;

import app.main.GameBot.models.Player;
import app.main.GameBot.talent.Talent;

public class Skeleton {

    private Integer health;

    private Integer attack;

    private Integer energy;

    private Integer defense;

    private String nameEn;

    private String nameRu;


    public Player attack(Player player, app.main.GameBot.models.Enemy enemy, Talent talent){
        return null;
    }

    public Player attack_talent(Player player, Integer counter, app.main.GameBot.models.Enemy enemy, Talent talent){
        return null;
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

    public app.main.GameBot.models.Enemy toModel(Enemy enemy, app.main.GameBot.models.Enemy enemy_model){
        enemy_model.setAttack(this.attack);
        enemy_model.setEnergy(this.energy);
        enemy_model.setHealth(this.health);
        enemy_model.setDefense(this.defense);
        return enemy_model;
    }
}
