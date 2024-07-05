package app.main.GameBot.bot.messager;

import lombok.Getter;

@Getter
public class MessagerRu extends Messager {
    //Текст для сообщений
    private final String get_name = "Введите имя игрока:";
    private final String get_tg_name = "Использовать имя из telegram";
    private final String greeting = "Добро пожаловать ";
    private final String menu = "Выберите пунк меню:";


    //Текст для клавиатуры
    private final String character = "Персонаж\uD83D\uDC71\u200D♂\uFE0F";
    private final String inventory = "Инвентарь\uD83D\uDCFC";
    private final String actionInLocation = "Действия в локации➡\uFE0F";
    private final String changeLocation = "Смена локации\uD83D\uDD04";

    private final String characterMenu = "Выберите пункт в меню персонажа\uD83D\uDC71\u200D♂\uFE0F";
    private final String inventoryMenu = "Выберите пункт в меню инвенторя\uD83D\uDCFC";
    private final String actionInLocationMenu = "Выберите пукт в меню действие в локации➡\uFE0F";
    private final String changeLocationMenu = "Выберите пункт в меню смна локации\uD83D\uDD04";

    private final String characteristics = "Харрактеристики";
    private final String talents = "Таланты(В разработке\uD83D\uDEE0)";
    private final String back = "Назад❌";
}
