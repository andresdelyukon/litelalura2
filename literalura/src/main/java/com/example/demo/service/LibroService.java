package com.example.demo.service;

import com.example.demo.model.Autor;
import com.example.demo.model.DatosLibro;
import com.example.demo.model.Libro;
import com.example.demo.model.Resultado;
import com.example.demo.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LibroService {
    @Autowired
    private LibroRepository repository;
    public void guardarLibro(Libro libro) {
        repository.save(libro);
    }

    @Transactional
    public Libro saveLibroWithAutor(Libro libro, Autor autor) {
        // Asociar el autor al libro
        libro.setAutor(autor);
        // Guardar el libro (esto también guardará el autor debido a CascadeType.ALL)
        return repository.save(libro);
    }

    public List<Libro> getAllLibros() {
        return repository.findAll();
    }

    public Optional<Libro> getLibroById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public void deleteLibroById(Long id) {
        repository.deleteById(id);
    }


}




