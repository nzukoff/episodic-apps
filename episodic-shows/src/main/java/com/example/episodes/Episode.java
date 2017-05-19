package com.example.episodes;

import com.example.shows.Show;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "episodes")
@Data
public class Episode {
    private Long id;
    private int seasonNumber;
    private int episodeNumber;
    private Show show;

    public Episode() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name="show_id")
    public Show getShow() {
        return show;
    }
    public void setShow(Show show) {
        this.show = show;
    }
}