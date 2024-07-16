package app.main.GameBot.bot.service;

import app.main.GameBot.bot.handler.FightHandler;
import app.main.GameBot.bot.handler.MenuHandler;
import app.main.GameBot.models.Enemy;
import app.main.GameBot.models.Player;
import app.main.GameBot.models.User;
import app.main.GameBot.repositories.FightRepository;
import app.main.GameBot.repositories.TalentRepository;
import app.main.GameBot.repositories.UserRepository;
import app.main.GameBot.states.UserState;
import app.main.GameBot.talent.Talent;
import app.main.GameBot.talent.TalentsInit;
import app.main.GameBot.way.Way;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FightService {

    private final TalentsInit talentsInit;
    private final FightHandler fightHandler;
    private final TalentRepository talentRepository;
    private final FightRepository fightRepository;
    private final UserRepository userRepository;
    private final MenuHandler menuHandler;


    public List<BotApiMethodMessage> callback_menu_handle(Update update, User user, Player player){
        List<BotApiMethodMessage> messages = new ArrayList<>();
        var callback = update.getCallbackQuery().getData();
        var chatId = update.getCallbackQuery().getFrom().getId();

        if(callback.startsWith("skills")){
            messages.add(fightHandler.skills_menu(chatId, user.getLanguage(), player));
        }
        if(callback.startsWith("actions")){
            messages.add(fightHandler.actions(chatId, user.getLanguage()));
        }
        if(callback.startsWith("evade")){
            messages.add(fightHandler.evade(chatId, user.getLanguage(), user, player));
        }
        if(callback.startsWith("back")){
            messages.add(fightHandler.fight_menu(chatId, user.getLanguage()));
        }
        if(callback.startsWith("choose_")){
            var talent_name = callback.substring(7);
            Talent talent = searchTalent(talent_name);
            app.main.GameBot.models.Talent talent1 = talentRepository.findTalentByPlayerAndName(player,
                    talent.getNameEn());
            if(talent.check_resources(player, talent1.getLevel())){
                messages.add(fightHandler.choose_talent(chatId, user.getLanguage(), talent, player));
                messages.add(fightHandler.use_skill(chatId, user.getLanguage(),player, user, talent));
                messages.add(fightHandler.enemy_characteristics(chatId, user.getLanguage(), player));
                if(fightRepository.findByPlayer(player).getEnemy().getHealth() <=0){
                    messages.add(fightHandler.enemy_dead(chatId, user.getLanguage(), player));
                    messages.add(menuHandler.menu(chatId, user.getLanguage()));
                    user.setUserState(UserState.MENU);
                    userRepository.save(user);
                }
            }else {
                messages.add(fightHandler.no_resources(chatId, user.getLanguage()));
                messages.add(fightHandler.fight_menu(chatId, user.getLanguage()));
            }
        }
        if(talentsInit.getWaysList().contains(searchWay(callback))){
            messages.add(fightHandler.talents_list(chatId, user.getLanguage(), searchWay(callback).getTalents(),
                    searchWay(callback), player));
            return messages;
        }

        return messages;
    }

    public SendMessage under_attack(Long chatId, String lang, User user, Player player){
        return fightHandler.under_attack(chatId, lang, user, player);
    }
    public SendMessage enemy_attack(Long chatId, String lang, Player player){
        return fightHandler.enemy_attack(chatId, lang, player);
    }
    public SendMessage first_enemy_step(Long chatId, String lang){
        return fightHandler.first_enemy_step(chatId, lang);
    }
    public SendMessage sendCharacteristics(Long chatId, String lang, Player player){
        return fightHandler.sendCharacteristics(chatId, lang, player);
    }
    public SendMessage fight_menu(Long chatId, String lang){
        return fightHandler.fight_menu(chatId, lang);
    }
    public SendMessage enemy_step(Long chatId, String lang){
        return fightHandler.enemy_step(chatId, lang);
    }
    public SendMessage player_step(Long chatId, String lang){
        return fightHandler.player_step(chatId, lang);
    }
    public SendMessage player_is_dead(Long chatId, String lang){
        return fightHandler.player_is_dead(chatId, lang);
    }

    public Boolean check_death_player(Player player){
        if(player.getHealthNow() <= 0 ){
            return true;
        }
        return false;
    }
    public Boolean check_death_enemy(Enemy enemy){
        if(enemy.getHealth() <= 0 ){
            return true;
        }
        return false;
    }

    private Way searchWay(String callback){
        List<Way> ways = talentsInit.getWaysList();
        for(Way way : ways){
            if(way.getNameEn().equals(callback)){
                return way;
            }
        }
        return null;
    }

    private Talent searchTalent(String callback){
        List<Way> ways = talentsInit.getWaysList();
        for(Way way : ways){
            for(Talent talent:way.getTalents()){
                if(talent.getNameEn().equals(callback)){
                    return talent;
                }
            }
        }
        return null;
    }
}
