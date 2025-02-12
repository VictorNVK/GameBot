package app.main.GameBot.bot.service;

import app.main.GameBot.bot.handler.InventoryHandler;
import app.main.GameBot.bot.handler.LocationHandler;
import app.main.GameBot.bot.handler.MenuHandler;
import app.main.GameBot.bot.handler.PlayerHandler;
import app.main.GameBot.models.Player;
import app.main.GameBot.models.User;
import app.main.GameBot.other.Logger;
import app.main.GameBot.repositories.ItemRepository;
import app.main.GameBot.repositories.PlayerRepository;
import app.main.GameBot.repositories.UserRepository;
import app.main.GameBot.states.UserState;
import app.main.GameBot.talent.Talent;
import app.main.GameBot.talent.TalentsInit;
import app.main.GameBot.way.Way;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Getter
public class PlayerService {

    private final PlayerHandler playerHandler;
    private final MenuHandler menuHandler;
    private final InventoryHandler inventoryHandler;
    private final Logger logger;
    private final ItemRepository itemRepository;
    private final LocationHandler locationHandler;
    private final PlayerRepository playerRepository;
    private User _user;
    private Player _player;
    private final TalentsInit talentsInit;
    private final UserRepository userRepository;


    public List<BotApiMethodMessage> callback_menu_handle(Update update, User user, Player player){
        this._user = user;
        this._player = player;
        List<BotApiMethodMessage> messages = new ArrayList<>();
        var callback = update.getCallbackQuery().getData();
        var chatId = update.getCallbackQuery().getFrom().getId();

        if (callback.equals("character")) {
            messages.add(playerHandler.character_menu(chatId, user.getLanguage()));
            logger.log(player.getNickname(), user.getId(), "выбрал пункт меню",
                    "персонаж");
            return messages;
        }

        if (callback.startsWith("characteristics")) {
            messages.add(playerHandler.sendCharacteristics(chatId, user.getLanguage(), player));
            logger.log(player.getNickname(), user.getId(), "выбрал пункт меню персонажа",
                    "харрактеристика");
            return messages;
        }

        if(callback.startsWith("training")){
            messages.add(playerHandler.train_menu(chatId, user.getLanguage()));
            logger.log(player.getNickname(), user.getId(), "выбрал пункт меню персонажа",
                    "тренировка");
            return messages;
        }
        /**/

        if(callback.startsWith("prof_up")){
            messages.add(menuHandler.in_dev(chatId, user.getLanguage()));
            return messages;
        }
        /**/
        if (callback.startsWith("inventory")) {
            messages.add(inventoryHandler.inventory_menu(chatId, user.getLanguage()));
            logger.log(player.getNickname(), user.getId(), "выбрал пункт меню",
                    "инвентарь");
            return messages;
        }
        if (callback.startsWith("items")) {
            messages.add(menuHandler.in_dev(chatId, user.getLanguage()));
            logger.log(player.getNickname(), user.getId(), "выбрал пункт меню инвенторя",
                    "Предметы");
            return messages;
        }
        if (callback.startsWith("ingredients")) {
            var items = itemRepository.findItemsByPlayer(player);
            messages.add(inventoryHandler.ingredients(chatId, user.getLanguage(), items));
            return messages;
        }
        if (callback.startsWith("artifacts")) {
            messages.add(menuHandler.in_dev(chatId, user.getLanguage()));
            logger.log(player.getNickname(), user.getId(), "выбрал пункт меню инвенторя",
                    "Артефакты");
            return messages;
        }
        if (callback.startsWith("action")) {
            messages.add(locationHandler.action_menu(chatId, user.getLanguage()));
            logger.log(player.getNickname(), user.getId(), "выбрал пункт меню",
                    "действие в локации");
            return messages;
        }
        if (callback.startsWith("craft")) {
            messages.add(menuHandler.in_dev(chatId, user.getLanguage()));
            logger.log(player.getNickname(), user.getId(), "выбрал пункт меню действие в локации",
                    "крафт");
            return messages;
        }
        if (callback.startsWith("changeLocation")) {
            messages.add(locationHandler.change_location(chatId, user.getLanguage()));
            logger.log(player.getNickname(), user.getId(), "выбрал пункт меню",
                    "сменить локацию");
            return messages;
        }
        if (callback.startsWith("Clearing")) {
            player.setLocation(callback);
            messages.add(locationHandler.location_has_been_chosen(chatId, user.getLanguage()));
            messages.add(locationHandler.action_menu(chatId, user.getLanguage()));

            logger.log(player.getNickname(), user.getId(), "выбрал пункт меню смена локации",
                    "Поляна");
        }
        if (callback.startsWith("Suburb")) {
            player.setLocation(callback);
            messages.add(locationHandler.location_has_been_chosen(chatId, user.getLanguage()));
            messages.add(locationHandler.action_menu(chatId, user.getLanguage()));
            logger.log(player.getNickname(), user.getId(), "выбрал пункт меню смена локации",
                    "Пригород");
            return messages;
        }
        if(callback.startsWith("talents_up")){
            messages.add(playerHandler.ways_list(chatId, user.getLanguage(), talentsInit.getWaysList()));
            return messages;
        }
        if (callback.startsWith("back")) {
            messages.add(menuHandler.menu(chatId, user.getLanguage()));
            return messages;
        }
        if(talentsInit.getWaysList().contains(searchWay(callback))){
            messages.add(playerHandler.talents_list(chatId, user.getLanguage(), searchWay(callback).getTalents(),
                    searchWay(callback), player));
            return messages;
        }
        if(callback.startsWith("up_")){
            callback = callback.substring(3);
            messages.add(playerHandler.talent_up_info(chatId, user.getLanguage(), player, callback, user));
            messages.add(playerHandler.your_balance(chatId, user.getLanguage(), player.getCrystals()));

            return messages;
        }
        if(callback.startsWith("branch_up_")){
            callback = callback.substring(10);
            messages.add(playerHandler.your_balance(chatId, user.getLanguage(), player.getCrystals()));
            messages.add(playerHandler.branch_up_info(chatId, user.getLanguage(), player, callback, user));
            messages.add(playerHandler.your_balance(chatId, user.getLanguage(), player.getCrystals()));
        }
        if(searchTalent(callback) != null){
            messages.add(playerHandler.your_talent_stats(searchTalent(callback), chatId, user.getLanguage(), player));
            messages.add(playerHandler.your_balance(chatId, user.getLanguage(), player.getCrystals()));
            return messages;
        }
        if(callback.startsWith("regeneration")){
            messages.add(playerHandler.regeneration_menu(chatId, user.getLanguage()));
        }
        if(callback.startsWith("healing")){
            user.setUserState(UserState.HEALING);
            messages.add(playerHandler.regeneration_stop(chatId, user.getLanguage()));
            userRepository.save(user);
            healing(user, player);
        }
        return messages;
    }

    private Talent searchTalent(String callback){
        List<Way> ways = talentsInit.getWaysList();
        for(Way way : ways){
            for(Talent talent:way.getTalents()){
                if(talent.getNameEn().equals(callback)){
                    return talent;
                }
            }
        }
        return null;
    }
    private Way searchWay(String callback){
        List<Way> ways = talentsInit.getWaysList();
        for(Way way : ways){
            if(way.getNameEn().equals(callback)){
                return way;
            }
        }
        return null;
    }

    private void healing(User user, Player player) {
        if (user.getUserState().equals(UserState.HEALING)) {
            CompletableFuture.runAsync(() -> {
                try {
                    TimeUnit.SECONDS.sleep(15);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(user.getUserState().equals(UserState.HEALING)) {
                    if(player.getHealthNow() < player.getHealth()) {
                        player.setHealthNow(player.getHealthNow() + player.getHealthRegeneration());
                    }
                    if(player.getEnergyNow() < player.getEnergy()) {
                        player.setEnergyNow(player.getEnergyNow() + player.getEnergyNow());
                    }
                    if(player.getBloodNow() < player.getBlood()) {
                        player.setBloodNow(player.getBloodNow() + player.getBloodRegeneration());
                    }
                    if(player.getBarrierNow() < player.getBarrier()){
                        player.setBarrierNow(player.getBarrierNow() + 1);
                    }
                    playerRepository.save(player);
                    healing(user, player);
                }
            });
        }
    }
}
