package app.main.GameBot.bot.messager;

import lombok.Getter;

@Getter
public abstract class Messager {

    private String get_name;
    private String get_tg_name;
    private String greeting;
    private String menu;

    private String character;
    private String inventory;
    private String actionInLocation;
    private String changeLocation;

    private String characterMenu;
    private String inventoryMenu;
    private String actionInLocationMenu;
    private String changeLocationMenu;

    private String characteristics;
    private String talents;
    private String back;
    private String inDev;

    private String health;
    private String energy;
    private String blood;
    private String attack;
    private String defense;
    private String healthRegeneration;
    private String energyRegeneration;
    private String bloodRegeneration;
    private String level;

    private String inventory_menu;
    private String craftingIngredients;
    private String items;
    private String artifacts;

    private String inventoryIsEmpty;
    private String yourInventory;
}
