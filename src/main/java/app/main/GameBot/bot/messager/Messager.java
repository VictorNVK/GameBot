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
}
