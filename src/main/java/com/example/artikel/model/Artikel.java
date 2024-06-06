package com.example.artikel.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Artikel {
    @Id
    @GeneratedValue
    private Long id;

    private String judulArtikel;
    private String content;
}
