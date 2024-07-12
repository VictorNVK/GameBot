package app.main.GameBot.models;

import jakarta.persistence.*;
import lombok.*;
import org.glassfish.grizzly.http.util.TimeStamp;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpgradeProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Date time;

    @ManyToOne
    @JoinColumn(name = "talent_id")
    private Talent talent;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @Column
    private Integer price;
}
