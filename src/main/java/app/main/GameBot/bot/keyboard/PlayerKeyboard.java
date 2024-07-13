package app.main.GameBot.bot.keyboard;

import app.main.GameBot.models.Player;
import app.main.GameBot.talent.Talent;
import app.main.GameBot.way.Way;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class PlayerKeyboard extends Keyboard {

    public InlineKeyboardMarkup character_menu(String lang){
        choose_lang(lang);
        var keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        List<InlineKeyboardButton> row1 = new ArrayList<>();
        var button1 = new InlineKeyboardButton();
        button1.setText(messager.getCharacteristics());
        button1.setCallbackData("characteristics");
        row1.add(button1);
        var button2 = new InlineKeyboardButton();
        button2.setText(messager.getTraining());
        button2.setCallbackData("training");
        row1.add(button2);
        rows.add(row1);

        List<InlineKeyboardButton> row2 = new ArrayList<>();
        var back = new InlineKeyboardButton();
        back.setText(messager.getBack());
        back.setCallbackData("back");
        row2.add(back);
        rows.add(row2);
        keyboardMarkup.setKeyboard(rows);
        return keyboardMarkup;
    }
    public InlineKeyboardMarkup back_keyboard(String lang){
        choose_lang(lang);
        var keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        var back = new InlineKeyboardButton();
        back.setText(messager.getBack());
        back.setCallbackData("back");
        row1.add(back);
        rows.add(row1);
        keyboardMarkup.setKeyboard(rows);
        return keyboardMarkup;
    }
    public InlineKeyboardMarkup train_menu(String lang){
        choose_lang(lang);

        var keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        List<InlineKeyboardButton> row1 = new ArrayList<>();
        var button1 = new InlineKeyboardButton();
        button1.setText(messager.getTalents());
        button1.setCallbackData("talents_up");
        row1.add(button1);
        var button2 = new InlineKeyboardButton();
        button2.setText(messager.getProf());
        button2.setCallbackData("prof_up");
        row1.add(button2);
        rows.add(row1);

        List<InlineKeyboardButton> row2 = new ArrayList<>();
        var back = new InlineKeyboardButton();
        back.setText(messager.getBack());
        back.setCallbackData("back");
        row2.add(back);
        rows.add(row2);
        keyboardMarkup.setKeyboard(rows);
        return keyboardMarkup;
    }
    public InlineKeyboardMarkup talents_list(List<Talent> talents, String lang, app.main.GameBot.models.Way way){
        choose_lang(lang);
        var keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        for(int i = 0; i < talents.size(); i ++){
            if(talents.get(i).getUnlocked_way_level() <= way.getLevel()) {
                List<InlineKeyboardButton> row1 = new ArrayList<>();
                var button1 = new InlineKeyboardButton();
                if (lang.equals("rus")) {
                    button1.setText(talents.get(i).getNameRu());
                } else if (lang.equals("eng")) {
                    button1.setText(talents.get(i).getNameEn());
                }
                button1.setCallbackData(talents.get(i).getNameEn());
                row1.add(button1);
                rows.add(row1);
            }
        }
        List<InlineKeyboardButton> row3 = new ArrayList<>();
        var up_branch = new InlineKeyboardButton();
        up_branch.setText(messager.getUp_branch());
        up_branch.setCallbackData("branch_up_" + way.getName());
        row3.add(up_branch);
        rows.add(row3);


        List<InlineKeyboardButton> row2 = new ArrayList<>();
        var back = new InlineKeyboardButton();
        back.setText(messager.getBack());
        back.setCallbackData("back");
        row2.add(back);
        rows.add(row2);

        keyboardMarkup.setKeyboard(rows);
        return keyboardMarkup;
    }
    public InlineKeyboardMarkup up_talent_menu(String lang, Player player){
        choose_lang(lang);

        var keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        List<InlineKeyboardButton> row1 = new ArrayList<>();
        var button1 = new InlineKeyboardButton();
        button1.setText(messager.getTrain_up());
        button1.setCallbackData("up_" + player.getLastTalent());
        row1.add(button1);
        rows.add(row1);

        List<InlineKeyboardButton> row2 = new ArrayList<>();
        var back = new InlineKeyboardButton();
        back.setText(messager.getBack());
        back.setCallbackData("back");
        row2.add(back);
        rows.add(row2);
        keyboardMarkup.setKeyboard(rows);
        return keyboardMarkup;
    }
    public InlineKeyboardMarkup waysList_list(List<Way> ways, String lang){
        choose_lang(lang);
        var keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        for(int i = 0; i < ways.size(); i ++){
            List<InlineKeyboardButton> row1 = new ArrayList<>();
            var button1 = new InlineKeyboardButton();
            if(lang.equals("rus")) {
                button1.setText(ways.get(i).getNameRu());
            }else if(lang.equals("eng")) {
                button1.setText(ways.get(i).getNameEn());
            }
            button1.setCallbackData(ways.get(i).getNameEn());
            row1.add(button1);
            rows.add(row1);
        }
        keyboardMarkup.setKeyboard(rows);
        return keyboardMarkup;
    }



}
