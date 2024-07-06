package app.main.GameBot.bot.handler;

import app.main.GameBot.bot.keyboard.InventoryKeyboard;
import app.main.GameBot.bot.messager.Messager;
import app.main.GameBot.bot.messager.MessagerEn;
import app.main.GameBot.bot.messager.MessagerRu;
import app.main.GameBot.models.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InventoryHandler {

    private Messager messager;
    private final InventoryKeyboard inventoryKeyboard;


    private void choose_lang(String lang) {
        if (lang.startsWith("rus")) {
            messager = new MessagerRu();
        } else if (lang.startsWith("eng")) {
            messager = new MessagerEn();
        }
    }

    public SendMessage inventory_menu(Long chatId, String lang){
        choose_lang(lang);
        var sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(messager.getInventory_menu());
        sendMessage.setReplyMarkup(inventoryKeyboard.menu_keyboard(lang));
        return sendMessage;
    }
    public SendMessage ingredients(Long chatId, String lang, List<Item> items){
        choose_lang(lang);
        var sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        if(items.isEmpty()){
            sendMessage.setText(messager.getInventoryIsEmpty());
        }else {
            var text = messager.getYourInventory();

            if(lang.equals("rus")) {
                for (Item item : items) {
                    text = text + item.getItemNameRu() + " " + item.getCount() + "\n";
                }
            }else if (lang.equals("eng")){
                for (Item item : items) {
                    text = text + item.getItemNameEn() + " " + item.getCount() + "\n";
                }
            }
            sendMessage.setText(text);
        }
        sendMessage.setReplyMarkup(inventoryKeyboard.ingredients_keyboard(lang));
        return sendMessage;
    }
}
