package app.main.GameBot.bot;

import app.main.GameBot.bot.config.BotConfig;
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
import lombok.SneakyThrows;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*Главный метод бота. Здесь обрабатываются все обновления от клиента, после чего вызываются нужные хендлеры
 * для дальнейшей обработки и обновления*/
@Component
public class GameBot extends TelegramLongPollingBot {

    private final BotConfig botConfig;
    private final InventoryHandler inventoryHandler;
    private final LocationHandler locationHandler;
    private final MenuHandler menuHandler;
    private final PlayerHandler playerHandler;
    private final PlayerRepository playerRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final Logger logger;

    private List<User> users = new ArrayList<>();
    private Map<Long, User> userMap = new HashMap<>();

    public GameBot(BotConfig botConfig, InventoryHandler inventoryHandler,
                   LocationHandler locationHandler, MenuHandler menuHandler,
                   PlayerHandler playerHandler, PlayerRepository playerRepository, ItemRepository inventoryRepository, UserRepository userRepository, Logger logger) {
        this.botConfig = botConfig;
        this.inventoryHandler = inventoryHandler;
        this.locationHandler = locationHandler;
        this.menuHandler = menuHandler;
        this.playerHandler = playerHandler;
        this.playerRepository = playerRepository;
        this.itemRepository = inventoryRepository;
        this.userRepository = userRepository;
        this.logger = logger;
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
        var chatId = update.getMessage().getChat().getId();
        var command = update.getMessage().getText();
        if (command.startsWith("/start")) {
            execute(menuHandler.start(chatId, user.getLanguage()));
            return;
        }
        if (user.getUserState().equals(UserState.GET_NAME)) {
            var nickname = command;
            Player player = playerHandler.create_player(nickname, user);
            playerRepository.save(player);
            logger.log(nickname, player.getId(), "зарегистрировался", null);
            user.setUserState(UserState.MENU);
            updateUser(user);
            execute(menuHandler.greeting(chatId, user.getLanguage(), player.getNickname()));
            execute(menuHandler.menu(chatId, user.getLanguage()));
        }
    }

    /*Асинхронный метод для обработки обновленйи с типом Callback*/
    @Async
    @SneakyThrows
    protected void callbackHandler(Update update, User user) {

        var callback = update.getCallbackQuery().getData();
        var chatId = update.getCallbackQuery().getFrom().getId();

        if (user.getUserState() == null) {
            if (callback.startsWith("lang")) {
                callback = callback.substring(5);
                user.setLanguage(callback);
                user.setUserState(UserState.GET_NAME);
                updateUser(user);
                execute(menuHandler.get_name(chatId, user.getLanguage()));
                return;
            }
        }
        Player player = playerRepository.findPlayerById(user.getId());

        if (user.getUserState().equals(UserState.MENU)) {
            if (callback.equals("character")) {
                execute(playerHandler.character_menu(chatId, user.getLanguage()));
                logger.log(player.getNickname(), user.getId(), "выбрал пукт меню",
                        "персонаж");
                return;
            }
            if(callback.startsWith("characteristics")){
                execute(playerHandler.sendCharacteristics(chatId, user.getLanguage(), player));
                logger.log(player.getNickname(), user.getId(), "выбрал пукт меню персонажа",
                        "харрактеристика");
                return;
            }
            if (callback.startsWith("talents")){
                execute(menuHandler.in_dev(chatId, user.getLanguage()));
                logger.log(player.getNickname(), user.getId(), "выбрал пукт меню персонажа",
                        "таланты");
                return;
            }
            if (callback.startsWith("inventory")) {
                execute(inventoryHandler.inventory_menu(chatId, user.getLanguage()));
                logger.log(player.getNickname(), user.getId(), "выбрал пукт меню",
                        "инвентарь");
                return;

            }
            if(callback.startsWith("items")){
                execute(menuHandler.in_dev(chatId, user.getLanguage()));
                logger.log(player.getNickname(), user.getId(), "выбрал пукт меню инвенторя",
                        "Предметы");
                return;
            }
            if(callback.startsWith("ingredients")){
                var items = itemRepository.findItemsByPlayer(player);
                execute(inventoryHandler.ingredients(chatId, user.getLanguage(), items));
                return;
            }
            if(callback.startsWith("artifacts")){
                execute(menuHandler.in_dev(chatId, user.getLanguage()));
                logger.log(player.getNickname(), user.getId(), "выбрал пукт меню инвенторя",
                        "Артефакты");
                return;
            }
            if (callback.startsWith("action")) {
                execute(locationHandler.action_menu(chatId, user.getLanguage()));
                logger.log(player.getNickname(), user.getId(), "выбрал пукт меню",
                        "действие в локации");
                return;
            }
            if (callback.startsWith("search")) {
                execute(locationHandler.search(chatId, user.getLanguage(), player));
                logger.log(player.getNickname(), user.getId(), "выбрал пукт меню действие в локации",
                        "исследование локации");
                return;
            }
            if (callback.startsWith("craft")) {
                execute(menuHandler.in_dev(chatId, user.getLanguage()));
                logger.log(player.getNickname(), user.getId(), "выбрал пукт меню действие в локации",
                        "крафт");
                return;
            }
            if (callback.startsWith("changeLocation")) {
                logger.log(player.getNickname(), user.getId(), "выбрал пукт меню",
                        "сменить локацию");
                return;
            }

            if (callback.startsWith("back")) {
                execute(menuHandler.menu(chatId, user.getLanguage()));
                return;
            }
        }
        if (user.getUserState().equals(UserState.GET_NAME)) {
            var nickname = update.getCallbackQuery().getFrom().getUserName();
            Player newPlayer = playerHandler.create_player(nickname, user);
            playerRepository.save(newPlayer);
            logger.log(nickname, newPlayer.getId(), "зарегистрировался", null);
            user.setUserState(UserState.MENU);
            updateUser(user);
            execute(menuHandler.greeting(chatId, user.getLanguage(), newPlayer.getNickname()));
            execute(menuHandler.menu(chatId, user.getLanguage()));
            return;
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
        userMap.put(user.getChatId(), user);
    }

}
