package app.main.GameBot.bot.messager;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
public class MessagerEn extends Messager {
    private final String get_name = "Enter player name:";
    private final String get_tg_name = "Use name from Telegram";
    private final String greeting = "Welcome ";
    private final String menu = "Choose a menu item:";

    private final String character = "Character\uD83D\uDC71\u200D♂\uFE0F";
    private final String inventory = "Inventory\uD83D\uDCFC";
    private final String actionInLocation = "Actions in location➡\uFE0F";
    private final String changeLocation = "Change location\uD83D\uDD04";

    private final String characterMenu = "Choose an item in the character menu\uD83D\uDC71\u200D♂\uFE0F";
    private final String inventoryMenu = "Choose an item in the inventory menu\uD83D\uDCFC";
    private final String actionInLocationMenu = "Choose an item in the actions in location menu➡\uFE0F";
    private final String changeLocationMenu = "Choose an item in the change location menu\uD83D\uDD04";

    private final String characteristics = "Characteristics";
    private final String talents = "Talents";
    private final String back = "Back❌";
    private final String inDev = "This function is under development\uD83D\uDEE0";

    private final String health = "Health: ";
    private final String energy = "Energy: ";
    private final String blood = "Blood: ";
    private final String attack ="Attack: ";
    private final String defense= "Defense: ";
    private final String healthRegeneration = "Health regeneration: ";
    private final String energyRegeneration = "Energy regeneration: ";
    private final String bloodRegeneration = "Blood regeneration: ";
    private final String level = "Level: ";

    private final String inventory_menu = "Choose a menu item in the inventory: ";
    private final String craftingIngredients = "Crafting ingredients\uD83C\uDF43";
    private final String items = "Items\uD83E\uDDF3";
    private final String artifacts = "Artifacts✨";
    private final String inventoryIsEmpty = "No crafting ingredients❌";
    private final String yourInventory = "Your crafting ingredients :\n\n";

    private final String location_menu = "Choose a menu item for location exploration";
    private final String locationResearch = "Location exploration\uD83D\uDD0E";
    private final String craft = "Craft(Under development\uD83D\uDEE0)";
    private final String search_await = "Searching for an item, please wait\uD83D\uDD0E";

    private final String yourItemFind = "You found an item: ";
    private final String chooseLocationToChange = "Choose a location for exploration :";
    private final String location_has_been_chosen = "Location chosen, choose an action";
    private final String start_location = "Starting location - Clearing\uD83D\uDFE2";
    private final String training = "Training\uD83D\uDCAA";
    private final String choose_param_of_menu = "Choose a menu item :";
    private final String prof = "Professions" ;
    private final String your_talent_stats = "Your current talent stats : ";
    private final String your_balance = "Your crystal balance : ";
    private final String train_up = "Train";
    private final String little_crystals = "Not enough crystals❌\uD83D\uDC8E";
    private final String talent_is_maxed = "This skill has been improved to the maximum level✅";
    private final String barrier = "Barrier : ";
    private final String up_time = "Skill improvement will take: ";
    private final String await_up = "wait\uD83D\uDD58";
    private final String second = "seconds";
    private final String up_is_delete = "Improvement was canceled!";
    private final String up_branch = "Upgrade branch⬆\uFE0F";
    private final String branch_is_maxed = "Maximum branch level reached!";
    private final String level_is_maxed_for_branch = "Maximum branch level reached for your level!";
    private final String up_time_branch = "Branch improvement will take: ";
    private final String branch_is_up = "Branch has been upgraded, current branch level - ";
    private final String you_under_attack = "You are under attack ";
    private final String you_have_20_second = "You have 20 seconds to choose an action\uD83D\uDD50";
    private final String attacking = "You are being attacked⚔\uFE0F";
    private final String first_enemy_step = "Enemy goes first!";
    private final String enemy_use_attack = "Enemy used regular attack!";
    private final String enemy_use_skill = "Enemy used skill!";
    private final String skills = "Skills⚔\uFE0F";
    private final String actions = "Actions\uD83D\uDCA1";
    private final String evade = "Escape\uD83E\uDDB5";
    private final String player_step = "Your turn\uD83D\uDD58";
    private final String enemy_step = "Enemy's turn\uD83D\uDD58";
    private final String evade_is_successful = "Escape successful✅";
    private final String evade_is_unsuccessful = "Escape failed, battle continues❌";
    private final String player_is_dead = "You died☠\uFE0F";
    private final String ways_not_learned = "You haven't learned any branches!";
    private final String talents_not_learned = "You haven't learned any skills!";
    private final String not_resources = "Not enough resources to use skill!";
    private final String skill_is_choosen = "Skill chosen✅";
    private final String await_your_step = "Action performed, wait for your turn...";
    private final String enemy_stats = "Enemy stats: ";
    private final String enemy_is_dead = "Enemy defeated! Here's your reward\n";
    private final String crystal = "crystal";
    private final String talent_is_not_learned = "This talent is not learned!";
    private final String healing = "Healing\uD83E\uDEE7";
    private final String stop_regeneration = "Stop regeneration\uD83D\uDED1";
    private final String regen_was_stopped = "Regeneration stopped\uD83D\uDED1";
}
