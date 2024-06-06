package com.example.artikel;

import com.example.artikel.controller.ArtikelController;
import com.example.artikel.model.Artikel;
import com.example.artikel.repository.ArtikelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ArtikelControllerTest {

    @Mock
    private ArtikelRepository artikelRepository;

    @InjectMocks
    private ArtikelController artikelController;

    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        model = new BindingAwareModelMap();
    }

    @Test
    void testIndex() {
        List<Artikel> artikels = new ArrayList<>();
        Artikel artikel = new Artikel();
        artikel.setJudulArtikel("Test Title");
        artikel.setContent("Test Content");
        artikels.add(artikel);

        when(artikelRepository.findAll()).thenReturn(artikels);

        String viewName = artikelController.index(model);
        assertEquals("index", viewName);
        List<Artikel> modelArtikels = (List<Artikel>) model.getAttribute("artikels");
        assertEquals(1, modelArtikels.size());
        assertEquals("Test Title", modelArtikels.get(0).getJudulArtikel());
        assertEquals("Test Content", modelArtikels.get(0).getContent());

        verify(artikelRepository, times(1)).findAll();
    }

    @Test
    void testIndexNoArticles() {
        when(artikelRepository.findAll()).thenReturn(new ArrayList<>());

        String viewName = artikelController.index(model);
        assertEquals("index", viewName);
        List<Artikel> modelArtikels = (List<Artikel>) model.getAttribute("artikels");
        assertEquals(0, modelArtikels.size());

        verify(artikelRepository, times(1)).findAll();
    }

    void testSubmitArtikel() {
        Artikel artikel = new Artikel();
        artikel.setJudulArtikel("Test Title");
        artikel.setContent("Test Content");

        String viewName = artikelController.submitArtikel(artikel);
        assertEquals("redirect:/", viewName);

        verify(artikelRepository, times(1)).save(artikel);
    }

    @Test
    void testSubmitArtikelEmptyTitle() {
        Artikel artikel = new Artikel();
        artikel.setContent("Test Content");

        String viewName = artikelController.submitArtikel(artikel);
        assertEquals("redirect:/", viewName);

        verify(artikelRepository, times(1)).save(artikel);
    }

    @Test
    void testSubmitArtikelEmptyContent() {
        Artikel artikel = new Artikel();
        artikel.setJudulArtikel("Test Title");

        String viewName = artikelController.submitArtikel(artikel);
        assertEquals("redirect:/", viewName);

        verify(artikelRepository, times(1)).save(artikel);
    }

}
