package app.main.GameBot.bot.messager;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class MessagerEn extends Messager {

    private final String get_name = "Enter player name:";
    private final String get_tg_name = "Use name from Telegram";
    private final String greeting = "Welcome ";
    private final String menu = "Select a menu item:";

    private final String character = "Character\uD83D\uDC71\u200D♂\uFE0F";
    private final String inventory = "Inventory\uD83D\uDCFC";
    private final String actionInLocation = "Actions in location➡\uFE0F";
    private final String changeLocation = "Change location\uD83D\uDD04";

    private final String characterMenu = "Select an item in the character menu\uD83D\uDC71\u200D♂\uFE0F";
    private final String inventoryMenu = "Select an item in the inventory menu\uD83D\uDCFC";
    private final String actionInLocationMenu = "Select an item in the actions in location menu➡\uFE0F";
    private final String changeLocationMenu = "Select an item in the change location menu\uD83D\uDD04";

    private final String characteristics = "Characteristics";
    private final String talents = "Talents (Under development\uD83D\uDEE0)";
    private final String back = "Back❌";
    private final String inDev = "This feature is under development\uD83D\uDEE0";

    private final String health = "Health: ";
    private final String energy = "Energy: ";
    private final String blood = "Blood: ";
    private final String attack ="Attack: ";
    private final String defense= "Defense: ";
    private final String healthRegeneration = "Health regeneration: ";
    private final String energyRegeneration = "Energy regeneration: ";
    private final String bloodRegeneration = "Blood regeneration: ";
    private final String level = "Level: ";

    private final String inventory_menu = "Select an inventory menu item: ";
    private final String craftingIngredients = "Crafting ingredients\uD83C\uDF43";
    private final String items = "Items\uD83E\uDDF3";
    private final String artifacts = "Artifacts✨";
    private final String inventoryIsEmpty = "No crafting ingredients❌";
    private final String yourInventory = "Your crafting ingredients :\n\n";

    private final String location_menu = "Select an item in the location exploration menu";
    private final String locationResearch = "Location exploration\uD83D\uDD0E";
    private final String craft = "Craft (Under development\uD83D\uDEE0)";
    private final String search_await = "Searching for an item, please wait\uD83D\uDD0E";

    private final String yourItemFind = "You found an item: ";
    private final String chooseLocationToChange = "Select a location for exploration:";
    private final String location_has_been_chosen = "Location selected, choose an action";

}
