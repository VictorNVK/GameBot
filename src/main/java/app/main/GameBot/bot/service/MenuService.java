package app.main.GameBot.bot.service;

import app.main.GameBot.bot.handler.LocationHandler;
import app.main.GameBot.bot.handler.MenuHandler;
import app.main.GameBot.bot.handler.PlayerHandler;
import app.main.GameBot.models.Player;
import app.main.GameBot.models.User;
import app.main.GameBot.other.Logger;
import app.main.GameBot.repositories.PlayerRepository;
import app.main.GameBot.repositories.UserRepository;
import app.main.GameBot.states.UserState;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Getter
public class MenuService {

    private final MenuHandler menuHandler;
    private final Logger logger;
    private final PlayerRepository playerRepository;
    private final PlayerHandler playerHandler;
    private final LocationHandler locationHandler;
    private final UserRepository userRepository;
    private User _user;
    private Player _player;



    public List<BotApiMethodMessage> message_menu_handle(Update update, User user){
        this._user = user;

        List<BotApiMethodMessage> messages= new ArrayList<>();
        var chatId = update.getMessage().getChat().getId();
        var command = update.getMessage().getText();
        if (command.startsWith("/start")){
            messages.add(menuHandler.start(chatId, user.getLanguage()));
            return messages;
        }
        if (user.getUserState().equals(UserState.GET_NAME)) {
            var nickname = command;
            Player player = playerHandler.create_player(nickname, user);
            playerRepository.save(player);
            logger.log(nickname, player.getId(), "зарегистрировался", null);
            messages.add(menuHandler.greeting(chatId, user.getLanguage(), player.getNickname()));
            messages.add(locationHandler.clearing_start_location(chatId, user.getLanguage()));
            messages.add(menuHandler.menu(chatId, user.getLanguage()));
            return messages;
        }

        return messages;
    }
    public List<BotApiMethodMessage> callback_menu_handle(Update update, User user){

        this._user = user;
        Player player = playerRepository.findPlayerById(user.getId());
        List<BotApiMethodMessage> messages = new ArrayList<>();
        var callback = update.getCallbackQuery().getData();
        var chatId = update.getCallbackQuery().getFrom().getId();

        if (callback.startsWith("lang")) {
            callback = callback.substring(5);
            user.setLanguage(callback);
            user.setUserState(UserState.GET_NAME);
            messages.add(menuHandler.get_name(chatId, user.getLanguage()));
            return messages;
        }
        if (user.getUserState().equals(UserState.GET_NAME)) {
            user.setUserState(UserState.MENU);
            var nickname = update.getCallbackQuery().getFrom().getUserName();
            Player newPlayer = playerHandler.create_player(nickname, user);
            playerRepository.save(newPlayer);
            logger.log(nickname, newPlayer.getId(), "зарегистрировался", null);
            user.setUserState(UserState.MENU);
            messages.add(menuHandler.greeting(chatId, user.getLanguage(), newPlayer.getNickname()));
            messages.add(locationHandler.clearing_start_location(chatId, user.getLanguage()));
            messages.add(menuHandler.menu(chatId, user.getLanguage()));
            return messages;

        }
        if(callback.startsWith("back")){
            user.setUserState(UserState.MENU);
            messages.add(playerHandler.back_up(chatId, user.getLanguage() , player));
            messages.add(playerHandler.your_balance(chatId, user.getLanguage(), player.getCrystals()));
            userRepository.save(user);
        }
        return messages;
    }


}
