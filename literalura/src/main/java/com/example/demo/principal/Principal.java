package com.example.demo.principal;

import com.example.demo.model.*;
import com.example.demo.repository.AutorRepository;
import com.example.demo.repository.LibroRepository;
import com.example.demo.service.ConsumoApi;
import com.example.demo.service.ConvierteDatos;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private String URL_BASE = "https://gutendex.com/books/?search=";
    private ConvierteDatos conversor = new ConvierteDatos();

    private LibroRepository repository;
    private AutorRepository autorRepositorio;
    private List<Libro> libros;
    private List<Autor> autors;

    public Principal(LibroRepository repository, AutorRepository autorRepositorio) {
        this.repository = repository;
        this.autorRepositorio = autorRepositorio;

    }


    public void menu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
					Elija la opción a traves de su número:
						1.- Buscar libro por titulo
						2.- Lista liibros registrados
						3.- Lista autores registrados
						4.- Lista autores vivos en un determinado año
						5.- Listar libros por idioma
						0 - Salir
						""";
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroWeb();
                    break;
                case 2:
                    buscarLibros();
                    break;
                case 3:
                    buscarAutores();
                    break;
                case 4:
                    buscarAutoresVivo();
                    break;
                case 5:
                    buscarPorIdiomas();
                    break;
                case 0:
                    System.out.println("Adios, Vuelva Pronto...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }

    private void buscarLibros() {

        List<Libro> libros = repository.findAll();

        if (!libros.isEmpty()) {

            for (Libro libro : libros) {
                System.out.println("\n\n---------- LIBROS -------\n");
                System.out.println(" Titulo: " + libro.getTitulo());
                System.out.println(" Autor: " + libro.getAutor().getName());
                System.out.println(" Idioma: " + libro.getLenguaje());
                System.out.println(" Descargas: " + libro.getDescargas());
                System.out.println("\n-------------------------\n\n");
            }

        } else {
            System.out.println("\n\n ----- NO SE ENCONTRARON RESULTADOS ---- \n\n");
        }

    }

    private void buscarAutores() {
        List<Autor> autores = autorRepositorio.findAll();

        if (!autores.isEmpty()) {
            for (Autor autor : autores) {
                System.out.println("\n\n---------- Autores -------\n");
                System.out.println(" Nombre: " + autor.getName());
                System.out.println(" Fecha de Nacimiento: " + autor.getBirth_year());
                System.out.println(" Fecha de Fallecimiento: " + autor.getDeath_year());
                System.out.println(" Libros: " + autor.getLibros().getTitulo());
                System.out.println("\n-------------------------\n\n");
            }
        } else {
            System.out.println("\n\n ----- NO SE ENCONTRARON RESULTADOS ---- \n\n");

        }

    }

    private void buscarAutoresVivo() {
        System.out.println("Escriba el año para en el que vivio: ");
        var anio = teclado.nextInt();
        teclado.nextLine();

        List<Autor> autores = autorRepositorio.findForYear(anio);

        if (!autores.isEmpty()) {
            for (Autor autor : autores) {
                System.out.println("\n\n---------- Autores Vivos -------\n");
                System.out.println(" Nombre: " + autor.getName());
                System.out.println(" Fecha de nacimiento: " + autor.getBirth_year());
                System.out.println(" Fecha de fallecimiento: " + autor.getDeath_year());
                System.out.println(" Libros: " + autor.getLibros().getTitulo());
                System.out.println("\n-------------------------\n\n");
            }
        } else {
            System.out.println("\n\n ----- NO SE ENCONTRARON RESULTADOS ---- \n\n");

        }
    }

    private void buscarPorIdiomas() {

        var menu = """
				Seleccione un Idioma:
					1.- Español
					2.- Ingles
				
					""";
        System.out.println(menu);
        var idioma = teclado.nextInt();
        teclado.nextLine();

        String seleccion = "";

        if(idioma == 1) {
            seleccion = "es";
        } else 	if(idioma == 2) {
            seleccion = "en";
        }

        List<Libro> libros = repository.findForLanguaje(seleccion);

        if (!libros.isEmpty()) {

            for (Libro libro : libros) {
                System.out.println("\n\n---------- LIBROS POR IDIOMA-------\n");
                System.out.println(" Titulo: " + libro.getTitulo());
                System.out.println(" Autor: " + libro.getAutor().getName());
                System.out.println(" Idioma: " + libro.getLenguaje());
                System.out.println(" Descargas: " + libro.getDescargas());
                System.out.println("\n-------------------------\n\n");
            }

        } else {
            System.out.println("\n\n ----- NO SE ENCONTRARON RESULTADOS ---- \n\n");
        }


    }

    private void buscarLibroWeb() {
        Resultado datos = getDatosSerie();

        if (!datos.results().isEmpty()) {

            Libro libro = new Libro();
            libro = repository.save(libro);

        }

        System.out.println("Datos: ");
        System.out.println(datos);
    }

    private Resultado getDatosSerie() {
        System.out.println("Ingresa el nombre del libro que deseas buscar: ");
        var titulo = teclado.nextLine();
        titulo = titulo.replace(" ", "%20");
        System.out.println("Titlulo : " + titulo);
        System.out.println(URL_BASE + titulo);
        var json = consumoApi.obtenerDatos(URL_BASE + titulo);
        System.out.println(json);
        Resultado datos = conversor.obtenerDatos(json, Resultado.class);
        return datos;
    }




}


