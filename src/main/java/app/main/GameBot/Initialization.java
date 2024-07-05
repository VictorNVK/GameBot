package app.main.GameBot;

import app.main.GameBot.bot.GameBot;
import app.main.GameBot.bot.config.BotConfig;
import app.main.GameBot.bot.handler.InventoryHandler;
import app.main.GameBot.bot.handler.LocationHandler;
import app.main.GameBot.bot.handler.MenuHandler;
import app.main.GameBot.bot.handler.PlayerHandler;
import app.main.GameBot.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/*Класс реализации и внедрения всех зависомостей в главный калсс бота*/
@Component
@RequiredArgsConstructor
public class Initialization {

    private final BotConfig botConfig;
    private final UserRepository userRepository;
    private final InventoryHandler inventoryHandler;
    private final LocationHandler locationHandler;
    private final MenuHandler menuHandler;
    private final PlayerHandler playerHandler;

    @EventListener(ContextRefreshedEvent.class)
    public void init() {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new GameBot(botConfig, userRepository, inventoryHandler, locationHandler, menuHandler
                    ,playerHandler));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
