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
    private final String talents = "Таланты";
    private final String back = "Назад❌";
    private final String inDev = "Эта функция находиться в разработке\uD83D\uDEE0";

    private final String health = "Здоровье: ";
    private final String energy = "Энергий: ";
    private final String blood = "Кровь: ";
    private final String attack ="Атака: ";
    private final String defense= "Защита: ";
    private final String healthRegeneration = "Регенерация HP: ";
    private final String energyRegeneration = "Регенерация энергии: ";
    private final String bloodRegeneration = "Регенерация крови: ";
    private final String level = "Уровень: ";

    private final String inventory_menu = "Выберите пункт меню инвенторя: ";
    private final String craftingIngredients = "Ингридиенты для крафта\uD83C\uDF43";
    private final String items = "Предметы\uD83E\uDDF3";
    private final String artifacts = "Артефакты✨";
    private final String inventoryIsEmpty = "Нет ингридиентов для крафта❌";
    private final String yourInventory = "Ваши ингриенты для крафта :\n\n";


    private final String location_menu = "Выберите пункт в меню исследование локации";
    private final String locationResearch = "Исследование локации\uD83D\uDD0E";
    private final String craft = "Крафт(В разработке\uD83D\uDEE0)";
    private final String search_await = "Идёт поиск предмета, ожидайте\uD83D\uDD0E";

    private final String yourItemFind = "Вы нашли предмет: ";
    private final String chooseLocationToChange = "Выберите локацию для исследования :";
    private final String location_has_been_chosen = "Локация была выбрана, выберите действие";
    private final String start_location = "Стартовая локация - Поляна\uD83D\uDFE2";
    private final String training = "Тренировка\uD83D\uDCAA";
    private final String choose_param_of_menu = "Выберите пункт меню :";
    private final String prof = "Проффесии" ;
    private final String your_talent_stats = "Текущие харрактеристики вашего таланта : ";
    private final String your_balance = "Ваш баланс кристаллов : ";
    private final String train_up = "Тренировать";
    private final String little_crystals = "Недостаточно кристалов❌\uD83D\uDC8E";

}
