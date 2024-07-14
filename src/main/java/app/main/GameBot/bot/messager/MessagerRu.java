package app.main.GameBot.bot.messager;

import lombok.Getter;


@Getter
public class MessagerRu extends Messager {
    private final String get_name = "Введите имя игрока:";
    private final String get_tg_name = "Использовать имя из Telegram";
    private final String greeting = "Добро пожаловать ";
    private final String menu = "Выберите пункт меню:";

    private final String character = "Персонаж\uD83D\uDC71\u200D♂\uFE0F";
    private final String inventory = "Инвентарь\uD83D\uDCFC";
    private final String actionInLocation = "Действия в локации➡\uFE0F";
    private final String changeLocation = "Смена локации\uD83D\uDD04";

    private final String characterMenu = "Выберите пункт в меню персонажа\uD83D\uDC71\u200D♂\uFE0F";
    private final String inventoryMenu = "Выберите пункт в меню инвенторя\uD83D\uDCFC";
    private final String actionInLocationMenu = "Выберите пукт в меню действие в локации➡\uFE0F";
    private final String changeLocationMenu = "Выберите пункт в меню смена локации\uD83D\uDD04";

    private final String characteristics = "Характеристики";
    private final String talents = "Таланты";
    private final String back = "Назад❌";
    private final String inDev = "Эта функция находится в разработке\uD83D\uDEE0";

    private final String health = "Здоровье: ";
    private final String energy = "Энергия: ";
    private final String blood = "Кровь: ";
    private final String attack ="Атака: ";
    private final String defense= "Защита: ";
    private final String healthRegeneration = "Регенерация HP: ";
    private final String energyRegeneration = "Регенерация энергии: ";
    private final String bloodRegeneration = "Регенерация крови: ";
    private final String level = "Уровень: ";

    private final String inventory_menu = "Выберите пункт меню инвенторя: ";
    private final String craftingIngredients = "Ингредиенты для крафта\uD83C\uDF43";
    private final String items = "Предметы\uD83E\uDDF3";
    private final String artifacts = "Артефакты✨";
    private final String inventoryIsEmpty = "Нет ингредиентов для крафта❌";
    private final String yourInventory = "Ваши ингредиенты для крафта :\n\n";

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
    private final String prof = "Профессии" ;
    private final String your_talent_stats = "Текущие характеристики вашего таланта : ";
    private final String your_balance = "Ваш баланс кристаллов : ";
    private final String train_up = "Тренировать";
    private final String little_crystals = "Недостаточно кристаллов❌\uD83D\uDC8E";
    private final String talent_is_maxed = "Этот навык улучшен до максимального уровня✅";
    private final String barrier = "Барьер : ";
    private final String up_time = "Прокачка навыка займёт: ";
    private final String await_up = "ожидайте\uD83D\uDD58";
    private final String second = "секунд";
    private final String up_is_delete = "Улучшение было отменено!";
    private final String up_branch = "Улучшить ветку⬆\uFE0F";
    private final String branch_is_maxed = "Достигнут максимальный уровень ветки!";
    private final String level_is_maxed_for_branch = "Достигнут максимальный уровень ветки для вашего уровня!";
    private final String up_time_branch = "Прокачка ветки займёт: ";
    private final String branch_is_up = "Ветка была улучшена, текущий уровень ветки - " ;
    private final String you_under_attack = "Вы были атакованы ";
    private final String you_have_20_second = "У вас 20 секунд что бы выбрать действие\uD83D\uDD50";
    private final String attacking = "Вас атакуют⚔\uFE0F";
    private final String first_enemy_step = "Враг ходит первым!";
    private final String enemy_use_attack = "Враг использовал обычную атаку!";
    private final String enemy_use_skill = "Враг использовал навык!";
}

