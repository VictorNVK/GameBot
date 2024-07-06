package app.main.GameBot.bot.handler;

import app.main.GameBot.bot.keyboard.LocationKeyboard;
import app.main.GameBot.bot.messager.Messager;
import app.main.GameBot.bot.messager.MessagerEn;
import app.main.GameBot.bot.messager.MessagerRu;
import app.main.GameBot.location.LocationInit;
import app.main.GameBot.models.Player;
import app.main.GameBot.states.Location;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
@RequiredArgsConstructor
public class LocationHandler {

    private Messager messager;
    private final LocationKeyboard locationKeyboard;
    private final LocationInit locationInit;


    private void choose_lang(String lang) {
        if (lang.startsWith("rus")) {
            messager = new MessagerRu();
        } else if (lang.startsWith("eng")) {
            messager = new MessagerEn();
        }
    }

    public SendMessage action_menu(Long chatId, String lang){
        choose_lang(lang);
        var sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(messager.getLocation_menu());
        sendMessage.setReplyMarkup(locationKeyboard.action_menu_keyboard(lang));
        return sendMessage;
    }

    public SendMessage search(Long chatId, String lang, Player player){
        choose_lang(lang);
        var sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        if(player.getLocation().equals(Location.CLEARING)){
            
        }
        if(player.getLocation().equals(Location.SUBURB)){

        }

        return sendMessage;
    }
}
