package com.fgmariano.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fgmariano.entities.Book;

public interface ManyRepository extends JpaRepository<Book, Integer> {

}
