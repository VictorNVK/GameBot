package app.main.GameBot.talent;

import app.main.GameBot.bot.GameBot;
import app.main.GameBot.bot.handler.PlayerHandler;
import app.main.GameBot.models.UpgradeProgress;
import app.main.GameBot.models.User;
import app.main.GameBot.repositories.PlayerRepository;
import app.main.GameBot.repositories.UpgradeProgressRepository;
import app.main.GameBot.repositories.UserRepository;
import app.main.GameBot.states.UserState;
import app.main.GameBot.way.Way;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GradeTimer {

    private final UpgradeProgressRepository upgradeProgressRepository;
    private final PlayerRepository playerRepository;
    private final UserRepository userRepository;
    private final PlayerHandler playerHandler;
    @Autowired
    private final GameBot gameBot;
    private final TalentsInit talentsInit;

    @SneakyThrows
    @Scheduled(fixedRate = 25000)
    @Async
    public void start_timer(){
        Date date = new Date();
        List<UpgradeProgress> upgradeProgresses = upgradeProgressRepository.findAll();
        for(UpgradeProgress upgradeProgress : upgradeProgresses){
            if(date.after(upgradeProgress.getTime())){
                User user = userRepository.findUserById(upgradeProgress.getPlayer().getId());
                var chatId = user.getChatId();
                 var sendMessage = playerHandler.talent_up(chatId, user.getLanguage(),
                        upgradeProgress.getTalent().getName(),
                        upgradeProgress.getPlayer(),
                         searchTalent(upgradeProgress.getTalent().getName()), talentsInit.getWaysList());
                gameBot.execute(sendMessage);
                upgradeProgressRepository.delete(upgradeProgress);
                user.setUserState(UserState.MENU);
                userRepository.save(user);
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
