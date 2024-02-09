package jaba.web.fourthWebLab.DatabaseHandlers.Results;

import jaba.web.fourthWebLab.DatabaseHandlers.User.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@NoArgsConstructor
@Data
@Table(name="results")
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Double x;
    @Column(nullable = false)
    private Double y;
    @Column(nullable = false)
    private Double r;
    @Column(nullable = false)
    private Date date;
    @Column(nullable = false)
    private Boolean hit;

    @ManyToOne
    @JoinColumn(name="user_login",nullable = false)
    private User user_login;

    public Result(Double x, Double y, Double r, Date date, Boolean hit, User user_login) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.date = date;
        this.hit = hit;
        this.user_login = user_login;
    }
}
