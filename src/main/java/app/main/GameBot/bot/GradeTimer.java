package app.main.GameBot.bot;

import app.main.GameBot.bot.handler.PlayerHandler;
import app.main.GameBot.models.UpgradeProgress;
import app.main.GameBot.models.User;
import app.main.GameBot.repositories.UpgradeProgressRepository;
import app.main.GameBot.repositories.UserRepository;
import app.main.GameBot.states.UserState;
import app.main.GameBot.talent.Talent;
import app.main.GameBot.talent.TalentsInit;
import app.main.GameBot.way.Way;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GradeTimer {

    private final UpgradeProgressRepository upgradeProgressRepository;
    private final UserRepository userRepository;
    private final PlayerHandler playerHandler;

    private final GameBot gameBot;
    private final TalentsInit talentsInit;

    @SneakyThrows
    @Scheduled(fixedRate = 5000)
    @Async
    public void start_timer(){
        Date date = new Date();
        List<UpgradeProgress> upgradeProgresses = upgradeProgressRepository.findAll();
        for(UpgradeProgress upgradeProgress : upgradeProgresses){
            if(date.after(upgradeProgress.getTime())){
                User user = userRepository.findUserById(upgradeProgress.getPlayer().getId());
                var chatId = user.getChatId();
                if(upgradeProgress.getTalent() != null) {
                    var sendMessage = playerHandler.talent_up(chatId, user.getLanguage(),
                            upgradeProgress.getTalent().getName(),
                            upgradeProgress.getPlayer(),
                            searchTalent(upgradeProgress.getTalent().getName()), talentsInit.getWaysList());
                    gameBot.execute(sendMessage);
                    user.setUserState(UserState.MENU);
                    userRepository.save(user);
                    upgradeProgressRepository.delete(upgradeProgress);
                    gameBot.execute(playerHandler.character_menu(user.getChatId(), user.getLanguage()));

                }
                else if(upgradeProgress.getWay() != null){
                    var sendMessage = playerHandler.branch_up(chatId, user.getLanguage(),
                            upgradeProgress.getWay());
                    gameBot.execute(sendMessage);
                    user.setUserState(UserState.MENU);
                    userRepository.save(user);
                    upgradeProgressRepository.delete(upgradeProgress);
                    gameBot.execute(playerHandler.character_menu(user.getChatId(), user.getLanguage()));
                }
            }
        }
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
