package com.example.shows;

import com.example.episodes.Episode;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "shows")
@Data
public class Show {
    private long id;
    private String name;
    private List<Episode> episodes;

    public Show() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "show")
    public List<Episode> getEpisodes() {
        return episodes;
    }
    public void setEpisodes(List<Episode> episodes) {
        this.episodes = episodes;
    }
}
