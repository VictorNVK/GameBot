package app.main.GameBot.bot.handler;

import app.main.GameBot.bot.keyboard.FightKeyboard;
import app.main.GameBot.bot.keyboard.LocationKeyboard;
import app.main.GameBot.bot.keyboard.MenuKeyboard;
import app.main.GameBot.bot.messager.Messager;
import app.main.GameBot.bot.messager.MessagerEn;
import app.main.GameBot.bot.messager.MessagerRu;
import app.main.GameBot.location.Location;
import app.main.GameBot.location.LocationInit;
import app.main.GameBot.models.*;
import app.main.GameBot.repositories.*;
import app.main.GameBot.states.UserState;
import app.main.GameBot.talent.Suppression;
import app.main.GameBot.talent.Talent;
import app.main.GameBot.talent.TalentsInit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.nio.channels.Pipe;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class FightHandler {

    private final UserRepository userRepository;
    private final MenuKeyboard menuKeyboard;
    private final LocationKeyboard locationKeyboard;
    private final WayRepository wayRepository;
    private final TalentsInit talentsInit;
    private final TalentRepository talentRepository;
    private Messager messager;
    private final FightKeyboard fightKeyboard;
    private final LocationInit locationInit;
    private final FightRepository fightRepository;
    private final EnemyRepository enemyRepository;
    private final PlayerRepository playerRepository;


    private void choose_lang(String lang) {
        if (lang.startsWith("rus")) {
            messager = new MessagerRu();
        } else if (lang.startsWith("eng")) {
            messager = new MessagerEn();
        }
    }

    public SendMessage under_attack(Long chatId, String lang, User user, Player player) {
        choose_lang(lang);
        var sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        Location location = search_location(player.getLocation());
        Random random = new Random();
        var number = random.nextInt(location.getEnemy().size());

        if (lang.equals("rus")) {
            var text = messager.getYou_under_attack() + location.getEnemy().get(number).getNameRu();
            sendMessage.setText(text);
        } else {
            var text = messager.getYou_under_attack() + location.getEnemy().get(number).getNameEn();
            sendMessage.setText(text);
        }
        app.main.GameBot.enemy.Enemy enemy = location.getEnemy().get(number);

        Enemy enemy_model = new Enemy();
        enemy = enemy.scale(enemy, player);
        enemy_model.setAttack(enemy.getAttack());
        enemy_model.setNameEn(enemy.getNameEn());
        enemy_model.setEnergy(enemy.getEnergy());
        enemy_model.setHealth(enemy.getHealth());
        enemy_model.setDefense(enemy.getDefense());

        enemyRepository.save(enemy_model);

        Fight fight = new Fight();
        fight.setCounter(1);
        fight.setEnemy(enemy_model);
        fight.setPlayer(player);

        fightRepository.save(fight);
        return sendMessage;
    }

    private Location search_location(String name) {
        for (Location location : locationInit.getLocations()) {
            if (location.getNameEn().equals(name)) {
                return location;
            }
        }
        return null;
    }
        public SendMessage enemy_attack(Long chatId, String lang, Player player) {
        choose_lang(lang);
        var sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

        Fight fight = fightRepository.findByPlayer(player);
        app.main.GameBot.enemy.Enemy enemy = search_enemy(fight.getEnemy().getNameEn(), player.getLocation());
        Enemy enemy_model = fight.getEnemy();
        Random random = new Random();
        var number = random.nextInt(2);
            List<app.main.GameBot.models.Talent> talents = talentRepository.findTalentsByPlayerAndActive(player, true);
            Talent talent = null;
            var talentLevel = 0;
            if (!talents.isEmpty()) {
                for (app.main.GameBot.models.Talent talent1 : talents) {
                    talent1.setCounter(talent1.getCounter() - 1);
                    talent = searchTalent(talent1.getName());
                    if (talent1.getCounter() <= 0 && talent.getType().equals("defense")) {
                        talent1.setActive(false);
                        talentRepository.save(talent1);
                    }
                }
                 talent = searchTalent(talents.getLast().getName());
                 talentLevel = talents.getLast().getLevel();
            }

        if (number == 1) {
            player = enemy.attack(player, enemy_model, talent, talentLevel);
            sendMessage.setText(messager.getEnemy_use_attack());
        } else if(enemy.talent_condition(player, fight.getCounter(), enemy_model)) {
            player = enemy.attack_talent(player, fight.getCounter(), enemy_model, talent, talentLevel);
            enemy_model = enemy.talent_price(enemy_model);
            sendMessage.setText(messager.getEnemy_use_skill());
        }else {
            player = enemy.attack(player, enemy_model, talent, talentLevel);
            sendMessage.setText(messager.getEnemy_use_attack());
        }
        enemyRepository.save(enemy_model);

        playerRepository.save(player);
        fight.setCounter(fight.getCounter() + 1);
        fightRepository.save(fight);

        return sendMessage;
    }

    public SendMessage first_enemy_step(Long chatId, String lang) {
        choose_lang(lang);
        var sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(messager.getFirst_enemy_step());
        return sendMessage;
    }

    private app.main.GameBot.enemy.Enemy search_enemy(String name, String location_name) {
        Location location = search_location(location_name);
        for (app.main.GameBot.enemy.Enemy enemy : location.getEnemy()) {
            if (enemy.getNameEn().equals(name)) {
                return enemy;
            }
        }
        return null;
    }

    public SendMessage sendCharacteristics(Long chatId, String lang, Player player) {
        choose_lang(lang);
        var sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(messager.getCharacteristics() +
                "\n\n"
                + messager.getLevel() + player.getLevel() + "\uD83C\uDF1F"
                + "\n" + messager.getHealth() + player.getHealthNow() + "/" + player.getHealth() + "♥\uFE0F"
                + "\n" + messager.getEnergy() + player.getEnergyNow() + "/" + player.getEnergy() + "⚡\uFE0F"
                + "\n" + messager.getBlood() + player.getBloodNow() + "/" + player.getBlood() + "\uD83E\uDE78"
                + "\n" + messager.getAttack() + player.getAttack() + "\uD83D\uDDE1"
                + "\n" + messager.getDefense() + player.getDefense() + "\uD83D\uDEE1"
                + "\n" + messager.getBarrier() + player.getBarrier() + "\uD83D\uDD35");

        return sendMessage;
    }
    public SendMessage fight_menu(Long chatId, String lang){
        choose_lang(lang);
        var sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(messager.getChoose_param_of_menu());
        sendMessage.setReplyMarkup(fightKeyboard.action_menu(lang));
        return sendMessage;
    }
    public SendMessage actions(Long chatId, String lang){
        choose_lang(lang);
        var sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(messager.getChoose_param_of_menu());
        sendMessage.setReplyMarkup(fightKeyboard.actions_keyboard(lang));
        return sendMessage;
    }
    public SendMessage evade(Long chatId, String lang, User user, Player player){
        choose_lang(lang);
        var sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        Random random = new Random();
        int randomNumber = random.nextInt(100) + 1;
        if (randomNumber <= 30) {
            sendMessage.setText(messager.getEvade_is_successful());
            sendMessage.setReplyMarkup(locationKeyboard.action_menu_keyboard(lang));
            Fight fight = fightRepository.findByPlayer(player);
            fightRepository.delete(fight);
            enemyRepository.delete(fight.getEnemy());
            user.setUserState(UserState.MENU);
            userRepository.save(user);
        } else {
            sendMessage.setText(messager.getEvade_is_unsuccessful());
        }
        return sendMessage;
    }
    public SendMessage player_is_dead(Long chatId, String lang){
        choose_lang(lang);
        var sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(messager.getPlayer_is_dead());
        return sendMessage;
    }
    public SendMessage enemy_step(Long chatId, String lang){
        choose_lang(lang);
        var sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(messager.getEnemy_step());
        return sendMessage;
    }
    public SendMessage player_step(Long chatId, String lang){
        choose_lang(lang);
        var sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(messager.getPlayer_step());
        return sendMessage;
    }
    public SendMessage skills_menu(Long chatId, String lang, Player player){
        choose_lang(lang);
        var sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        List<Way> ways = wayRepository.findWaysByPlayer(player);
        if(ways.isEmpty()){
            sendMessage.setText(messager.getWays_not_learned());
            return sendMessage;
        }
        sendMessage.setText(messager.getChoose_param_of_menu());
        sendMessage.setReplyMarkup(fightKeyboard.ways_menu(lang,talentsInit.getWaysList()));
        return sendMessage;
    }
    public SendMessage talents_list(Long chatId, String lang, List<Talent> talents, app.main.GameBot.way.Way way,
                                    Player player){
        choose_lang(lang);
        var sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(messager.getChoose_param_of_menu());
        var wayModel = wayRepository.findWayByPlayerAndName(player, way.getNameEn());
        sendMessage.setReplyMarkup(fightKeyboard.talents_list(talents, lang, wayModel));

        return sendMessage;
    }
    public SendMessage no_resources(Long chatId, String lang){
        choose_lang(lang);
        var sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(messager.getNot_resources());
        return sendMessage;
    }

    public SendMessage choose_talent(Long chatId, String lang, Talent talent, Player player){
        choose_lang(lang);
        var sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(messager.getSkill_is_choosen());
        app.main.GameBot.models.Talent talentModel = talentRepository.findTalentByPlayerAndName(player,
                talent.getNameEn());
            talentModel.setActive(true);
            talentRepository.save(talentModel);
        return sendMessage;
    }

    public SendMessage use_skill(Long chatId, String lang, Player player, User user, Talent talent){
        choose_lang(lang);
        var sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        Fight fight = fightRepository.findByPlayer(player);
        app.main.GameBot.models.Talent talentModel = talentRepository.findTalentByPlayerAndName(player, talent.getNameEn());
        Enemy enemy = fight.getEnemy();
        enemy = talent.action_attack(enemy, player, talentModel);
        if(enemy.getHealth() < 0){
            enemy.setHealth(0);
        }
        player = talent.action_price(player, talentModel);
        playerRepository.save(player);
        enemyRepository.save(enemy);
        user.setUserState(UserState.FIGHT_AWAIT);
        userRepository.save(user);
        sendMessage.setText(messager.getAwait_your_step());
        return sendMessage;
    }
    public SendMessage enemy_characteristics(Long chatId, String lang, Player player){
        choose_lang(lang);
        var sendMessage = new SendMessage();
        Fight fight = fightRepository.findByPlayer(player);
        Enemy enemy = fight.getEnemy();
        sendMessage.setChatId(chatId);
        sendMessage.setText(messager.getEnemy_stats() + "\n"
                + "\n" + messager.getHealth() + enemy.getHealth() + "♥\uFE0F"
                + "\n" + messager.getEnergy() + enemy.getEnergy() + "⚡\uFE0F"
                + "\n" + messager.getAttack() + enemy.getAttack() + "\uD83D\uDDE1"
                + "\n" + messager.getDefense() + enemy.getDefense() + "\uD83D\uDEE1");
        return sendMessage;
    }

    public SendMessage enemy_dead(Long chatId, String lang, Player player){
        choose_lang(lang);
        var sendMessage = new SendMessage();
        Fight fight = fightRepository.findByPlayer(player);
        sendMessage.setChatId(chatId);
        var text = messager.getEnemy_is_dead();
        var crystals = player.getLevel() + 1;
        sendMessage.setText(text + "-" + messager.getCrystal() + " " + crystals + "x" + "\uD83D\uDC8E");
        fightRepository.delete(fight);
        enemyRepository.delete(fight.getEnemy());
        player.setCrystals(player.getCrystals() + crystals);
        playerRepository.save(player);
        return sendMessage;
    }

    private Talent searchTalent(String callback){
        List<app.main.GameBot.way.Way> ways = talentsInit.getWaysList();
        for(app.main.GameBot.way.Way way : ways){
            for(Talent talent:way.getTalents()){
                if(talent.getNameEn().equals(callback)){
                    return talent;
                }
            }
        }
        return null;
    }
}
