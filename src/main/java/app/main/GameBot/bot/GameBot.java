package app.main.GameBot.bot;

import app.main.GameBot.bot.config.BotConfig;
import app.main.GameBot.bot.handler.InventoryHandler;
import app.main.GameBot.bot.handler.LocationHandler;
import app.main.GameBot.bot.handler.MenuHandler;
import app.main.GameBot.bot.handler.PlayerHandler;
import app.main.GameBot.bot.service.MenuService;
import app.main.GameBot.bot.service.PlayerService;
import app.main.GameBot.models.Player;
import app.main.GameBot.models.User;
import app.main.GameBot.other.Logger;
import app.main.GameBot.repositories.ItemRepository;
import app.main.GameBot.repositories.PlayerRepository;
import app.main.GameBot.repositories.UserRepository;
import app.main.GameBot.states.Location;
import app.main.GameBot.states.UserState;
import lombok.SneakyThrows;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/*Главный метод бота. Здесь обрабатываются все обновления от клиента, после чего вызываются нужные хендлеры
 * для дальнейшей обработки и обновления*/
@Component
public class GameBot extends TelegramLongPollingBot {

    private final BotConfig botConfig;
    private final LocationHandler locationHandler;
    private final PlayerRepository playerRepository;
    private final UserRepository userRepository;
    private final Logger logger;
    private final MenuService menuService;
    private final PlayerService playerService;

    private List<User> users = new ArrayList<>();
    private Map<Long, User> userMap = new HashMap<>();

    public GameBot(BotConfig botConfig,
                   LocationHandler locationHandler,
                   PlayerRepository playerRepository,UserRepository userRepository, Logger logger, MenuService menuService, PlayerService playerService) {
        this.botConfig = botConfig;
        this.locationHandler = locationHandler;
        this.playerRepository = playerRepository;
        this.userRepository = userRepository;
        this.logger = logger;
        this.menuService = menuService;
        this.playerService = playerService;
    }

    @Override
    @Async
    public void onUpdateReceived(Update update) {
        User user;
        if (update.hasMessage() && update.getMessage().hasText()) {
            if (userMap.containsKey(update.getMessage().getChatId())) {
                user = userMap.get(update.getMessage().getChatId());
            } else {
                user = userRepository.findUserByChatId(update.getMessage().getFrom().getId());
                if (user == null) {
                    user = new User();
                    user.setChatId(update.getMessage().getFrom().getId());
                    userRepository.save(user);
                }
                users.add(user);
                userMap.put(update.getMessage().getChatId(), user);
            }
            commandHandler(update, user);
        } else if (update.hasCallbackQuery()) {
            if (userMap.containsKey(update.getCallbackQuery().getFrom().getId())) {
                user = userMap.get(update.getCallbackQuery().getFrom().getId());
            } else {
                user = userRepository.findUserByChatId(update.getCallbackQuery().getFrom().getId());
                if (user == null) {
                    user = new User();
                    user.setChatId(update.getCallbackQuery().getFrom().getId());
                    userRepository.save(user);
                }
                users.add(user);
                userMap.put(update.getCallbackQuery().getFrom().getId(), user);
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
            updateUser(menuService.get_user());

            return;
        }
        if (user.getUserState().equals(UserState.GET_NAME)) {
            user.setUserState(UserState.MENU);
            List<BotApiMethodMessage> messages = menuService.message_menu_handle(update, user);
            updateUser(menuService.get_user());
            sendMessages(messages);
        }
        /*ToDo*/
    }

    /*Асинхронный метод для обработки обновленйи с типом Callback*/
    @Async
    @SneakyThrows
    protected void callbackHandler(Update update, User user) {

        var callback = update.getCallbackQuery().getData();
        var chatId = update.getCallbackQuery().getFrom().getId();

        if (user.getUserState() == null) {
            sendMessages(menuService.callback_menu_handle(update, user));
            updateUser(menuService.get_user());
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
                        execute(locationHandler.search(chatId, user.getLanguage(), player));
                        execute(locationHandler.action_menu(chatId, user.getLanguage()));
                        user.setUserState(UserState.MENU);
                        updateUser(user);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                });

                logger.log(player.getNickname(), user.getId(), "выбрал пункт меню действие в локации",
                        "исследование локации");
                return;
            }
        }
        if (user.getUserState().equals(UserState.GET_NAME)) {
            List<BotApiMethodMessage> messages = menuService.callback_menu_handle(update, user);
            updateUser(menuService.get_user());
            sendMessages(messages);
        }
        /*ToDo*/
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
        userMap.put(user.getChatId(), user);
    }

    @SneakyThrows
    private void sendMessages(List<BotApiMethodMessage> messages) {
        if(!messages.isEmpty()) {
            for (BotApiMethodMessage botApiMethodMessage : messages) {
                execute(botApiMethodMessage);
            }
        }
    }
}
