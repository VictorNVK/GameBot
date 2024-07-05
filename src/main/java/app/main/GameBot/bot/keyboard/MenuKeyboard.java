package app.main.GameBot.bot.keyboard;


import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class MenuKeyboard extends Keyboard{


    public InlineKeyboardMarkup select_lang(){
        var keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        List<InlineKeyboardButton> row1 = new ArrayList<>();
        var button1 = new InlineKeyboardButton();
        button1.setText("eng\uD83C\uDDEC\uD83C\uDDE7");
        button1.setCallbackData("lang-eng");
        var button2 = new InlineKeyboardButton();
        button2.setText("рус\uD83C\uDDF7\uD83C\uDDFA");
        button2.setCallbackData("lang-rus");
        row1.add(button1);
        row1.add(button2);
        rows.add(row1);

        keyboardMarkup.setKeyboard(rows);
        return keyboardMarkup;
    }
    public InlineKeyboardMarkup use_tg_name(String text){
        var keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        List<InlineKeyboardButton> row1 = new ArrayList<>();
        var button1 = new InlineKeyboardButton();
        button1.setText(text);
        button1.setCallbackData("use-tg-name");
        row1.add(button1);
        rows.add(row1);
        keyboardMarkup.setKeyboard(rows);
        return keyboardMarkup;
    }
    public InlineKeyboardMarkup menu(String lang){
         choose_lang(lang);

        var keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        var characterButton = new InlineKeyboardButton();
        characterButton.setText(messager.getCharacter());
        characterButton.setCallbackData("character");
        row1.add(characterButton);
        rows.add(row1);

        List<InlineKeyboardButton> row2 = new ArrayList<>();
        var inventoryButton = new InlineKeyboardButton();
        inventoryButton.setText(messager.getInventory());
        inventoryButton.setCallbackData("inventory");
        row2.add(inventoryButton);
        rows.add(row2);

        List<InlineKeyboardButton> row3 = new ArrayList<>();
        var actionButton = new InlineKeyboardButton();
        actionButton.setText(messager.getActionInLocation());
        actionButton.setCallbackData("action");
        row3.add(actionButton);
        var changeLocationButton = new InlineKeyboardButton();
        changeLocationButton.setText(messager.getChangeLocation());
        changeLocationButton.setCallbackData("changeLocation");
        row3.add(changeLocationButton);
        rows.add(row3);

        keyboardMarkup.setKeyboard(rows);
        return keyboardMarkup;
    }

}
