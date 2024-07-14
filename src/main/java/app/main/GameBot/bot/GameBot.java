package app.main.GameBot.bot;
import app.main.GameBot.bot.config.BotConfig;
import app.main.GameBot.bot.handler.LocationHandler;
import app.main.GameBot.bot.handler.PlayerHandler;
import app.main.GameBot.bot.service.FightService;
import app.main.GameBot.bot.service.MenuService;
import app.main.GameBot.bot.service.PlayerService;
import app.main.GameBot.models.Player;
import app.main.GameBot.models.User;
import app.main.GameBot.repositories.PlayerRepository;
import app.main.GameBot.repositories.UserRepository;
import app.main.GameBot.states.UserState;
import app.main.GameBot.talent.Talent;
import app.main.GameBot.talent.TalentsInit;
import app.main.GameBot.way.Way;
import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

/*Главный метод бота. Здесь обрабатываются все обновления от клиента, после чего вызываются нужные хендлеры
 * для дальнейшей обработки и обновления*/
@Component
@Getter
public class GameBot extends TelegramLongPollingBot {

    private final BotConfig botConfig;
    private final LocationHandler locationHandler;
    private final PlayerRepository playerRepository;
    private final UserRepository userRepository;
    private final MenuService menuService;
    private final PlayerService playerService;
    private final PlayerHandler playerHandler;
    private final TalentsInit talentsInit;
    private final FightService fightService;


    public GameBot(BotConfig botConfig,
                   LocationHandler locationHandler,
                   PlayerRepository playerRepository, UserRepository userRepository, MenuService menuService,
                   PlayerService playerService, PlayerHandler playerHandler, TalentsInit talentsInit, FightService fightService) {
        this.botConfig = botConfig;
        this.locationHandler = locationHandler;
        this.playerRepository = playerRepository;
        this.userRepository = userRepository;
        this.menuService = menuService;
        this.playerService = playerService;
        this.playerHandler = playerHandler;
        this.talentsInit = talentsInit;
        this.fightService = fightService;
    }

    @Override
    @Async
    public void onUpdateReceived(Update update) {
        User user;
        if (update.hasMessage() && update.getMessage().hasText()) {
                user = userRepository.findUserByChatId(update.getMessage().getFrom().getId());
                if (user == null) {
                    user = new User();
                    user.setChatId(update.getMessage().getFrom().getId());
                    userRepository.save(user);
            }
            commandHandler(update, user);
        } else if (update.hasCallbackQuery()) {
                user = userRepository.findUserByChatId(update.getCallbackQuery().getFrom().getId());
                if (user == null) {
                    user = new User();
                    user.setChatId(update.getCallbackQuery().getFrom().getId());
                    userRepository.save(user);
            }
            callbackHandler(update, user);
        }
    }

    /*Асинхронный метод для обработки обновлений с типом Message*/
    @Async
    @SneakyThrows
    protected void commandHandler(Update update, User user) {
        if (user.getUserState() == null) {
            List<BotApiMethodMessage> messages = menuService.message_menu_handle(update, user);
            sendMessages(messages);
            return;
        }
        if (user.getUserState().equals(UserState.GET_NAME)) {
            List<BotApiMethodMessage> messages = menuService.message_menu_handle(update, user);
            sendMessages(messages);
            user.setUserState(UserState.MENU);
            userRepository.save(user);
        }
    }

    /*Асинхронный метод для обработки обновленйи с типом Callback*/
    @Async
    @SneakyThrows
    protected void callbackHandler(Update update, User user) {

        var callback = update.getCallbackQuery().getData();
        var chatId = update.getCallbackQuery().getFrom().getId();


        if (user.getUserState() == null) {
            sendMessages(menuService.callback_menu_handle(update, user));
            return;
        }
        Player player = playerRepository.findPlayerById(user.getId());

        if(user.getUserState().equals(UserState.MENU)) {
             sendMessages(playerService.callback_menu_handle(update, user, player));
             updateUser(playerService.get_user());
             playerRepository.save(playerService.get_player());

            if (callback.startsWith("search") && !user.getUserState().equals(UserState.SEARCH)) {
                execute(locationHandler.search_await(chatId, user.getLanguage()));
                user.setUserState(UserState.SEARCH);
                updateUser(user);
                CompletableFuture.runAsync(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        execute(locationHandler.search(chatId, user.getLanguage(), player, user));

                        if(user.getUserState().equals(UserState.MENU)) {
                            execute(locationHandler.action_menu(chatId, user.getLanguage()));
                        }
                        if(user.getUserState().equals(UserState.FIGHT)){
                            execute(fightService.under_attack(chatId, user.getLanguage(), user, player));
                            Random random = new Random();
                            var number = random.nextInt(2);
                            if(number == 1){
                                user.setUserState(UserState.FIGHT_AWAIT);
                                userRepository.save(user);
                                execute(fightService.first_enemy_step(chatId, user.getLanguage()));
                                enemy_step(user);
                            }else{
                                user.setUserState(UserState.FIGHT);
                                userRepository.save(user);
                                execute(fightService.fight_menu(chatId, user.getLanguage()));
                                player_step(user);
                            }
                        }
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                });
                return;
            }
        }
        if (user.getUserState().equals(UserState.GET_NAME)) {
            List<BotApiMethodMessage> messages = menuService.callback_menu_handle(update, user);
            sendMessages(messages);
            return;
        }
        if(user.getUserState().equals(UserState.GRADE)){
            List<BotApiMethodMessage> messages = menuService.callback_menu_handle(update, user);
            sendMessages(messages);
            return;
        }
        if(user.getUserState().equals(UserState.FIGHT)){
            sendMessages(fightService.callback_menu_handle(update, user, player));

        }
        if(user.getUserState().equals(UserState.FIGHT_AWAIT)){


        }
    }
        private void player_step(User user) throws TelegramApiException {
        Player player = playerRepository.findPlayerById(user.getId());
        var chatId = user.getChatId();
        execute(fightService.player_step(chatId, user.getLanguage()));
        if(user.getUserState().equals(UserState.FIGHT)) {
            CompletableFuture.runAsync(() -> {
                try {
                    TimeUnit.SECONDS.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                user.setUserState(UserState.FIGHT_AWAIT);
                userRepository.save(user);
                try {
                    enemy_step(user);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
    private void enemy_step(User user) throws TelegramApiException {
        Player player = playerRepository.findPlayerById(user.getId());
        var chatId = user.getChatId();
        execute(fightService.enemy_step(chatId, user.getLanguage()));
        if(user.getUserState().equals(UserState.FIGHT_AWAIT)) {
            CompletableFuture.runAsync(() -> {
                try {
                    TimeUnit.SECONDS.sleep(15);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                user.setUserState(UserState.FIGHT);
                userRepository.save(user);
                try {
                    execute(fightService.enemy_attack(chatId, user.getLanguage(), player));
                    execute(fightService.sendCharacteristics(chatId, user.getLanguage(), player));
                    execute(fightService.fight_menu(chatId, user.getLanguage()));
                    player_step(user);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBOT_NAME();
    }

    @Override
    public String getBotToken() {
        return botConfig.getBOT_TOKEN();
    }

    /*Метод для обновления атрибутов пользователя*/
    private void updateUser(User user) {
        userRepository.save(user);
    }

    @SneakyThrows
    private void sendMessages(List<BotApiMethodMessage> messages) {
        if(!messages.isEmpty()) {
            for (BotApiMethodMessage botApiMethodMessage : messages) {
                execute(botApiMethodMessage);
            }
        }
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
}
