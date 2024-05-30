package com.example.demo.model;

import com.example.demo.service.CadenasUtil;
import jakarta.persistence.*;

@Entity
@Table(name = "autor2")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    private Integer birth_year;
    private Integer death_year;
    private String name;

    @OneToOne
    @JoinTable(
            name = "Libro",
            joinColumns = @JoinColumn(name = "autor_id"),
            inverseJoinColumns = @JoinColumn(name = "id"))
    private Libro libros;

    public Autor() {}

    public Autor(DatosAutor autor) {
        this.name = CadenasUtil.limitarLongitud(autor.name(), 200);

        if (autor.birth_year() == null)
            this.birth_year = 1980;
        else
            this.birth_year = autor.birth_year();

        if (autor.death_year() == null)
            this.death_year = 3022;
        else
            this.death_year = autor.death_year();
    }



    // Getters y setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(Integer birth_year) {
        this.birth_year = birth_year;
    }

    public Integer getDeath_year() {
        return death_year;
    }

    public void setDeath_year(Integer death_year) {
        this.death_year = death_year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Libro getLibros() {
        return libros;
    }

    @Override
    public String toString() {
        return "Autor{" +
                "id=" + id +
                ", birth_year=" + birth_year +
                ", death_year=" + death_year +
                ", name='" + name + '\'' +
                ", libros=" + libros +
                '}';
    }
}
