package app.main.GameBot;

import app.main.GameBot.bot.GameBot;
import app.main.GameBot.bot.service.FightService;
import app.main.GameBot.bot.service.MenuService;
import app.main.GameBot.bot.config.BotConfig;
import app.main.GameBot.bot.handler.InventoryHandler;
import app.main.GameBot.bot.handler.LocationHandler;
import app.main.GameBot.bot.handler.MenuHandler;
import app.main.GameBot.bot.handler.PlayerHandler;
import app.main.GameBot.bot.service.PlayerService;
import app.main.GameBot.other.Logger;
import app.main.GameBot.repositories.ItemRepository;
import app.main.GameBot.repositories.PlayerRepository;
import app.main.GameBot.repositories.UserRepository;
import app.main.GameBot.talent.TalentsInit;
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
    private final LocationHandler locationHandler;
    private final MenuHandler menuHandler;
    private final PlayerHandler playerHandler;
    private final PlayerRepository playerRepository;
    private final Logger logger;
    private final UserRepository userRepository;
    private final MenuService menuService;
    private final PlayerService playerService;
    private final TalentsInit talentsInit;
    private final FightService fightService;

    @EventListener(ContextRefreshedEvent.class)
    public void init() {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new GameBot(botConfig, locationHandler,
                    playerRepository, userRepository, menuService
            ,playerService, playerHandler, talentsInit, fightService));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
