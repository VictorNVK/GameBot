package app.main.GameBot.models;

import app.main.GameBot.states.Location;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "player")
public class Player {

    @Id
    private Integer id;

    @Column
    private String nickname;

    @Enumerated
    private Location location;

    @Column
    private Integer level;

    @Column
    private Integer health;

    @Column
    private Integer energy;

    @Column
    private Integer blood;

    @Column
    private Integer attack;

    @Column
    private Integer defense;

    @Column
    private Integer room;

    @Column
    private Integer HealthRegeneration;

    @Column
    private Integer EnergyRegeneration;

    @Column
    private Integer BloodRegeneration;
}
