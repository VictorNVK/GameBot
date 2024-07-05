package app.main.GameBot.bot.keyboard;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class InventoryKeyboard extends Keyboard{

    public InlineKeyboardMarkup menu_keyboard(String lang){
        choose_lang(lang);
        var keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        var ingredientButton = new InlineKeyboardButton();
        ingredientButton.setText(messager.getCraftingIngredients());
        ingredientButton.setCallbackData("ingredients");
        row1.add(ingredientButton);
        rows.add(row1);

        List<InlineKeyboardButton> row2 = new ArrayList<>();
        var itemButton = new InlineKeyboardButton();
        itemButton.setText(messager.getItems());
        itemButton.setCallbackData("items");
        row2.add(itemButton);
        rows.add(row2);

        List<InlineKeyboardButton> row3 = new ArrayList<>();
        var artifactButton = new InlineKeyboardButton();
        artifactButton.setText(messager.getArtifacts());
        artifactButton.setCallbackData("artifacts");
        row3.add(artifactButton);
        var backButton= new InlineKeyboardButton();
        backButton.setText(messager.getBack());
        backButton.setCallbackData("back");
        row3.add(backButton);
        rows.add(row3);

        keyboardMarkup.setKeyboard(rows);
        return keyboardMarkup;
    }
    public InlineKeyboardMarkup ingredients_keyboard(String lang){
        choose_lang(lang);

        var keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        var backButton = new InlineKeyboardButton();
        backButton.setText(messager.getBack());
        backButton.setCallbackData("back");
        row1.add(backButton);
        rows.add(row1);
        keyboardMarkup.setKeyboard(rows);
        return keyboardMarkup;
    }
}
