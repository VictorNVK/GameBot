package app.main.GameBot.bot;

import app.main.GameBot.bot.config.BotConfig;
import app.main.GameBot.bot.handler.InventoryHandler;
import app.main.GameBot.bot.handler.LocationHandler;
import app.main.GameBot.bot.handler.MenuHandler;
import app.main.GameBot.bot.handler.PlayerHandler;
import app.main.GameBot.models.User;
import app.main.GameBot.repositories.UserRepository;
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
    private final UserRepository userRepository;
    private final InventoryHandler inventoryHandler;
    private final LocationHandler locationHandler;
    private final MenuHandler menuHandler;
    private final PlayerHandler playerHandler;

    private List<User> users = new ArrayList<>();
    private Map<Long, User> userMap = new HashMap<>();

    public GameBot(BotConfig botConfig, UserRepository userRepository, InventoryHandler inventoryHandler,
                    LocationHandler locationHandler, MenuHandler menuHandler,
                   PlayerHandler playerHandler) {
        this.botConfig = botConfig;
        this.userRepository = userRepository;
        this.inventoryHandler = inventoryHandler;
        this.locationHandler = locationHandler;
        this.menuHandler = menuHandler;
        this.playerHandler = playerHandler;
    }

    @Override
    @Async
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            User user;
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
            callbackHandler(update);
        }
    }

    @Async
    @SneakyThrows
    protected void commandHandler(Update update, User user){
        var chatId = update.getMessage().getChat().getId();
        var command = update.getMessage().getText();
        if(command.startsWith("/start")){
            execute(menuHandler.start(chatId, user.getLanguage()));
        }
    }

    @Async
    protected void callbackHandler(Update update){

    }

    @Override
    public String getBotUsername() {
        return botConfig.getBOT_NAME();
    }

    @Override
    public String getBotToken() {
        return botConfig.getBOT_TOKEN();
    }

    private void updateUser(User user){
        userRepository.save(user);
        userMap.put(user.getChatId(), user);
    }

}
