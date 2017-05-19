package com.example.shows;

import com.example.episodes.Episode;
import com.example.episodes.IEpisodeRepository;
import com.example.users.IUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(secure=false)
public class ShowsControllerTest {
    @Autowired
    MockMvc mvc;

    @Autowired
    IShowRepository showRepository;

    @Autowired
    IEpisodeRepository episodeRepository;

//    @Before
//    public void setup() {
//        showRepository.deleteAll();
//    }

    @Test
    @Transactional
    @Rollback
    public void postCreateShowWorks() throws Exception {
        Map<String, Object> payload = new HashMap<String, Object>() {
            {
                put("name", "friends");
            }
        };

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(payload);

        MockHttpServletRequestBuilder request = post("/shows")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name",is("friends")));
    }

    @Test
    @Transactional
    @Rollback
    public void getShowWorks() throws Exception {
        Show show = new Show();
        show.setName("Friends");
        showRepository.save(show);

        MockHttpServletRequestBuilder request = get("/shows");

        mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*",hasSize(1)))
                .andExpect(jsonPath("$[0].name",is("Friends")));
    }

    @Test
    @Transactional
    @Rollback
    public void getEpisodesWorks() throws Exception {
        Show show = new Show();
        show.setName("Friends");
        this.showRepository.save(show);
        Episode episode1 = new Episode();
        episode1.setEpisodeNumber(1);
        episode1.setSeasonNumber(1);
        episode1.setShow(show);
        System.out.println("EP 1 is" + episode1);
        this.episodeRepository.save(episode1);
        Episode episode2 = new Episode();
        episode2.setEpisodeNumber(2);
        episode2.setSeasonNumber(1);
        episode2.setShow(show);
        System.out.println("EP 2 is" + episode2);
        this.episodeRepository.save(episode2);

        MockHttpServletRequestBuilder request = get("/shows/1/episodes");

        mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk());

    }
}

