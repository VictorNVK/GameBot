package app.main.GameBot.bot.handler;

import app.main.GameBot.bot.keyboard.PlayerKeyboard;
import app.main.GameBot.bot.messager.Messager;
import app.main.GameBot.bot.messager.MessagerEn;
import app.main.GameBot.bot.messager.MessagerRu;
import app.main.GameBot.models.Player;
import app.main.GameBot.models.User;
import app.main.GameBot.states.Location;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
@RequiredArgsConstructor
/*Класс управления для обновлений связанных с игроком, все методы вызываюся в главном классе бота, но есть и
вспомогательные*/
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
        var player = new Player();
        player.setId(user.getId());
        player.setNickname(name);
        player.setLevel(0);
        player.setHealth(20);
        player.setEnergy(10);
        player.setBlood(5);
        player.setAttack(1);
        player.setDefense(0);
        player.setHealthRegeneration(1);
        player.setEnergyRegeneration(1);
        player.setBloodRegeneration(1);
        player.setLocation(Location.CLEARING);
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
    public SendMessage sendCharacteristics(Long chatId, String lang, Player player){
        choose_lang(lang);
        var sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(messager.getCharacteristics() +
                "\n\n"
                + messager.getLevel() + player.getLevel() + "\uD83C\uDF1F"
                +"\n"+ messager.getHealth() + player.getHealth() + "♥\uFE0F"
        +"\n" +messager.getEnergy() + player.getEnergy() + "⚡\uFE0F"
        +"\n" + messager.getBlood() + player.getBlood() + "\uD83E\uDE78"
                +"\n" + messager.getAttack() + player.getAttack() + "\uD83D\uDDE1"
        +"\n" +messager.getDefense() + player.getDefense() + "\uD83D\uDEE1"
        +"\n" + messager.getHealthRegeneration() + player.getHealthRegeneration()
        +"\n" + messager.getEnergyRegeneration() + player.getEnergyRegeneration()
        +"\n" + messager.getBloodRegeneration() + player.getBloodRegeneration());

        sendMessage.setReplyMarkup(playerKeyboard.characteristics_keyboard(lang));
        return sendMessage;
    }
}
