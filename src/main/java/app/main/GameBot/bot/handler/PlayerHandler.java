package app.main.GameBot.bot.handler;

import app.main.GameBot.bot.keyboard.Keyboard;
import app.main.GameBot.bot.keyboard.MenuKeyboard;
import app.main.GameBot.bot.keyboard.PlayerKeyboard;
import app.main.GameBot.bot.messager.Messager;
import app.main.GameBot.bot.messager.MessagerEn;
import app.main.GameBot.bot.messager.MessagerRu;
import app.main.GameBot.models.Player;
import app.main.GameBot.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
@RequiredArgsConstructor
public class PlayerHandler {

    private Messager messager;
    private final PlayerKeyboard playerKeyboard;

    private void choose_lang(String lang) {
        if (lang.startsWith("rus")) {
            messager = new MessagerRu();
        } else if (lang.startsWith("eng")) {
            messager = new MessagerEn();
        }
    }

    public Player create_player(String name, User user){
        Player player = new Player();
        player.setOwner(user);
        player.setNickname(name);
        player.setLevel(1);
        player.setHealth(20);
        player.setEnergy(10);
        player.setBlood(5);
        player.setAttack(1);
        player.setDefense(0);
        player.setHealthRegeneration(1);
        player.setEnergyRegeneration(1);
        player.setBloodRegeneration(1);

        return player;
    }

    public SendMessage character_menu(Long chatId, String lang){
        choose_lang(lang);
        var sendMessage = new SendMessage();
        sendMessage.setText(messager.getCharacterMenu());
        sendMessage.setChatId(chatId);
        sendMessage.setReplyMarkup(playerKeyboard.character_menu(lang));
        return sendMessage;
    }
}
