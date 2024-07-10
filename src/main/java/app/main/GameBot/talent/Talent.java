package app.main.GameBot.talent;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Talent {

    private String nameRu;

    private String nameEn;


    private void action_attack(){

    }
    private void action_defense(){

    }
    public String descriptionRu(app.main.GameBot.models.Talent talent){
        return "";
    }
    public String descriptionEn(app.main.GameBot.models.Talent talent){
        return "";
    }
}
