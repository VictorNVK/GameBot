package app.main.GameBot.bot.keyboard;

import app.main.GameBot.models.Player;
import app.main.GameBot.repositories.TalentRepository;
import app.main.GameBot.talent.Talent;
import app.main.GameBot.way.Way;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.language.bm.Lang;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FightKeyboard extends Keyboard{

    private final TalentRepository talentRepository;


    public InlineKeyboardMarkup action_menu(String lang){
        choose_lang(lang);
        var keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        var skill = new InlineKeyboardButton();
        skill.setText(messager.getSkills());
        skill.setCallbackData("skills");
        row1.add(skill);

        var action = new InlineKeyboardButton();
        action.setText(messager.getActions());
        action.setCallbackData("actions");
        row1.add(action);

        rows.add(row1);
        keyboardMarkup.setKeyboard(rows);
        return keyboardMarkup;
    }

    public InlineKeyboardMarkup actions_keyboard(String lang){
        choose_lang(lang);
        var keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        var evade = new InlineKeyboardButton();
        evade.setText(messager.getEvade());
        evade.setCallbackData("evade");
        row1.add(evade);

        var back = new InlineKeyboardButton();
        back.setText(messager.getBack());
        back.setCallbackData("back");
        row1.add(back);

        rows.add(row1);
        keyboardMarkup.setKeyboard(rows);
        return keyboardMarkup;
    }

    public InlineKeyboardMarkup ways_menu(String lang, List<Way> ways){
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
    public InlineKeyboardMarkup talents_list(List<Talent> talents, String lang, app.main.GameBot.models.Way way) {
        choose_lang(lang);
        var keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        for (int i = 0; i < talents.size(); i++) {
            if (talents.get(i).getUnlocked_way_level() <= way.getLevel()) {
                List<InlineKeyboardButton> row1 = new ArrayList<>();
                var button1 = new InlineKeyboardButton();
                if (lang.equals("rus")) {
                    button1.setText(talents.get(i).getNameRu());
                } else if (lang.equals("eng")) {
                    button1.setText(talents.get(i).getNameEn());
                }
                button1.setCallbackData("choose_" +  talents.get(i).getNameEn());
                row1.add(button1);
                rows.add(row1);
            }
        }
        List<InlineKeyboardButton> row2 = new ArrayList<>();
        var back = new InlineKeyboardButton();
        back.setText(messager.getBack());
        back.setCallbackData("back");
        row2.add(back);
        rows.add(row2);

        keyboardMarkup.setKeyboard(rows);
        return keyboardMarkup;

    }
}
