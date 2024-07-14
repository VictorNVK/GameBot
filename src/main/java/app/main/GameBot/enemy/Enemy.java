package app.main.GameBot.enemy;

import app.main.GameBot.models.Player;
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


    public Player attack(Player player){
        return null;
    }

    public Player attack_talent(Player player, Integer counter){
        return null;
    }

    public void scale(Player player){

    }

    public app.main.GameBot.models.Enemy toModel(Enemy enemy, app.main.GameBot.models.Enemy enemy_model){
        enemy_model.setAttack(this.attack);
        enemy_model.setEnergy(this.energy);
        enemy_model.setHealth(this.health);
        enemy_model.setDefense(this.defense);
        return enemy_model;
    }
}
