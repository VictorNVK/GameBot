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
    private Integer level = 1;

    @Column
    private Integer barrier = 0;

    @Column
    private Integer health = 20;

    @Column
    private Integer energy = 10;

    @Column
    private Integer blood = 5;

    @Column
    private Integer attack = 1;

    @Column
    private Integer defense = 0;

    @Column
    private Integer room;

    @Column
    private Integer HealthRegeneration = 1;

    @Column
    private Integer EnergyRegeneration = 1;

    @Column
    private Integer BloodRegeneration = 1;

    @Column
    private Integer crystals;

    @Column
    private String lastTalent;

    @Column
    private String lastBranch;
}
