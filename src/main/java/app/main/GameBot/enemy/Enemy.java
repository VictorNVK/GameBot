package app.main.GameBot.enemy;

import app.main.GameBot.models.Player;

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

    public Player attack_talent(Player player){
        return null;
    }

    public void scale(Player player){

    }
}
