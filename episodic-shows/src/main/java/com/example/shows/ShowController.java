package com.example.shows;

import com.example.episodes.Episode;
import com.example.episodes.IEpisodeRepository;
import com.example.users.IUserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shows")
public class ShowController {

    private final IShowRepository showRepository;
    private final IEpisodeRepository episodeRepository;

    public ShowController(IShowRepository showRepository, IEpisodeRepository episodeRepository) {
        this.showRepository = showRepository;
        this.episodeRepository = episodeRepository;
    }

    @PostMapping
    public Show createShow(@RequestBody Show show) {
        return this.showRepository.save(show);
    }

    @GetMapping
    public Iterable<Show> getShows() {
        return this.showRepository.findAll();
    }

    @GetMapping("/{id}/episodes")
    public Iterable<Episode> getShow(@PathVariable Long id) {
        Show show = this.showRepository.findOne(id);
        return show.getEpisodes();
    }
}

