package com.example.artikel;

import com.example.artikel.model.Artikel;
import com.example.artikel.repository.ArtikelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class ArtikelIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ArtikelRepository artikelRepository;

    @BeforeEach
    void setUp() {
        artikelRepository.deleteAll();
        Artikel artikel = new Artikel();
        artikel.setJudulArtikel("Integration Test Title");
        artikel.setContent("Integration Test Content");
        artikelRepository.save(artikel);
    }

    @Test
    void testIndexPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(content().string(containsString("Integration Test Title")))
                .andExpect(content().string(containsString("Integration Test Content")));
    }

    @Test
    void testSubmitPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/submit"))
                .andExpect(status().isOk())
                .andExpect(view().name("submit"));
    }

    @Test
    void testSubmitArtikel() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/submit")
                .param("judulArtikel", "New Artikel Title")
                .param("content", "New Artikel Content"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(content().string(containsString("New Artikel Title")))
                .andExpect(content().string(containsString("New Artikel Content")));
    }
}
