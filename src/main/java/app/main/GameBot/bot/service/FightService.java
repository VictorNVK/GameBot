package app.main.GameBot.bot.service;

import app.main.GameBot.bot.handler.FightHandler;
import app.main.GameBot.bot.handler.LocationHandler;
import app.main.GameBot.bot.handler.MenuHandler;
import app.main.GameBot.bot.handler.PlayerHandler;
import app.main.GameBot.location.LocationInit;
import app.main.GameBot.models.Player;
import app.main.GameBot.models.User;
import app.main.GameBot.other.Logger;
import app.main.GameBot.repositories.UserRepository;
import app.main.GameBot.states.UserState;
import app.main.GameBot.talent.TalentsInit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FightService {

    private final TalentsInit talentsInit;
    private final UserRepository userRepository;
    private final PlayerHandler playerHandler;
    private final MenuHandler menuHandler;
    private final Logger logger;
    private final LocationInit locationInit;
    private final FightHandler fightHandler;
    private final LocationHandler locationHandler;


    public List<BotApiMethodMessage> callback_menu_handle(Update update, User user, Player player){
        List<BotApiMethodMessage> messages = new ArrayList<>();
        var callback = update.getCallbackQuery().getData();
        var chatId = update.getCallbackQuery().getFrom().getId();

        fightHandler.check_death_player(player);

        if(callback.startsWith("skills")){

        }
        if(callback.startsWith("actions")){
            messages.add(fightHandler.actions(chatId, user.getLanguage()));

        }
        if(callback.startsWith("evade")){
            messages.add(fightHandler.evade(chatId, user.getLanguage(), user, player));
        }
        if(callback.startsWith("back")){
            messages.add(fightHandler.fight_menu(chatId, user.getLanguage()));
        }

        return messages;
    }

    public SendMessage under_attack(Long chatId, String lang, User user, Player player){
        return fightHandler.under_attack(chatId, lang, user, player);
    }
    public SendMessage enemy_attack(Long chatId, String lang, Player player){


        return fightHandler.enemy_attack(chatId, lang, player);
    }
    public SendMessage first_enemy_step(Long chatId, String lang){
        return fightHandler.first_enemy_step(chatId, lang);
    }
    public SendMessage sendCharacteristics(Long chatId, String lang, Player player){
        return fightHandler.sendCharacteristics(chatId, lang, player);
    }
    public SendMessage fight_menu(Long chatId, String lang){
        return fightHandler.fight_menu(chatId, lang);
    }
    public SendMessage enemy_step(Long chatId, String lang){
        return fightHandler.enemy_step(chatId, lang);
    }
    public SendMessage player_step(Long chatId, String lang){
        return fightHandler.player_step(chatId, lang);
    }


}
