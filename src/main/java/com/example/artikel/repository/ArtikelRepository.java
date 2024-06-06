package com.example.artikel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.artikel.model.Artikel;

public interface ArtikelRepository extends JpaRepository<Artikel, Long> {

}
