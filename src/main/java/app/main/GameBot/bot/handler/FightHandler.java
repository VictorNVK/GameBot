package app.main.GameBot.bot.handler;

import app.main.GameBot.bot.keyboard.FightKeyboard;
import app.main.GameBot.bot.keyboard.LocationKeyboard;
import app.main.GameBot.bot.keyboard.MenuKeyboard;
import app.main.GameBot.bot.messager.Messager;
import app.main.GameBot.bot.messager.MessagerEn;
import app.main.GameBot.bot.messager.MessagerRu;
import app.main.GameBot.location.Location;
import app.main.GameBot.location.LocationInit;
import app.main.GameBot.models.Enemy;
import app.main.GameBot.models.Fight;
import app.main.GameBot.models.Player;
import app.main.GameBot.models.User;
import app.main.GameBot.repositories.EnemyRepository;
import app.main.GameBot.repositories.FightRepository;
import app.main.GameBot.repositories.PlayerRepository;
import app.main.GameBot.repositories.UserRepository;
import app.main.GameBot.states.UserState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Random;

@Component
@RequiredArgsConstructor
public class FightHandler {

    private final UserRepository userRepository;
    private final MenuKeyboard menuKeyboard;
    private final LocationKeyboard locationKeyboard;
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
        if (number == 1) {
            player = enemy.attack(player, enemy_model);
            sendMessage.setText(messager.getEnemy_use_attack());
        } else if(enemy.talent_condition(player, fight.getCounter(), enemy_model)) {
            player = enemy.attack_talent(player, fight.getCounter(), enemy_model);
            enemy_model = enemy.talent_price(enemy_model);
            sendMessage.setText(messager.getEnemy_use_skill());
        }else {
            player = enemy.attack(player, enemy_model);
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
}
